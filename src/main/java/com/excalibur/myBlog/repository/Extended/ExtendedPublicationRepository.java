package com.excalibur.myBlog.repository.Extended;

import com.excalibur.myBlog.dao.Publication;

import java.sql.SQLException;
import java.util.Optional;

public interface ExtendedPublicationRepository {

    Optional<Publication> findByIdWithFiles(Integer publicationId) throws SQLException;

}
