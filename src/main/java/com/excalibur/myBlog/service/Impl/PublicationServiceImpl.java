package com.excalibur.myBlog.service.Impl;

import com.excalibur.myBlog.service.PublicationService;
import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.repository.PublicationRepository;
import com.excalibur.myBlog.dao.wrapper.PublicationWrapper;
import com.excalibur.myBlog.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PublicationServiceImpl implements PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    public void createPublication(Publication publication){
        publicationRepository.save(publication);
    }

    public PublicationWrapper getPublication(Integer publicationId) throws IOException {
        Optional<Publication> optional = publicationRepository.findById(publicationId);
        if (optional.isPresent()) {
            return new PublicationWrapper(optional.get());
        } else {
            throw new IOException("No data found");
        }
    }

    public void deletePublication(Publication publication){
        publicationRepository.delete(publication);
    }

    public List<Publication> getPublication(String title){
        return publicationRepository.findByTitle(title);
    }

    public List<Publication> getPublication(User user){
        return publicationRepository.findByUser(user);
    }

    public List<PublicationWrapper> getUserPublications(User user) {
        return ApplicationUtils.getFormattedPublications(
                publicationRepository.findByUserIdOrderByDateTimeDesc(user.getId()).orElseGet(ArrayList::new)
        );
    }

    public Iterable<Publication> getPublications(){
        return publicationRepository.findAll();
    }

    public void deletePublication(Integer publicationId){
        publicationRepository.deleteById(publicationId);
    }

}
