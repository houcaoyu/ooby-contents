package ooby.contents.dao;

import org.springframework.data.repository.CrudRepository;

import ooby.contents.entity.TemporaryFile;

public interface TemporaryFileDao extends CrudRepository<TemporaryFile, String>{

}
