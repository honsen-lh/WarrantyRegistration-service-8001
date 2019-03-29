package com.leaderment.claim.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value="/uploadTest")
public class UploadHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadHandler.class);
	
	@RequestMapping(value="/uploadPage",method=RequestMethod.GET)
    public String uploadPage() {
        return "uploadPic";   //过度跳转页
    }
	@RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
	@ResponseBody
	public String uploadPic(HttpServletRequest request,@RequestParam("file") MultipartFile file,Model m) {
		String destFileName ="";
		try {
			
			String fileName = System.currentTimeMillis()+"_"+ file.getOriginalFilename();
			 destFileName = request.getServletContext().getRealPath("") + "uploaded" + File.separator + fileName;
			 File destFile = new File(destFileName);
		        if(!destFile.getParentFile().exists()){    
		        	destFile.getParentFile().mkdirs();    //创建目录；
		        }
		     //把浏览器上传的文件复制到希望的位置
		        file.transferTo(destFile);   
		     m.addAttribute("fileName", fileName);
		    LOGGER.info("文件类型："+file.getContentType());
			LOGGER.info("原始文件名："+file.getOriginalFilename());
			LOGGER.info("文件大小："+file.getSize());
			LOGGER.info("是否为空："+file.isEmpty());
			LOGGER.info("--------------------------------------------------------------------");
		
		} catch (FileNotFoundException e) {
            e.printStackTrace();
            return "上传失败," + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败," + e.getMessage();
        }
		return destFileName;
	}
}
