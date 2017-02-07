package ooby.contents.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ooby.contents.entity.User;

public interface UserDao extends CrudRepository<User, Long>{
	@Query("select u from User u left join u.contentGroups g left join g.contents c group by u.id order by sum(c.pv) desc")
	List<User> findHotestUsers(Pageable pageable);
	
	List<User> findByFollowersId(Long userId);
	
	User findByLoginNameAndPassword(String loginName,String password);
	
	User findByLoginName(String loginName);
}
