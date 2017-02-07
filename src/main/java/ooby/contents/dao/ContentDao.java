package ooby.contents.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import ooby.contents.entity.Content;

public interface ContentDao extends CrudRepository<Content, Long>{
	List<Content> findByOrderByPvDesc(Pageable pageable);
	
	List<Content> findByContentGroupUserId(Long userId);
	
	List<Content> findByCollectedUsersId(Long userId);
	
	List<Content> findByFansId(Long userId);
	
	List<Content> findByReadersId(Long userId);
}
