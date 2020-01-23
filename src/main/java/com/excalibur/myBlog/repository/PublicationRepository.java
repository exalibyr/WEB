package com.excalibur.myBlog.repository;

import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface PublicationRepository extends CrudRepository<Publication, Integer> {

    List<Publication> findByTitle(String title);

    List<Publication> findByUser(User user);

    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM user_publication WHERE user_id = :userId ORDER BY date_time DESC", nativeQuery = true)
    List<Publication> findByUserIdOrdered(Integer userId);

}
