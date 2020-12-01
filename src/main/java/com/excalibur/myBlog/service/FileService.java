package com.excalibur.myBlog.service;

import com.excalibur.myBlog.dao.File;
import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public interface FileService {

    File setAvatarFile(Map<String, Object> payload) throws SQLException, IllegalStateException;

    File createUserFile(File file) throws SQLException;

    void createPublicationFiles(List<File> files, Publication publication, User user) throws SQLException;

}
