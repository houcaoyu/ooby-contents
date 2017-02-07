package ooby.contents.controler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ooby.contents.dao.ContentDao;
import ooby.contents.dao.ContentGroupDao;
import ooby.contents.dao.TemporaryFileDao;
import ooby.contents.dao.UserDao;
import ooby.contents.entity.Content;
import ooby.contents.entity.ContentGroup;
import ooby.contents.entity.FileIF;
import ooby.contents.entity.TemporaryFile;
import ooby.contents.entity.User;
import ooby.contents.utils.App;

@Controller
@RequestMapping("/file")
public class FileController {
	@Autowired
	private App app;
	@Autowired
	private ContentGroupDao contentGroupDao;
	@Autowired
	private ContentDao contentDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private TemporaryFileDao temporaryFileDao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@PreAuthorize("authenticated")
	@PostMapping
	@ResponseBody
	@Transactional
	public Map<String,Object> upload(MultipartFile file,HttpSession session) throws IOException{
		if (!file.isEmpty()) {
            // store the bytes 
			TemporaryFile tempFile=new TemporaryFile();
			tempFile.setDateTime(new Date());
			
			User user=userDao.findOne(app.getCurrentUser().getId());
			tempFile.setUser(user);
			String extension=StringUtils.getFilenameExtension(file.getOriginalFilename());
			if(extension==null)
				extension="";
			
			tempFile.setExtension(extension.toLowerCase());
			
			tempFile.setFileName(file.getOriginalFilename());
			tempFile.setFile(Hibernate.getLobCreator(sessionFactory.getCurrentSession()).createBlob(file.getInputStream(), file.getSize()));
			temporaryFileDao.save(tempFile);
			Map<String,Object> map=new HashMap<String, Object>();
            map.put("id", tempFile.getId());
			
//            String path = app.getTempPath(session);
//            String targetName=UUID.randomUUID().toString();
//            File target=new File(path,targetName);
//            file.transferTo(target); 
//            Map<String,Object> map=new HashMap<String, Object>();
//            map.put("name", targetName);
            return map;
        }
		return null;
	}
	
	@GetMapping("/contentgroup/{id}")
	public void contentGroupImage(@PathVariable Long id,HttpServletResponse response) {
		ContentGroup contentGroup=contentGroupDao.findOne(id);
		writeByte(response,contentGroup.getImage());
	}
	@GetMapping("/content/{id}")
	public void contentImage(@PathVariable Long id,HttpServletResponse response) {
		Content content=contentDao.findOne(id);
		writeByte(response,content.getImage());
	}
	@GetMapping("/user/{id}")
	public void userImage(@PathVariable Long id,HttpServletResponse response) {
		User user=userDao.findOne(id);
		writeByte(response,user.getImage());
	}
	@GetMapping("/temp/{id}")
	public void tempImage(@PathVariable String id,HttpServletResponse response) {
		TemporaryFile temp=temporaryFileDao.findOne(id);
		writeByte(response,temp);
	}
	
	private void writeByte(HttpServletResponse response,FileIF resource){
		String contentType=null;
		if(resource.getExtension().equals("jpg"))
			contentType="img/jpeg";
		else if(resource.getExtension().equals("gif"))
			contentType="img/gif";
		else if(resource.getExtension().equals("png"))
			contentType="img/png";
		response.setContentType(contentType);
		InputStream input=null;
		OutputStream output=null;
		try {
			input=resource.getFile().getBinaryStream();
			output=response.getOutputStream();
			int len=0;  
            byte[]buf=new byte[1024];  
            while((len=input.read(buf,0,1024))!=-1){  
                output.write(buf, 0, len);  
            }  
            output.flush();
            output.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try{
				if(output!=null)
					output.close();
				if(input!=null)
					input.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
