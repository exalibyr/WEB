package com.excalibur.myBlog.service.Impl;

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

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserService userService;

    @Override
    public File setAvatarFile(FileStorageResponseBody responseBody) throws SQLException, IllegalStateException {
        if (responseBody.getSuccess()) {
            String userId = ApplicationUtils.getEncryptor().decrypt(responseBody.getKey());
            User user = userService.getUser(Integer.valueOf(userId));
            File file = new File();
            file.setName(responseBody.getFileName());
            file.setUser(user);
            file.setId(fileRepository.saveFile(file));
            user.setAvatar(responseBody.getFileName());
            userService.updateUser(user);
            return file;
        } else {
            throw new IllegalStateException("Failed to upload file");
        }
    }
}
