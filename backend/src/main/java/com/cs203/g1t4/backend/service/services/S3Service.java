package com.cs203.g1t4.backend.service.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.cs203.g1t4.backend.models.exceptions.invalidFieldsException.InvalidImageException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public interface S3Service {

    void putObject(String bucketName, String key, MultipartFile file);

    // S3 objects successfully changed to public access
    // Instead of using presignedUrl, now is a formatted string
//    public String getObjectURL(String bucketName, String key) {
//        Calendar calendar = Calendar.getInstance();
//
//        // set the expiry
//        calendar.setTime(new Date());
//        calendar.add(Calendar.HOUR, 1); // Generated URL will be valid for 1 hour
//
//        // get the signed URL
//        return s3.generatePresignedUrl(bucketName, key, calendar.getTime()).toString();
//    }
}
