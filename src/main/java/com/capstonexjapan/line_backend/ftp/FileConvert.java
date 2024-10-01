package com.capstonexjapan.line_backend.ftp;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileConvert {

    // Convert File to MultipartFile using byte[]
    public static MultipartFile fileToMultipartFileConvert(File file) throws IOException {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return new ByteArrayMultipartFile(file.getName(), file.getName(), Files.probeContentType(file.toPath()), fileContent);
    }

    // Convert MultipartFile to File
    public static File multipartFileToFileConvert(MultipartFile multipartFile, String path) {
        File file = new File(Paths.get(path + multipartFile.getOriginalFilename()).toAbsolutePath().toString());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while converting MultipartFile to File.", e);
        }
        System.out.println("CONVERT: " + file.getAbsolutePath());
        return file;
    }

    // Remove local file
    public static void removeLocalFile(String path) {
        File file = new File(Paths.get(path).toAbsolutePath().toString());
        System.out.println("REMOVE: " + file.getAbsolutePath() + " / STATUS: " + file.delete());
    }
}
