package org.npeonelove.fileservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.npeonelove.fileservice.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @SneakyThrows
    @PostMapping
    public ResponseEntity<List<String>> uploadMedia(@RequestPart("directory") String directory,
                                                    @RequestPart("images") MultipartFile[] files) {
        return ResponseEntity.ok(fileService.uploadFiles(directory, files));
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteMedia(@RequestParam("keys") String[] keys) {
        fileService.deleteFiles(keys);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
