package com.cs203.g1t4.backend.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.cs203.g1t4.backend.models.exceptions.InvalidImageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.ion.IonException;


import java.io.File;
import java.io.IOException;

@Service
public class S3Service {

    private final AmazonS3 s3;

    public S3Service (AmazonS3 s3, AmazonS3 s3Client) {
        this.s3 = s3;
    }

    public void putObject(String bucketName, String key, MultipartFile file) {

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());

            System.out.println(file.getContentType());
            System.out.println(file.getInputStream());

            s3.putObject(bucketName, key, file.getInputStream(), objectMetadata);
        } catch (IOException e) {
            throw new InvalidImageException();
        }

    }


// TODO: get object request

//    public byte[] getObject(String bucketName, String key) {
//        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//                .bucket(bucketName)
//                .key(key)
//                .build();
//
//        ResponseInputStream<GetObjectResponse> res = s3.getObject(getObjectRequest);
//
//        try {
//            return res.readAllBytes();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
