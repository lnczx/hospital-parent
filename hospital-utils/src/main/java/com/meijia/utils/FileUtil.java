package com.meijia.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class FileUtil {

	/**
	 * 获取文件内容
	 * @param file 文件对象
	 * @param encoding 编码格式，UTF-8
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String getFileContent(File file, String encoding) {
		String content = "";
		BufferedReader reader = null;
		
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF-8"); 
			reader = new BufferedReader( read );
			String tempString = null;
			
			//读取简历内容
			while ( (tempString = reader.readLine()) != null ) {
				content += tempString;
			}
			
			return content;
		} catch (IOException e) {
//			e.printStackTrace();
			return content;
		} finally {
			if (null != reader) {
				try{
					reader.close();
				} catch (IOException e) {
//					System.out.println(e.getMessage());
					return content;
				}
			}
			return content;
		}// end finally
	}	
	
	public static byte[] getBytes(String filePath) throws IOException {
		FileInputStream in = new FileInputStream(filePath);

		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);

		System.out.println("bytes available:" + in.available());

		byte[] temp = new byte[1024];

		int size = 0;

		while ((size = in.read(temp)) != -1) {
			out.write(temp, 0, size);
		}

		in.close();

		byte[] bytes = out.toByteArray();
		System.out.println("bytes size got is:" + bytes.length);

		return bytes;
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtend(String filename) {
		return getExtend(filename, "");
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtend(String filename, String defExt) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');

			if ((i > 0) && (i < (filename.length() - 1))) {
				return (filename.substring(i + 1)).toLowerCase();
			}
		}
		return defExt.toLowerCase();
	}

	/**
	 * 获取文件名称[不含后缀名]
	 * 
	 * @param
	 * @return String
	 */
	public static String getFilePrefix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(0, splitIndex).replaceAll("\\s*", "");
	}

	/**
	 * 获取文件名称[不含后缀名] 不去掉文件目录的空格
	 * 
	 * @param
	 * @return String
	 */
	public static String getFilePrefix2(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(0, splitIndex);
	}

	/**
	 * 文件复制 方法摘要：这里一句话描述方法的用途
	 *
	 * @param
	 * @return void
	 */
	public static void copyFile(String inputFile, String outputFile) throws FileNotFoundException {
		File sFile = new File(inputFile);
		File tFile = new File(outputFile);
		FileInputStream fis = new FileInputStream(sFile);
		FileOutputStream fos = new FileOutputStream(tFile);
		InputStreamReader in = null;
		OutputStreamWriter out = null;
		int temp = 0;
		char[] buf = new char[2048];

		try {
			in = new InputStreamReader(fis, "UTF-8");
			out = new OutputStreamWriter(fos, "UTF-8");
			while ((temp = in.read(buf)) != -1) {
				out.write(buf, 0, temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void fileDownload(HttpServletRequest request, HttpServletResponse response, String fileName, String filePath) {

		// 获取网站部署路径(通过ServletContext对象)，用于确定下载文件位置，从而实现下载
		// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
		response.setContentType("multipart/form-data");
		// 2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)

		final String userAgent = request.getHeader("USER-AGENT");

		ServletOutputStream out;
		// 通过文件路径获得File对象(假如此路径中有一个download.pdf文件)

		try {
			File file = new File(filePath + fileName);
			fileName = encodeChineseDownloadFileName(request, fileName);

			response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);

			FileInputStream inputStream = new FileInputStream(file);

			// 3.通过response获取ServletOutputStream对象(out)
			out = response.getOutputStream();

			int b = 0;
			byte[] buffer = new byte[512];
			while (b != -1) {
				b = inputStream.read(buffer);
				// 4.写到输出流(out)中
				out.write(buffer, 0, b);
			}
			inputStream.close();
			out.close();
			out.flush();

		} catch (UnsupportedEncodingException e) {  
	        e.printStackTrace();  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String encodeChineseDownloadFileName(  
            HttpServletRequest request, String pFileName) throws UnsupportedEncodingException {  
          
         String filename = null;    
            String agent = request.getHeader("USER-AGENT");    
            if (null != agent){    
                if (-1 != agent.indexOf("Firefox")) {//Firefox    
                    filename = "=?UTF-8?B?" + (new String(org.apache.commons.codec.binary.Base64.encodeBase64(pFileName.getBytes("UTF-8"))))+ "?=";    
                }else if (-1 != agent.indexOf("Chrome")) {//Chrome    
                    filename = new String(pFileName.getBytes(), "ISO8859-1");    
                } else {//IE7+    
                    filename = java.net.URLEncoder.encode(pFileName, "UTF-8");    
                    filename = StringUtils.replace(filename, "+", "%20");//替换空格    
                }    
            } else {    
                filename = pFileName;    
            }    
            return filename;   
    }  

	public static void main(String[] args) throws Exception {

	}
}