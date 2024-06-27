package com.example.dayonetest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.util.List;

@Configuration
public class s3Config {
    @Value("${aws.endpoint}")
    String awsEndpoint;


    // aws 서비스를 사용하기 위한 인증 정보를 담아서 제공해주는 클래스
    @Bean
    public AwsCredentialsProvider awsCredentialsProvider(){
        return AwsCredentialsProviderChain.builder()
                .reuseLastProviderEnabled(true)
                .credentialsProviders(List.of(
                        DefaultCredentialsProvider.create(),
                        StaticCredentialsProvider.create(AwsBasicCredentials.create("foo","bar"))
                ))
                .build();
    }

    // S3 서비스를 실제로 사용하기 위한 client 클래스
    @Bean
    public S3Client s3Client(){
        return S3Client.builder()
                .credentialsProvider(awsCredentialsProvider())
                .region(Region.AP_NORTHEAST_2)
                .endpointOverride(URI.create(awsEndpoint))
                .build();
    }
}
