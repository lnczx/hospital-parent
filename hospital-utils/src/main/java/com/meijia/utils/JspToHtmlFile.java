package com.meijia.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Map;

public class JspToHtmlFile {
	 private static String title ="标题测试1";
	   private static String context ="标题测试2";
	   private static String editer ="标题测试4";

	         /**
	             * 根据本地模板生成静态页面
	          * @param JspFile    jsp路经
	          * @param HtmlFile html路经
	          * @return
	          */
	         public static boolean JspToHtmlFile(String filePath, String HtmlFile,Map map) {
	                 String str = "";
	                 long beginDate = (new Date()).getTime();
	                 try {
	                         String tempStr = "";
	                   FileInputStream is = new FileInputStream(filePath);//读取模块文件
	                         BufferedReader br = new BufferedReader(new InputStreamReader(is));
	                         while ((tempStr = br.readLine()) != null)
	                         str = str + tempStr ;
	                         is.close();
	                 } catch (IOException e) {
	                         e.printStackTrace();
	                         return false;
	                 }
	                 try {

			             str = str.replaceAll("###title###",map.get("title").toString());
			             str = str.replaceAll("###addTime###",map.get("addTime").toString());//替换掉模块中相应的地方
			             str = str.replaceAll("###content###",map.get("content").toString());

	                         File f = new File(HtmlFile);
	                         BufferedWriter o = new BufferedWriter(new FileWriter(f));
	                         o.write(str);
	                         o.close();
	                         System.out.println("共用时：" + ((new Date()).getTime() - beginDate) + "ms");
	                 } catch (IOException e) {
	                         e.printStackTrace();
	                         return false;
	                 }
	                 return true;
	         }

	         /**
	          * 根据url生成静态页面
	          *
	          * @param u        动态文件路经 如：http://www.163.com/x.jsp
	          * @param path 文件存放路经如：x:\\abc\bbb.html
	          * @return
	          */
	         public static boolean JspToHtmlByURL(String u, String path) {
	                 //从utl中读取html存为str
	                 String str = "";
	                 try {
	                         URL url = new URL(u);
	                         URLConnection uc = url.openConnection();
	                         InputStream is = uc.getInputStream();
	                         BufferedReader br = new BufferedReader(new InputStreamReader(is));
	                         while (br.ready()) {
	                                 str += br.readLine() + "\n";

	                         }
	                         is.close();
	                         //写入文件
	                         File f = new File(path);
	                         BufferedWriter o = new BufferedWriter(new FileWriter(f));
	                         o.write(str);
	                         o.close();
	                         str = "";
	                         return true;
	                 } catch (Exception e) {
	                         e.printStackTrace();
	                         return false;
	                 }
	         }

	         /**
	          * 根据url生成静态页面
	          *
	          * @param url 动态文件路经 如：http://www.163.com/x.jsp
	          * @return d
	          */
	         public static StringBuffer getHtmlTextByURL(String url) {
	                 //从utl中读取html存为str
	                 StringBuffer sb = new StringBuffer();
	                 try {
	                         URL u = new URL(url);
	                         URLConnection uc = u.openConnection();
	                         InputStream is = uc.getInputStream();
	                         BufferedReader br = new BufferedReader(new InputStreamReader(is));
	                         while (br.ready()) {
	                                 sb.append(br.readLine() + "\n");
	                         }
	                         is.close();
	                         return sb;
	                 } catch (Exception e) {
	                         e.printStackTrace();
	                         return sb;
	                 }
	         }

	         /**
	          * 测试main 函数
	          *
	          * @param arg
	          */
	         public static void main(String[] arg) {
	                 long begin = System.currentTimeMillis();
	     //循环生成20个html文件

	                         String url = "d:\\workspace\\news_detail.html";//模板文件地址
	                         String savepath = "d:/"  + "demo.html";//生成文件地址
	                         //JspToHtmlFile(url, savepath);
	                 System.out.println("用时:" + (System.currentTimeMillis() - begin) + "ms");
	         }

	}
