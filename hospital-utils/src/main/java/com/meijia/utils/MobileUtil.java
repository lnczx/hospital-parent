package com.meijia.utils;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MobileUtil {

	/**
	 * 测试手机号码是来自哪个城市的，利用淘宝的API
	 * 
	 * @param mobile
	 *            手机号码
	 * @return
	 * @throws MalformedURLException
	 */
	public static String calcMobileCity(String mobile) throws MalformedURLException {
		String jsonString = null;
		JSONArray array = null;
		JSONObject jsonObject = null;
		String urlString = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm";

		String province = "";

		Map<String, String> params = new HashMap<String, String>();
		params.put("tel", mobile);
		try {

			jsonString = HttpClientUtil.get(urlString, params);
			// 替换掉“__GetZoneResult_ = ”，让它能转换为JSONArray对象
			jsonString = jsonString.replaceAll("^[__]\\w{14}+[_ = ]+", "[");
			// System.out.println(jsonString+"]");
			String jsonString2 = jsonString + "]";
			// 把STRING转化为json对象
			array = JSONArray.fromObject(jsonString2);

			// 获取JSONArray的JSONObject对象，便于读取array里的键值对
			jsonObject = array.getJSONObject(0);
			if (jsonObject != null && jsonObject.getString("province") != null) {
				province = jsonObject.getString("province");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return province;
	}

	/**
	 * 计算多个号码的归属地
	 * 
	 * @param mobileNumbers
	 *            号码列表
	 * @return
	 * @throws MalformedURLException
	 */
	public static JSONObject calcMobilesCities(List<String> mobiles) throws MalformedURLException {
		JSONObject jsonNumberCity = new JSONObject();
		for (String mobile : mobiles) {
			jsonNumberCity.put(mobile, calcMobileCity(mobile));
			;
		}
		return jsonNumberCity;
	}

	public static String getMobileStar(String mobile) {
		String str = "";

		for (int i = 0; i < mobile.length(); i++) {
			if (i == mobile.length() - 11) {
				str += mobile.charAt(i);
			} else if (i == mobile.length() - 10) {
				str += mobile.charAt(i);
			} else if (i == mobile.length() - 9) {
				str += mobile.charAt(i);
			} else if (i == mobile.length() - 3) {
				str += mobile.charAt(i);
			} else if (i == mobile.length() - 2) {
				str += mobile.charAt(i);
			} else if (i == mobile.length() - 1) {
				str += mobile.charAt(i);
			} else {
				str += "*";
			}
		}

		return str;
	}
	
	public static String getMobileX(String mobile) {
		String str = "";

		for (int i = 0; i < mobile.length(); i++) {
			if (i == mobile.length() - 11) {
				str += mobile.charAt(i);
			} else if (i == mobile.length() - 10) {
				str += mobile.charAt(i);
			} else if (i == mobile.length() - 9) {
				str += mobile.charAt(i);
			} else if (i == mobile.length() - 3) {
				str += mobile.charAt(i);
			} else if (i == mobile.length() - 2) {
				str += mobile.charAt(i);
			} else if (i == mobile.length() - 1) {
				str += mobile.charAt(i);
			} else {
				str += "x";
			}
		}

		return str;
	}

	public static void main(String[] args) throws Exception {
		String testMobileNumber = "18519188816";
//		System.out.println(calcMobileCity(testMobileNumber));
//		List<String> mobileList = new ArrayList<String>();
		// for(int i = 1350345; i < 1350388; i++){
		// mobileList.add(String.valueOf(i));
		// }
		// System.out.println(calcMobilesCities(mobileList).toString());
		
		System.out.println(MobileUtil.getMobileStar(testMobileNumber));
	}

}
