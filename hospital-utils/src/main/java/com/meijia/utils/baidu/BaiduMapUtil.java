package com.meijia.utils.baidu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.meijia.utils.GsonUtil;
import com.meijia.utils.HttpClientUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.baidu.vo.BaiduGeoCoderAddressComponent;
import com.meijia.utils.baidu.vo.BaiduGeoCoderResultVo;
import com.meijia.utils.baidu.vo.BaiduGeoCoderVo;
import com.meijia.utils.baidu.vo.BaiduPoiVo;

/**
 * 访问百度 API 的类
 */

public class BaiduMapUtil {

	/**
	 * 计算地球上任意两点(经纬度)距离
	 * 
	 * @param long1
	 *            第一点经度
	 * @param lat1
	 *            第一点纬度
	 * @param long2
	 *            第二点经度
	 * @param lat2
	 *            第二点纬度
	 * @return 返回距离 单位：米
	 */
	public static Integer poiDistance(String lng1, String lat1, String lng2, String lat2) {
		Integer poiDistance = 0;
		Double dlng1 = Double.valueOf(lng1);
		Double dlat1 = Double.valueOf(lat1);
		Double dlng2 = Double.valueOf(lng2);
		Double dlat2 = Double.valueOf(lat2);
		Double d = Distance(dlng1, dlat1, dlng2, dlat2);
		
		poiDistance = d.intValue();
		
		return poiDistance;
	}
	
	
	public static double Distance(double long1, double lat1, double long2, double lat2) {
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
		return d;
	}

	public static List<BaiduPoiVo> getMapRouteMatrix(String fromLat, String fromLng, List<BaiduPoiVo> destAddrs) throws Exception {
		List<BaiduPoiVo> resultAddrs = new ArrayList<BaiduPoiVo>();

		List<BaiduPoiVo> destAddrsMod4 = new ArrayList<BaiduPoiVo>();
		for (int i = 0; i < destAddrs.size(); i++) {
			destAddrsMod4.add(destAddrs.get(i));
			if (i > 0 && i % 4 == 0) {
				System.out.println(i);
				List<BaiduPoiVo> resultDestAddrsMod4 = toMapRouteMatrix(fromLat, fromLng, destAddrsMod4);
				for (int j = 0; j < resultDestAddrsMod4.size(); j++) {
					resultAddrs.add(resultDestAddrsMod4.get(j));
				}
				destAddrsMod4 = new ArrayList<BaiduPoiVo>();
			}
		}

		// 最后也需要再请求一次
		List<BaiduPoiVo> resultDestAddrsMod4 = toMapRouteMatrix(fromLat, fromLng, destAddrsMod4);

		for (int j = 0; j < resultDestAddrsMod4.size(); j++) {
			resultAddrs.add(resultDestAddrsMod4.get(j));
		}

		return resultAddrs;
	}

	/**
	 * http://developer.baidu.com/map/index.php?title=webapi/route-matrix-api
	 * Route Matrix API是一套以HTTP形式提供的批量线路查询接口，用于返回多个起点和多个终点间的线路距离和行驶时间。
	 * 该服务并不会返回详细的线路信息 一个起点，到多个终点的间距和时间
	 * 
	 * http://api.map.baidu.com/direction/v1/routematrix?output=json&ak=2
	 * sshjv8D4AOoOzozoutVb6WT
	 * &origins=39.894585,116.471626&destinations=39.896014
	 * ,116.47341|39.915285,116.403857
	 * 
	 * @param startAddress
	 *            起点地址名称 或者 经纬度坐标，以,分割，最多传5个点
	 * @param endAddresses
	 *            终点地址名称 或者 经纬度坐标，以,分割，最多传5个点 名称：百度大厦 经纬度：40.056878, 116.30815
	 *            坐标格式为：lat<纬度>,lng<经度>
	 * @param mode
	 *            导航模式，包括：driving（驾车）、walking（步行）
	 * @return
	 * @throws Exception
	 */
	public static List<BaiduPoiVo> toMapRouteMatrix(String fromLat, String fromLng, List<BaiduPoiVo> destAddrs) throws Exception {

		List<BaiduPoiVo> resultAddrs = new ArrayList<BaiduPoiVo>();

		String url = BaiduConfigUtil.getInstance().getRb().getString("routematrixUrl");
		String ak = BaiduConfigUtil.getInstance().getRb().getString("ak");
		Map<String, String> params = new HashMap<String, String>();
		params.put("output", "json");
		params.put("ak", ak);
		params.put("origins", fromLat + "," + fromLng);

		BaiduPoiVo item = null;
		String destinations = "";
		for (int i = 0; i < destAddrs.size(); i++) {
			item = destAddrs.get(i);
			destinations += item.getLat() + "," + item.getLng();
			if (i < destAddrs.size() - 1) {
				destinations += "|";
			}
		}

		params.put("destinations", destinations);
		System.out.println(params.toString());
		String getResult = HttpClientUtil.get(url, params);
		System.out.println(getResult);
		JSONObject dataJson;
		try {
			dataJson = new JSONObject(getResult);
			String status = dataJson.getString("status");
			if (!status.equals("0"))
				return resultAddrs;

			JSONObject result = dataJson.getJSONObject("result");

			JSONArray elements = result.getJSONArray("elements");

			BaiduPoiVo vo = null;

			for (int i = 0; i < elements.length(); i++) {
				JSONObject element = elements.getJSONObject(i);

				JSONObject duration = element.getJSONObject("duration");
				JSONObject distance = element.getJSONObject("distance");

				if (destAddrs.get(i) != null) {
					vo = destAddrs.get(i);
					vo.setDistanceText(distance.getString("text"));
					vo.setDistanceValue(distance.getInt("value"));
					vo.setDurationText(duration.getString("text"));
					vo.setDurationValue(duration.getInt("value"));
					resultAddrs.add(vo);
				}

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultAddrs;
	}

	/*
	 * 对结果集排序，索引为0的 第一个对象，即为最近距离的
	 */
	public static BaiduPoiVo getMinDest(List<BaiduPoiVo> resultAddrs, int maxDistance, int maxDuration) {

		if (maxDistance <= 0)
			maxDistance = 10000;
		if (maxDuration <= 0)
			maxDuration = 3600;

		List<BaiduPoiVo> firstList = new ArrayList<BaiduPoiVo>();

		// 取得 符合 60分钟。10公里 的Vo
		for (int i = 0; i < resultAddrs.size(); i++) {
			BaiduPoiVo baiduPoiVo = resultAddrs.get(i);
			// 10000米，3600秒
			if (baiduPoiVo.getDistanceValue() < maxDistance && baiduPoiVo.getDurationValue() < maxDuration) {
				firstList.add(baiduPoiVo);
			}
		}

		if (firstList.size() > 0) {
			Collections.sort(firstList, new Comparator<BaiduPoiVo>() {
				@Override
				public int compare(BaiduPoiVo s1, BaiduPoiVo s2) {
					return Integer.valueOf(s1.getDistanceValue()).compareTo(s2.getDistanceValue());
				}
			});
		}

		// 排序之后
		BaiduPoiVo baiduPoiVo = initBaiduPoiVo();

		if (firstList.size() > 0) {

			baiduPoiVo = firstList.get(0);
		}

		return baiduPoiVo;
	}

	@SuppressWarnings("unchecked")
	public static String getCityByPoi(String lat, String lng) {
		String cityName = "";
		String url = BaiduConfigUtil.getInstance().getRb().getString("geocoderUrl");
		String ak = BaiduConfigUtil.getInstance().getRb().getString("ak");
		Map<String, String> params = new HashMap<String, String>();
		params.put("output", "json");
		params.put("ak", ak);
		params.put("location", lat + "," + lng);

		String getResult = HttpClientUtil.get(url, params);

		if (StringUtil.isEmpty(getResult))
			return cityName;

		BaiduGeoCoderVo result = GsonUtil.GsonToObject(getResult, BaiduGeoCoderVo.class);

		if (result == null)
			return cityName;

		BaiduGeoCoderResultVo item = result.getResult();

		if (item == null)
			return cityName;

		BaiduGeoCoderAddressComponent vo = item.getAddressComponent();

		if (vo == null)
			return cityName;

		cityName = vo.getCity();

		return cityName;
	}

	private static BaiduPoiVo initBaiduPoiVo() {
		BaiduPoiVo baiduPoiVo = new BaiduPoiVo();

		baiduPoiVo.setDistanceText("");
		baiduPoiVo.setDistanceValue(-1);
		baiduPoiVo.setDurationText("");
		baiduPoiVo.setDurationValue(-1);
		baiduPoiVo.setLat("");
		baiduPoiVo.setLng("");
		baiduPoiVo.setName("");

		return baiduPoiVo;
	}

	public static void main(String[] args) {
		// 计算距离, 并且要做大于5的文本切割
		String fromLat = "39.894585";
		String fromLng = "116.471626";
		// 获得地理位置对应的城市
		System.out.println(BaiduMapUtil.getCityByPoi(fromLat, fromLng));

		// 需要计算的地址列表
		// List<BaiduPoiVo> destAddrs = new ArrayList<BaiduPoiVo>();
		// BaiduPoiVo d1 = new BaiduPoiVo();
		// d1.setLat("39.896014");
		// d1.setLng("116.47341");
		// d1.setName("百环家园");
		// destAddrs.add(d1);
		//
		// BaiduPoiVo d2 = new BaiduPoiVo();
		// d2.setLat("39.915285");
		// d2.setLng("116.403857");
		// d2.setName("天安门");
		// destAddrs.add(d2);
		//
		// //116.400532,40.00077 奥林匹克公园
		// BaiduPoiVo d3 = new BaiduPoiVo();
		// d3.setLat("40.00077");
		// d3.setLng("116.400532");
		// d3.setName("奥林匹克公园");
		// destAddrs.add(d3);
		//
		// //116.315732,40.016023 圆明园
		// BaiduPoiVo d4 = new BaiduPoiVo();
		// d4.setLat("40.016023");
		// d4.setLng("116.400532");
		// d4.setName("圆明园");
		// destAddrs.add(d4);
		//
		// //116.216846,40.00917 植物园
		// BaiduPoiVo d5 = new BaiduPoiVo();
		// d5.setLat("40.00917");
		// d5.setLng("116.216846");
		// d5.setName("植物园");
		// destAddrs.add(d5);
		//
		// //116.620724,40.061982 首都国际机场
		// BaiduPoiVo d6 = new BaiduPoiVo();
		// d6.setLat("40.061982");
		// d6.setLng("116.620724");
		// d6.setName("首都国际机场");
		// destAddrs.add(d6);
		//
		// //116.383284,39.870869 北京南站
		// BaiduPoiVo d7 = new BaiduPoiVo();
		// d7.setLat("39.870869");
		// d7.setLng("116.383284");
		// d7.setName("北京南站");
		// destAddrs.add(d7);
		//
		// //116.433302,39.910286 北京站
		// BaiduPoiVo d8 = new BaiduPoiVo();
		// d8.setLat("39.910286");
		// d8.setLng("116.433302");
		// d8.setName("北京站");
		// destAddrs.add(d8);
		//
		// //116.329242,39.900545 北京西站
		// BaiduPoiVo d9 = new BaiduPoiVo();
		// d9.setLat("39.900545");
		// d9.setLng("116.329242");
		// d9.setName("北京西站");
		// destAddrs.add(d9);
		//
		// //117.649823,39.033812 塘沽站
		// BaiduPoiVo d10 = new BaiduPoiVo();
		// d10.setLat("39.033812");
		// d10.setLng("117.649823");
		// d10.setName("塘沽站");
		// destAddrs.add(d10);
		//
		// //121.810487,31.156731 上海浦东国际机场
		// BaiduPoiVo d11 = new BaiduPoiVo();
		// d11.setLat("31.156731");
		// d11.setLng("121.810487");
		// d11.setName("上海浦东国际机场");
		// destAddrs.add(d11);
		//
		// //108.395552,22.792567 广西南宁青秀山
		// BaiduPoiVo d12 = new BaiduPoiVo();
		// d12.setLat("22.792567");
		// d12.setLng("108.395552");
		// d12.setName("广西南宁青秀山");
		// destAddrs.add(d12);
		//
		//
		//
		// try {
		// List<BaiduPoiVo> resultAddrs =
		// BaiduMapUtil.getMapRouteMatrix(fromLat, fromLng, destAddrs);
		//
		// Collections.sort(resultAddrs, new Comparator<BaiduPoiVo>() {
		// public int compare(BaiduPoiVo s1, BaiduPoiVo s2) {
		// return
		// Integer.valueOf(s1.getDistanceValue()).compareTo(s2.getDistanceValue());
		// }
		// });
		//
		// BaiduPoiVo v = null;
		// for (int j = 0; j < resultAddrs.size(); j++) {
		// v = resultAddrs.get(j);
		// System.out.println(v.getName() + "---" + v.getDistanceText() + "--- "
		// + v.getDistanceValue() + "--" + v.getDurationText() + "---" +
		// v.getDurationValue());
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// d3.setLat("40.00077");
		// d3.setLng("116.400532");
		// d3.setName("奥林匹克公园");
		
		// d4.setLat("40.016023");
		// d4.setLng("116.400532");
		// d4.setName("圆明园");
		
		// d5.setLat("40.00917");
		// d5.setLng("116.216846");
		// d5.setName("植物园");
		
		Double lng1 = new Double("116.697715");
		Double lat1 = new Double("40.110653");
		
		Double lng2 = new Double("116.477706");
		Double lat2 = new Double("39.899465");
		
		System.out.println(BaiduMapUtil.Distance(lng1, lat1, lng2, lat2));
		
//		List<HashMap<String, Object>> matchSettings = new ArrayList<HashMap<String, Object>>();
//		HashMap<String, Object> matchItem1 = new HashMap<String, Object>();
//		matchItem1.put("matchId", 1);
//		matchItem1.put("poiDistance", 50);
//		matchSettings.add(matchItem1);
//		
//		HashMap<String, Object> matchItem2 = new HashMap<String, Object>();
//		matchItem2.put("matchId", 2);
//		matchItem2.put("poiDistance", 30);
//		matchSettings.add(matchItem2);
//		
//		HashMap<String, Object> matchItem3 = new HashMap<String, Object>();
//		matchItem3.put("matchId", 3);
//		matchItem3.put("poiDistance", 80);
//		matchSettings.add(matchItem3);
//		
//		Collections.sort(matchSettings, new Comparator<Map<String, Object>>() {
//			public int compare(final Map<String, Object> o1, final Map<String, Object> o2) {
//				return Integer.valueOf(o1.get("poiDistance").toString()).compareTo(Integer.valueOf(o2.get("poiDistance").toString()));
//			}
//		});
//		
//		for (int i = 0; i < matchSettings.size(); i++) {
//			HashMap<String, Object> item = matchSettings.get(i);
//			System.out.println("mathcId = " + item.get("matchId").toString() + " -- poiDistance = " + item.get("poiDistance").toString());
//		}
		
	}

}