package com.sirma.demo.util;

import com.sirma.demo.exceptions.FileException;
import lombok.SneakyThrows;
import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator {

    public static void ValidateCSV (MultipartFile csvFile){
        if (csvFile.isEmpty()){
            throw new FileException("File is empty!");
        }
        validateMime(csvFile);
    }

    @SneakyThrows
    private static void validateMime(MultipartFile csvFile) {
        System.out.println("..........");
    }
}
