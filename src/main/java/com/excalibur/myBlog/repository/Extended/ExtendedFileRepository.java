package com.excalibur.myBlog.repository.Extended;

import com.excalibur.myBlog.dao.File;

import javax.validation.constraints.NotNull;
import java.sql.SQLException;

public interface ExtendedFileRepository {

    int saveFile(@NotNull File file, @NotNull Integer publicationId, @NotNull Integer userId) throws SQLException;

}
