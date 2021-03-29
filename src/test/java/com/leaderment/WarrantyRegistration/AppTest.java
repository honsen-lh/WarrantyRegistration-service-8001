package com.leaderment.WarrantyRegistration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest {
	public static void main(String[] args) throws InterruptedException {
		String fileName = System.currentTimeMillis() + "_" + "111.jpg";
		String destFolder = "D:/workspace-new/WarrantyRegistration-service-8001/src/main/webapp";
//				+ "/uploaded"+ "/" + fileName;
		System.out.println("destFolder:"+destFolder);
		
		File destFileFolder = new File(destFolder);
		String[] list = destFileFolder.list();
		List<Integer> numList = new ArrayList<>();
		for (int i = 0; i < list.length; i++) {
//			 System.out.println(list[i]);
			 String folderName = list[i];
			 if(folderName.contains("uploaded")){
//				 System.out.println(folderName);
				 if(!folderName.equals("uploaded")){
					 String num = folderName.substring(8, folderName.length());
					 Integer parseInt = Integer.parseInt(num);
					 numList.add(parseInt);
				 }
			 }
		 }
		Collections.sort(numList,Collections.reverseOrder());
		Integer num = numList.get(0);
		String destFileName= "D:/workspace-new/WarrantyRegistration-service-8001/src/main/webapp/uploaded"+ num+"/" + fileName;
		File destFile = new File(destFileName);
		if (destFile.getParentFile().list().length <= 3) {
			System.out.println("destFile:"+destFile);
			System.out.println("length:"+destFile.getParentFile().list().length + "");
			// file.transferTo(destFile1);
		}else{
			num = num+1;
			destFileName = "D:/workspace-new/WarrantyRegistration-service-8001/src/main/webapp/uploaded"+ num+"/" + fileName;
			destFile = new File(destFileName);
			System.out.println("destFile:"+destFile);
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}
			// file.transferTo(destFile1);
			System.out.println("length:"+destFile.getParentFile().list().length + "");
		}
		// file.transferTo(destFile1);
		String path = "https://unbreakcable.net";
		String displayFileName = "/imgs"+num+"/"+ fileName;
		System.out.println(path + displayFileName);

	}
}
