package ooby.contents.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ooby.contents.entity.ContentGroup;

public interface ContentGroupDao extends CrudRepository<ContentGroup, Long>{
	@Query("select g from ContentGroup g left join g.contents c where g.category.id=?1 group by g.id order by sum(c.pv) desc")
	List<ContentGroup> findHotestByCategoryId(Long CategoryId,Pageable pageable);
	
	@Query("select g from ContentGroup g left join g.contents c group by g.id order by sum(c.pv) desc")
	List<ContentGroup> findHotest(Pageable pageable);
	
	
	List<ContentGroup> findByUserId(Long userId);
	
	List<ContentGroup> findByTagsId(Long tagId);
	
	List<ContentGroup> findBySubscribersId(Long userId);
	
	List<ContentGroup> findByCategoryId(Long categoryId);
}
