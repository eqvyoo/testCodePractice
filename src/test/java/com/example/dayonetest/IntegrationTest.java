package com.example.dayonetest;

import com.redis.testcontainers.RedisContainer;
import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.junit.Ignore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

@Ignore
@Transactional
@SpringBootTest
@ContextConfiguration(initializers = IntegrationTest.IntegrationTestInitializer.class)
public class IntegrationTest {

  static DockerComposeContainer rdbms;

  static RedisContainer redis;

  static LocalStackContainer aws;

  static KafkaContainer kafka;

  static {
    rdbms =
        new DockerComposeContainer(new File("infra/test/docker-compose.yaml"))
            .withExposedService(
                "local-db",
                3306,
                Wait.forLogMessage(".*ready for connections.*", 1)
                    .withStartupTimeout(Duration.ofSeconds(300)))
            .withExposedService(
                "local-db-migrate",
                0,
                Wait.forLogMessage("(.*Successfully applied.*)|(.*Successfully validated.*)", 1)
                    .withStartupTimeout(Duration.ofSeconds(300)));
    rdbms.start();

    redis = new RedisContainer(RedisContainer.DEFAULT_IMAGE_NAME.withTag("6"));
    redis.start();
    aws =
        (new LocalStackContainer())
            .withServices(LocalStackContainer.Service.S3)
            .withStartupTimeout(Duration.ofSeconds(600));
    aws.start();

    kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.0")).withKraft();
    kafka.start();
  }

  static class IntegrationTestInitializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      Map<String, String> properties = new HashMap<>();

      var rdbmsHost = rdbms.getServiceHost("local-db", 3306);
      var rdbmsPort = rdbms.getServicePort("local-db", 3306);

      var redisHost = redis.getHost();
      var redisPort = redis.getFirstMappedPort();

      properties.put(
          "spring.datasource.url", "jdbc:mysql://" + rdbmsHost + ":" + rdbmsPort + "/score");
      properties.put("spring.data.redis.host", redisHost);
      properties.put("spring.data.redis.port", redisPort.toString());

      try {
        aws.execInContainer("awslocal", "s3api", "create-bucket", "--bucket", "test-bucket");
        properties.put("aws.endpoint", aws.getEndpoint().toString());
      } catch (Exception e) {
        // ignore
      }

      properties.put("spring.kafka.bootstrap-servers", kafka.getBootstrapServers());

      TestPropertyValues.of(properties).applyTo(applicationContext);
    }
  }
}
