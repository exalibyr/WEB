package com.excalibur.myBlog.service.Impl;

import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.fileStorage.entity.FileStorageResponseBody;
import com.excalibur.myBlog.service.FileService;
import com.excalibur.myBlog.dao.File;
import com.excalibur.myBlog.repository.FileRepository;
import com.excalibur.myBlog.service.UserService;
import com.excalibur.myBlog.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserService userService;

    @Override
    public File setAvatarFile(FileStorageResponseBody responseBody) throws SQLException, IllegalStateException {
        if (responseBody.isSuccess()) {
            String userId = ApplicationUtils.getEncryptor().decrypt(responseBody.getKey());
            User user = userService.getUser(Integer.valueOf(userId));
            File file = new File();
            file.setName(responseBody.getFileName());
            file.setUser(user);
            file = this.fileRepository.save(file);
            user.setAvatar(responseBody.getFileName());
            userService.updateUser(user);
            return file;
        } else {
            throw new IllegalStateException("Failed to upload file");
        }
    }

    @Override
    public File createUserFile(File file) throws SQLException {
        return this.fileRepository.save(file);
    }

    @Override
    public void createPublicationFiles(List<File> files, Publication publication, User user) throws SQLException {
        for (File file : files) {
            this.fileRepository.saveFile(file, publication.getId(), user.getId());
        }
    }
}
