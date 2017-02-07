package ooby.contents.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ooby.contents.entity.ResourceRepository;

public interface ResourceRepositoryDao extends CrudRepository<ResourceRepository, String>{
	@Modifying
	@Query("insert into ResourceRepository (id,extension,file) select temp.id,temp.extension,temp.file from TemporaryFile temp where id=?1")
	public void copyTemporaryFile(String id);
}
