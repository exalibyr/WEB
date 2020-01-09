package com.excalibur.myBlog.repository;

import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PublicationRepository extends CrudRepository<Publication, Integer> {

    List<Publication> findByTitle(String title);

    List<Publication> findByUser(User user);

}
