package com.excalibur.myBlog.service;

import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.utils.PublicationWrapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface PublicationService {

    void saveNewPublication(Publication publication);

    PublicationWrapper getPublicationById(Integer id) throws IOException;

    void deletePublication(Publication publication);

    List<Publication> findPublicationsByTitle(String title);

    List<Publication> findPublicationsByUser(User user);

    List<PublicationWrapper> getUserPublications(User user);

    Iterable<Publication> findAllPublications();

    void deletePublicationById(Integer id);
}
