package com.excalibur.myBlog.service.Impl;

import com.excalibur.myBlog.form.PublicationForm;
import com.excalibur.myBlog.service.PublicationService;
import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.repository.PublicationRepository;
import com.excalibur.myBlog.dao.wrapper.PublicationWrapper;
import com.excalibur.myBlog.service.UserService;
import com.excalibur.myBlog.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublicationServiceImpl implements PublicationService {

    @Autowired
    private UserService userService;

    @Autowired
    private PublicationRepository publicationRepository;

    @Override
    public void updatePublication(Publication publication) throws Exception {
        publication.setDateTime(ZonedDateTime.now());
        publicationRepository.save(publication);
    }

    @Override
    public void initPublicationForm(Integer publicationId, PublicationForm publicationForm) throws Exception {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new SQLException("PublicationServiceImpl.getPublicationForm(Integer publicationId): No data found"));
        publicationForm = new PublicationForm();
        publicationForm.setContent(publication.getContent());
        publicationForm.setTitle(publication.getTitle());
        publicationForm.setPublicationId(publication.getId());
        publicationForm.setUsername(publication.getUser().getUsername());
    }

    @Override
    public void createPublication(PublicationForm publicationForm) throws Exception {
        Publication publication = publicationForm.getPublication();
        publication.setUser(userService.getUser(publicationForm.getUsername()));
        publication.setDateTime(ZonedDateTime.now());
        publicationRepository.save(publication);
    }

    public PublicationWrapper getPublication(Integer publicationId) throws SQLException {
        Optional<Publication> publication = publicationRepository.findById(publicationId);
        if (publication.isPresent()) {
            return new PublicationWrapper(publication.get());
        } else {
            throw new SQLException("PublicationServiceImpl.getPublication(Integer publicationId): No data found");
        }
    }

    public void deletePublication(Publication publication){
        publicationRepository.delete(publication);
    }

    public List<Publication> getPublication(String title) throws SQLException {
        return publicationRepository.findByTitle(title)
                .orElseThrow(() -> new SQLException("PublicationServiceImpl.getPublication(String title): No data found"));
    }

    public List<Publication> getPublication(User user) throws SQLException {
        return publicationRepository.findByUser(user)
                .orElseThrow(() -> new SQLException("PublicationServiceImpl.getPublication(User user): No data found"));
    }

    public List<PublicationWrapper> getPublicationWrappers(User user) {
        return publicationRepository
                .findByUserIdOrderByDateTimeDesc(user.getId())
                .orElseGet(ArrayList::new)
                .stream()
                .map(PublicationWrapper::new)
                .collect(Collectors.toList());
    }

    public Iterable<Publication> getPublications(){
        return publicationRepository.findAll();
    }

    public void deletePublication(Integer publicationId){
        publicationRepository.deleteById(publicationId);
    }

}
