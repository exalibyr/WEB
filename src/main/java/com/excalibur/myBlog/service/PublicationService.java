package com.excalibur.myBlog.service;

import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.dao.wrapper.PublicationWrapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface PublicationService {

    void createPublication(Publication publication);

    PublicationWrapper getPublication(Integer publicationId) throws IOException;

    void deletePublication(Publication publication);

    List<Publication> getPublication(String title);

    List<Publication> getPublication(User user);

    List<PublicationWrapper> getUserPublications(User user);

    Iterable<Publication> getPublications();

    void deletePublication(Integer publicationId);
}
