package com.cs203.g1t4.backend.config.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
    @Value("${env.AWS_BUCKET_REGION}")
    private String awsRegion;

    @Value("${env.AWS_ACCESS_KEY}")
    private String accessKey;

    @Value("${env.AWS_SECRET_KEY}")
    private String secretKey;


    @Bean
    public AmazonS3 s3Client() {
        // Create the credentials
        AWSCredentials credentials = new BasicAWSCredentials(
                accessKey,
                secretKey
        );

        // Create the client using the client builder
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(awsRegion)
                .build();

        // Return the client
        return s3client;
    }

}
