package com.excalibur.myBlog.service.Impl;

import com.excalibur.myBlog.service.PublicationService;
import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.repository.PublicationRepository;
import com.excalibur.myBlog.utils.PublicationWrapper;
import com.excalibur.myBlog.utils.ZonedDateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PublicationServiceImpl implements PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    public void saveNewPublication(Publication publication){
        publicationRepository.save(publication);
    }

    public PublicationWrapper getPublicationById(Integer id) throws IOException {
        Optional<Publication> optional = publicationRepository.findById(id);
        if (optional.isPresent()) {
            return new PublicationWrapper(optional.get());
        } else {
            throw new IOException("No data found");
        }
    }

    public void deletePublication(Publication publication){
        publicationRepository.delete(publication);
    }

    public List<Publication> findPublicationsByTitle(String title){
        return publicationRepository.findByTitle(title);
    }

    public List<Publication> findPublicationsByUser(User user){
        return publicationRepository.findByUser(user);
    }

    public List<PublicationWrapper> getUserPublications(User user) {
        return ZonedDateTimeFormatter.getFormattedPublications(publicationRepository.findByUserIdOrdered(user.getId()));
    }

    public Iterable<Publication> findAllPublications(){
        return publicationRepository.findAll();
    }

    public void deletePublicationById(Integer id){
        publicationRepository.deleteById(id);
    }

}
