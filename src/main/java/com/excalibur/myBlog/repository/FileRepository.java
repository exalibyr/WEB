package com.excalibur.myBlog.repository;

import com.excalibur.myBlog.dao.File;
import com.excalibur.myBlog.repository.Extended.ExtendedFileRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface FileRepository extends CrudRepository<File, Long>, ExtendedFileRepository {


}
