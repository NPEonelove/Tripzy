package org.npeonelove.fileservice.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Client s3Client;

    @Value("${yandex.s3.bucket}")
    private String bucket;

    @SneakyThrows
    public List<String> uploadFiles(String directory, MultipartFile[] files) {

        List<String> fileLinks = new ArrayList<>();

        for (MultipartFile file : files) {
            String key = directory + "/" + UUID.randomUUID() + "-" + System.currentTimeMillis() + "-" + file.getOriginalFilename();

            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .acl(ObjectCannedACL.PUBLIC_READ)
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );

            fileLinks.add("https://storage.yandexcloud.net/" + bucket + "/" + key);
        }

        return fileLinks;
    }

    public void deleteFiles(String[] keyNames) {

        for (String keyName : keyNames) {

            List<String> splits = Arrays.asList(keyName.split("/"));
            List<String> keys = splits.subList(4, splits.size());
            StringBuilder stringBuilder = new StringBuilder();

            for (String key : keys) {
                stringBuilder.append(key).append("/");
            }

            keyName = stringBuilder.substring(0, stringBuilder.toString().length() - 1);
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucket).key(keyName).build();

            s3Client.deleteObject(deleteObjectRequest);
        }
    }

}
