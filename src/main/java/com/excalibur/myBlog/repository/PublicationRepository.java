package com.excalibur.myBlog.repository;

import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PublicationRepository extends CrudRepository<Publication, Integer> {

    Optional<List<Publication>> findByTitle(String title);

    Optional<List<Publication>> findByUser(User user);

    Optional<Publication> findById(Integer id);

    Optional<List<Publication>> findByUserIdOrderByDateTimeDesc(Integer userId);

}
