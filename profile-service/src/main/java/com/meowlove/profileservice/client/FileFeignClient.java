package com.meowlove.profileservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "file-service")
public interface FileFeignClient {

    // возвращает список из ссылок на добавленные объекты
    @PostMapping(value = "/api/v1/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<String> uploadMedia(@RequestPart("directory") String directory, @RequestPart("images") MultipartFile[] files);

    // возвращает http статус что все ок (ex: "OK")
    @DeleteMapping(value = "/api/v1/files")
    String deleteMedia(@RequestParam("keys") String[] keys);
}

