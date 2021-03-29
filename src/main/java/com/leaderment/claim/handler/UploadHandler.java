package com.leaderment.claim.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.leaderment.config.AliOSSUtil;
import com.leaderment.entity.ResultBean;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value="/uploadFile")
public class UploadHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadHandler.class);
	@Autowired
	AliOSSUtil aliOSSUtil;
	//linux系统
	private  String property = "/";
	@PostMapping("/uploadFiles")
	public ResultBean uploadFiles(@RequestParam(value = "file") MultipartFile file) {
		ResultBean resultBean = new ResultBean();
		LOGGER.info("filename {} " + file.getOriginalFilename());
		try {
			//1：校验文件的合法性
			resultBean = checkFile(file);

			if (resultBean.getCode().equals(500)) {
				return resultBean;
			}

			String suffixPath =  "static" + property + "curriculumFile" ;

			LOGGER.info("suffixPath {}",suffixPath);
			File file1 = new File(System.currentTimeMillis() + "_"  +file.getOriginalFilename());

			FileUtils.copyInputStreamToFile(file.getInputStream(),file1);

			System.out.println("fileName = " + file1.getName());

			//加上随机数 防止oss中文件同名
			String fullPath = suffixPath+property+file1.getName();
			LOGGER.info("fullPath {}",fullPath);
			aliOSSUtil.createOssClient();
			String pathStr = aliOSSUtil.uploadFile1(file1,aliOSSUtil.getBucketName(),fullPath);

			if(file1.exists()){
				//上传后删除本地
				file1.delete();
			}

			if(StringUtils.isNotEmpty(pathStr)){
				LOGGER.info("上传成功 url {} ",pathStr);
			}

			resultBean.setData( pathStr);


		}catch (Exception e) {
			e.printStackTrace();
			resultBean.setCode(500);
			resultBean.setMsg("Request upload Failed！");
			LOGGER.error("上传图片或文件失败！", e);
		}finally {
			aliOSSUtil.destory();
		}
		return resultBean;
	}
	private ResultBean checkFile(MultipartFile file) {
		ResultBean resultBean = new ResultBean();

		if(file == null ){
			resultBean.setCode(500);
			resultBean.setMsg("上传文件为空");
		}
		//文件类型校验等等

		return resultBean;
	}

}
