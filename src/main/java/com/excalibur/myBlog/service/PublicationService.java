package com.excalibur.myBlog.service;

import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.dao.wrapper.PublicationWrapper;
import com.excalibur.myBlog.form.PublicationForm;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public interface PublicationService {

    void updatePublication(Publication publication) throws Exception;

    void initPublicationForm(Integer publicationId, PublicationForm publicationForm) throws Exception;

    Publication createPublication(PublicationForm publicationForm, List<MultipartFile> files) throws Exception;

    Publication createPublication(PublicationForm publicationForm) throws Exception;

    PublicationWrapper getPublication(Integer publicationId) throws SQLException;

    void deletePublication(Publication publication);

    List<Publication> getPublication(String title) throws SQLException;

    List<Publication> getPublication(User user) throws SQLException;

    List<PublicationWrapper> getPublicationWrappers(User user);

    Iterable<Publication> getPublications();

    void deletePublication(Integer publicationId);
}
