package com.leaderment.claim.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.leaderment.constant.StatusCode;
import com.leaderment.entity.ResultBean;

@Controller
@CrossOrigin
@RequestMapping(value = "/upload")
public class UploadPhotoHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadPhotoHandler.class);

	/**
	 * 官网用户上传照片，视频，上传到47.91.78.254服务器的/var/imgs文件夹下
	 * 
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean uploadPic(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		ResultBean resultBean = new ResultBean();
		try {
			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			String destFolder = File.separator + "var" ;
			//选出var文件夹下imgs文件夹最大的一个 例如：imgs3
			File destFileFolder = new File(destFolder);
			String[] list = destFileFolder.list();
			List<Integer> numList = new ArrayList<>();
			for (int i = 0; i < list.length; i++) {
				 String folderName = list[i];
				 if(folderName.contains("imgs")){
					 if(!folderName.equals("imgs")){
						 String num = folderName.substring(4, folderName.length());
						 Integer parseInt = Integer.parseInt(num);
						 numList.add(parseInt);
					 }
				 }
			 }
			Collections.sort(numList,Collections.reverseOrder());
			Integer num = numList.get(0);
			String destFileName= File.separator + "var" + File.separator + "imgs" +num+ File.separator + fileName;
			File destFile = new File(destFileName);
			if (destFile.getParentFile().list().length <= 3000) {
				System.out.println("destFile:"+destFile);
				System.out.println("length:"+destFile.getParentFile().list().length + "");
				//把浏览器上传的文件复制到希望的位置
				file.transferTo(destFile);
			}else{
				num = num+1;
				destFileName = File.separator + "var" + File.separator + "imgs" +num+ File.separator + fileName;
				destFile = new File(destFileName);
				System.out.println("destFile:"+destFile);
				if (!destFile.getParentFile().exists()) {
					destFile.getParentFile().mkdirs();
				}
				//把浏览器上传的文件复制到希望的位置
				file.transferTo(destFile);
				System.out.println("length:"+destFile.getParentFile().list().length + "");
			}
			// 把浏览器上传的文件复制到希望的位置
//			file.transferTo(destFile);
			String path = "https://unbreakcable.net";
			String displayFileName = "/imgs"+num +"/"+ fileName;
			resultBean.setData(path + displayFileName);
			LOGGER.info("--------------------------上传成功！文件信息如下:--------------------------");
			LOGGER.info("文件类型：" + file.getContentType());
			LOGGER.info("原始文件名：" + file.getOriginalFilename());
			LOGGER.info("文件大小：" + file.getSize() / 1024 + "Kb");
			LOGGER.info("文件位置：" + destFileName);
			LOGGER.info("--------------------------------------------------------------------");
		} catch (Exception e) {
			resultBean.setCode(StatusCode.HTTP_FAILURE);
			resultBean.setMsg("upload Failed！");
			LOGGER.error("参数信息：", e);
		}
		return resultBean;
	}
}
