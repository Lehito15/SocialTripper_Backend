package com.socialtripper.restapi.services.implementation;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.socialtripper.restapi.services.MultimediaService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


/**
 * Implementacja serwisu zarządzającego multimediami w aplikacji.
 */
@Service
public class MultimediaServiceImpl implements  MultimediaService {
    /**
     * Nazwa kontenera w Azure Blob Storage.
     * Wartość pobierana z application.yml.
     */
    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    /**
     * Connection String do pamięci Blob.
     * Wartość pobierana z application.yml.
     */
    @Value("${spring.cloud.azure.storage.blob.connection-string}")
    private String connectionString;

    /**
     * Klient serwisu Blob.
     */
    private BlobServiceClient blobServiceClient;

    /**
     * Metoda inicjalizująca klienta Blob. Metoda wykonywana jest po utworzeniu istancji serwisu.
     */
    @PostConstruct
    public void init() {
        blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String uploadMultimedia(MultipartFile multipartFile, String filename) throws IOException {
        BlobClient blobClient = blobServiceClient
                .getBlobContainerClient(containerName)
                .getBlobClient(filename + getFileExtension(multipartFile));
        blobClient.upload(multipartFile.getInputStream(), multipartFile.getSize(), true);
        return blobClient.getBlobUrl();
    }

    /**
     * Metoda zwracająca roszerzenie przesyłanego pliku.
     *
     * @param multipartFile przesyłany plik
     * @return rozszerzenie pliku wraz ze znakiem '.' przed nim
     */
    public String getFileExtension(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        assert originalFilename != null;
        int dotIndex = originalFilename.indexOf('.');

        if (dotIndex > 0 && dotIndex < originalFilename.length() - 1) {
            return originalFilename.substring(dotIndex);
        }
        return "";
    }
}
