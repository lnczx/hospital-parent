package com.hos.service.impl.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hos.common.Constants;
import com.hos.po.dao.dict.DictOrgsMapper;
import com.hos.po.dao.dict.DictsMapper;
import com.hos.po.model.dict.DictOrgs;
import com.hos.po.model.dict.Dicts;
import com.hos.service.dict.DictService;
import com.hos.vo.dict.DictSearchVo;

@Service
public class DictServiceImpl implements DictService {

	@SuppressWarnings("rawtypes")
	public static Map<String, List> memDictMap = new HashMap<String, List>();
	
	@Autowired
	private DictsMapper dictMapper;

	@Autowired
	private DictOrgsMapper dictOrgMapper;
	
	/**
	 * Spring 容器初始化时加载
	 */
	@Override
	public void loadData() {
		
		this.LoadCityData();
		
		this.LoadTitleData();
		
		this.LoadSubjectData();
		
		this.LoadCourseTypeData();
		
		this.LoadNationData();

		this.LoadEduData();
		
		this.LoadDegreeData();
		
		this.LoadIdTypeData();

		this.LoadOrgData();
		
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public List<Dicts> LoadCityData() {

		//城市表
		DictSearchVo searchVo = new DictSearchVo();
		searchVo.setType(Constants.DICT_AREA);
		searchVo.setLevel(1);
		List<Dicts> listCity = memDictMap.get("listCity");
		if (listCity == null || listCity.isEmpty()) {
			listCity = dictMapper.selectBySearchVo(searchVo);
			memDictMap.put("listCity", listCity);
		}
		
		return listCity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Dicts> LoadTitleData() {

		//职称
		DictSearchVo searchVo = new DictSearchVo();
		searchVo.setType(Constants.DICT_TITLE);
		List<Dicts> listTitle = memDictMap.get("listTitle");
		if (listTitle == null || listTitle.isEmpty()) {
			listTitle = dictMapper.selectBySearchVo(searchVo);
			memDictMap.put("listTitle", listTitle);
		}
		return listTitle;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Dicts> LoadSubjectData() {
		//学科表  subject
		DictSearchVo searchVo = new DictSearchVo();
		searchVo.setType(Constants.DICT_SUBJECT);
		List<Dicts> listSubject = memDictMap.get("listSubject");
		if (listSubject == null || listSubject.isEmpty()) {
			listSubject = dictMapper.selectBySearchVo(searchVo);
			memDictMap.put("listSubject", listSubject);
		}
		return listSubject;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Dicts> LoadCourseTypeData() {
		//课程类型  实践 理论
		DictSearchVo searchVo = new DictSearchVo();
		searchVo.setType(Constants.DICT_COURSE_TYPE);
		List<Dicts> listCourseType = memDictMap.get("listCourseType");
		if (listCourseType == null || listCourseType.isEmpty()) {
			listCourseType = dictMapper.selectBySearchVo(searchVo);
			memDictMap.put("listCourseType", listCourseType);
		}
		return listCourseType;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Dicts> LoadNationData() {

		//城市表
		DictSearchVo searchVo = new DictSearchVo();
		searchVo.setType(Constants.DICT_NATION);
		List<Dicts> list = memDictMap.get("listNation");
		if (list == null || list.isEmpty()) {
			list = dictMapper.selectBySearchVo(searchVo);
			memDictMap.put("listNation", list);
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Dicts> LoadEduData() {

		//城市表
		DictSearchVo searchVo = new DictSearchVo();
		searchVo.setType(Constants.DICT_EDU);
		List<Dicts> list = memDictMap.get("listEdu");
		if (list == null || list.isEmpty()) {
			list = dictMapper.selectBySearchVo(searchVo);
			memDictMap.put("listEdu", list);
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Dicts> LoadDegreeData() {

		//城市表
		DictSearchVo searchVo = new DictSearchVo();
		searchVo.setType(Constants.DICT_DEGREE);
		List<Dicts> list = memDictMap.get("listDegree");
		if (list == null || list.isEmpty()) {
			list = dictMapper.selectBySearchVo(searchVo);
			memDictMap.put("listDegree", list);
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Dicts> LoadIdTypeData() {

		//城市表
		DictSearchVo searchVo = new DictSearchVo();
		searchVo.setType(Constants.DICT_ID_TYPE);
		List<Dicts> list = memDictMap.get("listIdType");
		if (list == null || list.isEmpty()) {
			list = dictMapper.selectBySearchVo(searchVo);
			memDictMap.put("listIdType", list);
		}
		
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DictOrgs> LoadOrgData() {

		//单位机构信息		
		List<DictOrgs> listOrgs = memDictMap.get("listOrgs");
		if (listOrgs == null || listOrgs.isEmpty()) {
			listOrgs = dictOrgMapper.selectByAll();
			memDictMap.put("listOrgs", listOrgs);
		}
		return listOrgs;
	}
	
	@Override
	public Dicts findById(Long id, String dictType) {
		
		List<Dicts> list = new ArrayList<Dicts>();
		if (dictType.equals(Constants.DICT_AREA)) list = this.LoadCityData();
		if (dictType.equals(Constants.DICT_COURSE_TYPE)) list = this.LoadCourseTypeData();
		if (dictType.equals(Constants.DICT_DEGREE)) list = this.LoadDegreeData();
		if (dictType.equals(Constants.DICT_EDU)) list = this.LoadEduData();
		if (dictType.equals(Constants.DICT_ID_TYPE)) list = this.LoadIdTypeData();
		if (dictType.equals(Constants.DICT_NATION)) list = this.LoadNationData();
		if (dictType.equals(Constants.DICT_SUBJECT)) list = this.LoadSubjectData();
		if (dictType.equals(Constants.DICT_TITLE)) list = this.LoadTitleData();
		
		for(Dicts item : list) {
			if (item.getId().equals(id)) {
				return item;
			}
		}
		return null;
	}
	
	@Override
	public Dicts findByName(String name, String dictType) {
		List<Dicts> list = new ArrayList<Dicts>();
		if (dictType.equals(Constants.DICT_AREA)) list = this.LoadCityData();
		if (dictType.equals(Constants.DICT_COURSE_TYPE)) list = this.LoadCourseTypeData();
		if (dictType.equals(Constants.DICT_DEGREE)) list = this.LoadDegreeData();
		if (dictType.equals(Constants.DICT_EDU)) list = this.LoadEduData();
		if (dictType.equals(Constants.DICT_ID_TYPE)) list = this.LoadIdTypeData();
		if (dictType.equals(Constants.DICT_NATION)) list = this.LoadNationData();
		if (dictType.equals(Constants.DICT_SUBJECT)) list = this.LoadSubjectData();
		if (dictType.equals(Constants.DICT_TITLE)) list = this.LoadTitleData();
		for(Dicts item : list) {
			if (item.getName().trim().equals(name.trim())) {
				return item;
			}
		}
		return null;
	}
}
