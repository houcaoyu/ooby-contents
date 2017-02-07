package ooby.contents.test.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import ooby.contents.dao.ContentGroupCategoryDao;
import ooby.contents.dao.ContentGroupDao;
import ooby.contents.entity.Content;
import ooby.contents.entity.ContentGroup;
import ooby.contents.entity.ContentGroupCategory;
import ooby.contents.entity.Tag;
import ooby.contents.entity.User;

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = true)
public class ContentGroupDaoTest {

	@Autowired
	private ContentGroupDao dao;
	@Autowired
	private ContentGroupCategoryDao cDao;
	@Autowired
	private TestEntityManager entityManager;

	//prepare data
	@Test
	@Commit
	public void a_prepare() {
		ContentGroupCategory category = new ContentGroupCategory();
		category.setName("c1");
		cDao.save(category);

		ContentGroup group1 = new ContentGroup();
		group1.setName("aaaa");
		group1.setCategory(category);
		dao.save(group1);

		ContentGroup group2 = new ContentGroup();
		group2.setName("bbbb");
		group2.setCategory(category);
		dao.save(group2);

		ContentGroup group3 = new ContentGroup();
		group3.setName("cccc");
		dao.save(group3);

		Content content = new Content();
		content.setTitle("title");
		content.setPv(100l);
		content.setContentGroup(group1);
		entityManager.persist(content);
		content = new Content();
		content.setTitle("title");
		content.setPv(100l);
		content.setContentGroup(group1);
		entityManager.persist(content);

		content = new Content();
		content.setTitle("title");
		content.setPv(199l);
		content.setContentGroup(group2);
		entityManager.persist(content);
		entityManager.flush();
	}

	// test for findHotestByCategoryId
	@Test
	public void test2() {
		Pageable page=new PageRequest(0, 1);
		List<ContentGroup> list = dao.findHotestByCategoryId(3l,page);
		assertThat(list.size()).isEqualTo(2);
		assertThat(list.get(0).getName()).isEqualTo("aaaa");
		assertThat(list.get(1).getName()).isEqualTo("bbbb");

		Content content = entityManager.find(Content.class, 3l);
		content.setPv(300l);
		entityManager.flush();
		list = dao.findHotestByCategoryId(1l,page);
		assertThat(list.size()).isEqualTo(2);
		assertThat(list.get(0).getName()).isEqualTo("bbbb");
		assertThat(list.get(1).getName()).isEqualTo("aaaa");

		return;
	}

	// test for findHotest
	@Test
	public void test3() {
		Pageable page=new PageRequest(0, 1);
		List<ContentGroup> list = dao.findHotest(page);
		assertThat(list.size()).isEqualTo(3);
		assertThat(list.get(0).getName()).isEqualTo("aaaa");
		assertThat(list.get(1).getName()).isEqualTo("bbbb");

		Content content = entityManager.find(Content.class, 3l);
		content.setPv(300l);
		entityManager.flush();
		list = dao.findHotest(page);
		assertThat(list.size()).isEqualTo(3);
		assertThat(list.get(0).getName()).isEqualTo("bbbb");
		assertThat(list.get(1).getName()).isEqualTo("aaaa");

		return;
	}

	// test for findByUserId
	@Test
	public void test4() {
		User u = new User();
		entityManager.persist(u);

		ContentGroup g = entityManager.find(ContentGroup.class, 1l);
		g.setUser(u);
		g = entityManager.find(ContentGroup.class, 3l);
		g.setUser(u);
		entityManager.flush();

		List<ContentGroup> list = dao.findByUserId(u.getId());
		assertThat(list.size()).isEqualTo(2);
		List<Long> ids = new ArrayList<Long>();
		for (ContentGroup group : list) {
			ids.add(group.getId());
		}
		assertThat(ids).contains(1l, 3l);
	}

	// test for findByTagsId
	@Test
	public void test5() {
		Tag tag1=new Tag();
		tag1.setName("tag1");
		entityManager.persist(tag1);
		
		Tag tag2=new Tag();
		tag2.setName("tag2");
		entityManager.persist(tag2);
		
		ContentGroup g1 = entityManager.find(ContentGroup.class, 1l);
		g1.getTags().add(tag1);
		ContentGroup g2 = entityManager.find(ContentGroup.class, 2l);
		g2.getTags().add(tag2);
		ContentGroup g3 = entityManager.find(ContentGroup.class, 3l);
		g3.getTags().add(tag2);
		entityManager.flush();
		
		List<ContentGroup> list=dao.findByTagsId(tag1.getId());
		assertThat(list.size()).isEqualTo(1);
		assertThat(list.get(0).getName()).isEqualTo("aaaa");
		
		list=dao.findByTagsId(tag2.getId());
		assertThat(list.size()).isEqualTo(2);
		List<String> names = new ArrayList<String>();
		for (ContentGroup group : list) {
			names.add(group.getName());
		}
		assertThat(names).contains("bbbb","cccc");
	}
}
