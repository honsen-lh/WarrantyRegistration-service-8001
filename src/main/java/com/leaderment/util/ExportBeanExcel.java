package com.leaderment.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExportBeanExcel<T> {

	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ssss");
    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出
     *
     * title         表格标题名
     * headersName  表格属性列名数组
     * headersId    表格属性列名对应的字段---你需要导出的字段名（为了更灵活控制你想要导出的字段）
     *  dtoList     需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象
     *  out         与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     */
    public String exportExcel(String title, List<String> headersName,List<String> headersId,List<T> dtoList,String md5String) {
    	String fileName="";
        /*（一）表头--标题栏*/
        Map<Integer, String> headersNameMap = new HashMap<>();
        int key=0;
        for (int i = 0; i < headersName.size(); i++) {
            if (!headersName.get(i).equals(null)) {
                headersNameMap.put(key, headersName.get(i));
                key++;
            }
        }
        /*（二）字段*/
        Map<Integer, String> titleFieldMap = new HashMap<>();
        int value = 0;
        for (int i = 0; i < headersId.size(); i++) {
            if (!headersId.get(i).equals(null)) {
                titleFieldMap.put(value, headersId.get(i));
                value++;
            }
        }
        /* （三）声明一个工作薄：包括构建工作簿、表格、样式*/
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(title);
        sheet.setDefaultColumnWidth((short)15);
        // 生成一个样式
        //HSSFCellStyle style = wb.createCellStyle();
        XSSFRow row = sheet.createRow(0);
        //style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        XSSFCell cell;
        Collection c = headersNameMap.values();//拿到表格所有标题的value的集合
        Iterator<String> it = c.iterator();//表格标题的迭代器
        /*（四）导出数据：包括导出标题栏以及内容栏*/
        //根据选择的字段生成表头
        short size = 0;
        while (it.hasNext()) {
            cell = row.createCell(size);
            cell.setCellValue(it.next().toString());
            //cell.setCellStyle(style);
            size++;
        }
        //表格标题一行的字段的集合
        Collection zdC = titleFieldMap.values();
        Iterator<T> labIt = dtoList.iterator();//总记录的迭代器
        int zdRow =0;//列序号
        while (labIt.hasNext()) {//记录的迭代器，遍历总记录
            int zdCell = 0;
            zdRow++;
            row = sheet.createRow(zdRow);
            T l = (T) labIt.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = l.getClass().getDeclaredFields();//获得JavaBean全部属性
            for (short i = 0; i < fields.length; i++) {//遍历属性，比对
                Field field = fields[i];
                String fieldName = field.getName();//属性名
                Iterator<String> zdIt = zdC.iterator();//一条字段的集合的迭代器
                while (zdIt.hasNext()) {//遍历要导出的字段集合
                    if (zdIt.next().equals(fieldName)) {//比对JavaBean的属性名，一致就写入，不一致就丢弃
                        String getMethodName = "get"
                                + fieldName.substring(0, 1).toUpperCase()
                                + fieldName.substring(1);//拿到属性的get方法
                        Class tCls = l.getClass();//拿到JavaBean对象
                        try {
                            Method getMethod = tCls.getMethod(getMethodName,
                                    new Class[] {});//通过JavaBean对象拿到该属性的get方法，从而进行操控
                            Object val = getMethod.invoke(l, new Object[] {});//操控该对象属性的get方法，从而拿到属性值
                            String textVal = null;
                            if (val!= null) {
                            	if(val.getClass()==Date.class){
                            		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            		textVal = String.valueOf(sdf.format(val));//转化成String
                            		//System.err.println("77777");
                            	}else{
                            		textVal = String.valueOf(val);//转化成String
                            	}
                            }else{
                                textVal = null;
                            }
                            row.createCell((short) zdCell).setCellValue(textVal);//写进excel对象
                            zdCell++;
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        try {
        	Date d=new Date();
        	String filePath="";
        	String osName = System.getProperties().getProperty("os.name");  
            if(osName.equals("Linux")){  
            	filePath="/usr/local/inventory-setting/";
            }  
            else{  
            	filePath="d://inventory-setting//";
            } 
            judeDirExists(new File(filePath));
            fileName=filePath+sdf2.format(d)+md5String+".xlsx";
            FileOutputStream exportXls = new FileOutputStream(fileName);
            //FileOutputStream exportXls = new FileOutputStream(filePath+"a.xlsx");
            wb.write(exportXls);
            exportXls.close();
            //System.out.println("导出成功!");
        } catch (FileNotFoundException e) {
           // System.out.println("导出失败!");
            e.printStackTrace();
        } catch (IOException e) {
           // System.out.println("导出失败!");
            e.printStackTrace();
        }
        return fileName;
    }
 // 判断文件夹是否存在
    public static void judeDirExists(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                //System.out.println("dir exists");
            } else {
                //System.out.println("the same name file exists, can not create dir");
            }
        } else {
            //System.out.println("dir not exists, create it ...");
            file.mkdir();
        }
    }
    public void download(String path, HttpServletResponse response) {
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/vnd.ms-excel;charset=gb2312");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
    }
    //解析单元格并返回值
    public static String getValueFromCell(Cell cell){
    	DecimalFormat df = new DecimalFormat("0");
    	String cellValue="";
    	int cellType=cell.getCellType();
    	switch (cellType) {
		case 0://Numeric
			cellValue=df.format(cell.getNumericCellValue());
			break;
		case 1://String
			cellValue=cell.getStringCellValue();
			break;
		default:
			break;
		}
		return cellValue;
    }
    /*
        使用例子
    */
    public static void main(String [] args){
    	
    	//列名
        List<String> listName = new ArrayList<>();
        listName.add("sellerId");
        listName.add("countryId");
        listName.add("productId");
        listName.add("ModelNO");
        listName.add("ASIN");
        listName.add("BusinessUnit");
        listName.add("Saleable");
        listName.add("Shipped");
        listName.add("Total");
        listName.add("UnitsAvg");
        listName.add("EstUnits");
        listName.add("AppUnits");
        listName.add("Shipped Days");
        listName.add("Total Days");
        listName.add("Ensure Days");
        listName.add("Replenish Days");
        listName.add("Delivery Days");
        listName.add("Gap Days");
        listName.add("Send Day");
        listName.add("Advise Quantity");
        listName.add("Is Urgent");
        
        //属性名  ，这里属性名对应列，并且表格渲染顺序不按这里的顺序，是按javabean实体类的顺序
        List<String> listId = new ArrayList<>();
        listId.add("sellerId");
        listId.add("countryId");
        listId.add("productId");
        listId.add("productSku");
        listId.add("asin");
        listId.add("businessUnitName");
        listId.add("saleable");
        listId.add("shipped");
        listId.add("total");
        listId.add("unitsAvg");
        listId.add("estUnits");
        listId.add("appUnits");
        listId.add("shippedDays");
        listId.add("totalDays");
        listId.add("ensureDays");
        listId.add("replenishDays");
        listId.add("deliveryDays");
        listId.add("gapDays");
        listId.add("sendDay");
        listId.add("adviseQuantity");
        listId.add("isUrgent");
        /*List<AmzInventoryUnitSettingVO> list = new ArrayList<>();
        list.add(new AmzInventoryUnitSettingVO(3, 8, 1,"SW-01","B00N1O1NQW","Cable BU [SYNCWIRE]"
        		,1,2,3,4,5,6,
        		7,8,9,10,11, 
        		12,new Date(), 14, "urgent"
    			));


        ExportBeanExcel<AmzInventoryUnitSettingVO> exportBeanExcelUtil = new ExportBeanExcel();
        exportBeanExcelUtil.exportExcel("测试POI导出EXCEL文档",listName,listId,list,MD5Util.getMd5("1"));*/

    }
	
}
