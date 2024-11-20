package com.socialtripper.restapi.services;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface MultimediaService {
    String uploadMultimedia(MultipartFile multipartFile, String filename) throws IOException;
}
