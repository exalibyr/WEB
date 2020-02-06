package com.excalibur.myBlog.service;

import com.excalibur.myBlog.dao.File;
import com.excalibur.myBlog.fileStorage.entity.FileStorageResponseBody;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public interface FileService {

    File setAvatarFile(FileStorageResponseBody responseBody) throws SQLException, IllegalStateException;

}
