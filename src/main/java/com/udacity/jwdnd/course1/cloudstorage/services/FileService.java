package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.repositories.FileRespsitory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class FileService {

    FileRespsitory fileRespsitory;

    public FileService(FileRespsitory fileRespsitory) {
        this.fileRespsitory = fileRespsitory;
    }

    public File getFile(Integer fileId) {
        return fileRespsitory.getFile(fileId);
    }

    public List<File> getUserFiles(Integer userId) {
        return fileRespsitory.getUserFiles(userId);
    }

    public Integer storeFile(MultipartFile file, Integer userId) throws IOException {
        InputStream inputStream = file.getInputStream();
        byte[] fileData = new byte[(int) file.getSize()];
        inputStream.read(fileData);

        File userFile = new File();
        userFile.setFilename(file.getOriginalFilename());
        userFile.setContentType(file.getContentType());
        userFile.setFileSize(String.valueOf(file.getSize()));
        userFile.setUserId(userId);
        userFile.setFileData(fileData);

        return fileRespsitory.insert(userFile);
    }

    public Integer delete(Integer fileId) {
        return fileRespsitory.delete(fileId);
    }
}
