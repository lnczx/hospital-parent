package com.meijia.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileUtil {

	/**
	 * 获取文件内容
	 * 
	 * @param file
	 *            文件对象
	 * @param encoding
	 *            编码格式，UTF-8
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String getFileContent(File file, String encoding) {
		String content = "";
		BufferedReader reader = null;

		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
			reader = new BufferedReader(read);
			String tempString = null;

			// 读取简历内容
			while ((tempString = reader.readLine()) != null) {
				content += tempString;
			}

			return content;
		} catch (IOException e) {
			// e.printStackTrace();
			return content;
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					// System.out.println(e.getMessage());
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

	public static HttpServletResponse fileDownload(HttpServletRequest request, HttpServletResponse response, String viewName, String filePath) {
		try {
//			viewName = processFileName(request, viewName);
			// path是指欲下载的文件的路径。
			File file = new File(filePath);
			// 取得文件名。
			String filename = file.getName();
			// 取得文件的后缀名。
			
			
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.setCharacterEncoding("utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + viewName);
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return response;
	}

	/**
	 * 
	 * @Title: processFileName
	 * 
	 * @Description: ie,chrom,firfox下处理文件名显示乱码
	 */
	public static String processFileName(HttpServletRequest request, String fileNames) {
		String codedfilename = null;
		try {
			String agent = request.getHeader("USER-AGENT");
			if (null != agent && -1 != agent.indexOf("MSIE") || null != agent && -1 != agent.indexOf("Trident") || null != agent && -1 != agent.indexOf("Edge")) {// ie

				String name = java.net.URLEncoder.encode(fileNames, "UTF8");

				codedfilename = name;
			} else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等

				codedfilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return codedfilename;
	}

	public static void main(String[] args) throws Exception {

	}
}