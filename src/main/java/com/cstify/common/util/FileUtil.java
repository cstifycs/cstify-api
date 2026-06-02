package com.cstify.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUtil {
    //private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static String save(MultipartFile file, String uploadRoot, String filePath) {
        try {
            //String dateDir = LocalDate.now().format(DATE_FORMAT);
            //Path dirPath = Paths.get(uploadRoot, dateDir);
            Path dirPath = Paths.get(uploadRoot, filePath);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            String ext = getExtension(file.getOriginalFilename());
            String fileName = UUID.randomUUID() + ext;

            Path filePath2 = dirPath.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    filePath2,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return fileName;

        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }

    private static String getExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
