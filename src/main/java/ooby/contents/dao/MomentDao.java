package ooby.contents.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ooby.contents.entity.Moment;

public interface MomentDao extends CrudRepository<Moment, Long>{
	List<Moment> findByFromUserFollowersId(Long userId);
	
	Long countByToUserIdAndTypeAndDateTimeAfter(Long userId,String type,Date dateTime);
	
	List<Moment> findByToUserIdAndTypeAndDateTimeAfter(Long userId,String type,Date dateTime);
}
