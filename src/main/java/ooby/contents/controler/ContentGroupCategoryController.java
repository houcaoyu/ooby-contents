package ooby.contents.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import ooby.contents.dao.ContentGroupCategoryDao;
import ooby.contents.entity.ContentGroupCategory;
import ooby.contents.view.ContentGroupCategoryView;

@Controller
@RequestMapping("/cgc")
public class ContentGroupCategoryController {
	@Autowired
	private ContentGroupCategoryDao dao;
	
	@JsonView(ContentGroupCategoryView.Base.class)
	@PostMapping()
	@ResponseBody
	public ContentGroupCategory create(ContentGroupCategory cgc){
		return dao.save(cgc);
	}
	@JsonView(ContentGroupCategoryView.Base.class)
	@GetMapping("/{id}")
	@ResponseBody
	public ContentGroupCategory get(@PathVariable Long id){
		return dao.findOne(id);
	}
	
	@JsonView(ContentGroupCategoryView.Base.class)
	@PutMapping()
	@ResponseBody
	public ContentGroupCategory update(ContentGroupCategory cgc){
		return dao.save(cgc);
	}
	@DeleteMapping("/{id}")
	@ResponseBody
	public void delete(@PathVariable Long id){
		dao.delete(id);
	}
	
	@JsonView(ContentGroupCategoryView.Base.class)
	@GetMapping
	@ResponseBody
	public Iterable<ContentGroupCategory> query(){
		return dao.findAll();
	}
	
	
}
