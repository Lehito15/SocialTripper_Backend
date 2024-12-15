package com.socialtripper.restapi.services;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

/**
 * Serwis zarządzający operacjami na multimediach.
 */
public interface MultimediaService {
    /**
     * Metoda zapisująca plik multimedialny z Azure Blob Storage.
     *
     * @param multipartFile plik multimedialny
     * @param filename nazwa pliku
     * @return url pliku w Azure Blob Storage
     * @throws IOException wyjątek wejścia / wyjścia rzucany przez funkcję sdk upload
     */
    String uploadMultimedia(MultipartFile multipartFile, String filename) throws IOException;
}
