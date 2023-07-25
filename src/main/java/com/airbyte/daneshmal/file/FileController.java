package com.airbyte.daneshmal.file;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import static com.airbyte.daneshmal.security.permission.ManagePermission.COMPANY_WRITE;


@RestController
@RequestMapping("/api/v1/file")
public class FileController {
    private final FtpService fileService;

    public FileController(FtpService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    @PreAuthorize(COMPANY_WRITE)
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,RedirectAttributes redirectAttributes) throws IOException {
        return new ResponseEntity<>(fileService.upload(file, redirectAttributes), HttpStatus.OK);
    }
}
