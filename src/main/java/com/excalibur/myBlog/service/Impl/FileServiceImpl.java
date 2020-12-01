package com.excalibur.myBlog.service.Impl;

import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.service.FileService;
import com.excalibur.myBlog.dao.File;
import com.excalibur.myBlog.repository.FileRepository;
import com.excalibur.myBlog.service.UserService;
import com.excalibur.myBlog.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserService userService;

    @Override
    public File setAvatarFile(Map<String, Object> payload) throws SQLException, IllegalStateException {
        if ((Boolean) payload.get("success")) {
            String userId = ApplicationUtils.getEncryptor().decrypt((String) payload.get("key"));
            User user = userService.getUser(Integer.valueOf(userId));
            File file = new File();
            String fileName = (String) payload.get("fileName");
            file.setName(fileName);
            file.setUser(user);
            file = this.fileRepository.save(file);
            user.setAvatar(fileName);
            userService.updateUser(user);
            return file;
        } else {
            throw new IllegalStateException("Failed to upload file. " + payload.get("message"));
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
