package com.airbyte.daneshmal.file;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Service
public class FtpService {

    @Value("${server.host}")
    private String HOST;
    @Value("${server.username}")
    private String USERNAME;
    @Value("${server.password}")
    private String PASSWORD;



    public String upload(MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        FTPClient con;

        try {
            con = new FTPClient();
            con.connect(HOST);
            if (con.login(USERNAME, PASSWORD)) {
                con.enterLocalPassiveMode();
                con.setFileType(FTP.BINARY_FILE_TYPE);

                boolean result = con.storeFile("/public_html/" + file.getOriginalFilename(), file.getInputStream());
                con.logout();
                con.disconnect();
                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded " + file.getOriginalFilename() + "!");

                return "https://cloud.daneshmall.com/" + file.getOriginalFilename();
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message",
                    "Could not upload " + file.getOriginalFilename() + "!");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "can't upload the file!");
        }
        return null;
    }
}
