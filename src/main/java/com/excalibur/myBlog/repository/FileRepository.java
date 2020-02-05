package com.excalibur.myBlog.repository;

import com.excalibur.myBlog.dao.File;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.repository.Extended.ExtendedFileRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface FileRepository extends CrudRepository<File, Long>, ExtendedFileRepository {


}
