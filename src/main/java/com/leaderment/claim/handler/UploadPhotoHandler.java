package com.leaderment.claim.handler;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.leaderment.constant.StatusCode;
import com.leaderment.entity.ResultBean;

@Controller
@RequestMapping(value="/upload")
public class UploadPhotoHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadPhotoHandler.class);
	@RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean uploadPic(HttpServletRequest request,@RequestParam("file") MultipartFile file) {
		ResultBean resultBean = new ResultBean();
		try {
			String fileName = System.currentTimeMillis()+"_"+ file.getOriginalFilename();
			 String destFileName = request.getServletContext().getRealPath("") + "uploaded" + File.separator + fileName;
			 File destFile = new File(destFileName);
		        if(!destFile.getParentFile().exists()){    
		        	destFile.getParentFile().mkdirs();//创建目录
		        }
		     //把浏览器上传的文件复制到希望的位置
		     file.transferTo(destFile); 
		     String displayFileName = "/uploaded/"+fileName;
		     resultBean.setData(displayFileName);
		    LOGGER.info("--------------------------------------------------------------------");
		    LOGGER.info("文件类型："+file.getContentType());
			LOGGER.info("原始文件名："+file.getOriginalFilename());
			LOGGER.info("文件大小："+file.getSize());
			LOGGER.info("是否为空："+file.isEmpty());
			LOGGER.info("文件位置："+destFileName);
			LOGGER.info("--------------------------------------------------------------------");
		} catch (Exception e) {
			resultBean.setCode(StatusCode.HTTP_FAILURE);
			resultBean.setMsg("upload Failed！");
			LOGGER.error("参数信息：",e);
        } 
		return resultBean;
	}
}
