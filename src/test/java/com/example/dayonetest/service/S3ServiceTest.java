package com.example.dayonetest.service;

import com.example.dayonetest.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

public class S3ServiceTest extends IntegrationTest {

  @Autowired private S3Service s3Service;

  @Test
  public void s3PutAndGetTest() throws Exception {

    // given
    var bucket = "test-bucket";
    var key = "sampleObject.txt";
    var sampleFile = new ClassPathResource("static/sample.txt").getFile();

    // when
    s3Service.putFile(bucket, key, sampleFile);

    // then
    var resultFile = s3Service.getFile(bucket, key);

    List<String> sampleFileLines = FileUtils.readLines(sampleFile);
    List<String> resultFileLines = FileUtils.readLines(resultFile);
    Assertions.assertIterableEquals(sampleFileLines, resultFileLines);
  }
}
