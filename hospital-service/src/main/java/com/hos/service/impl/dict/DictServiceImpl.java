package com.hos.service.impl.dict;

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
	public Dicts findCityById(Long id) {
		List<Dicts> list = this.LoadCityData();
		
		for(Dicts item : list) {
			if (item.getId().equals(id)) {
				return item;
			}
		}
		return null;
	}
	
	@Override
	public Dicts findCityByName(String name) {
		List<Dicts> listCity = this.LoadCityData();
		for(Dicts item : listCity) {
			if (item.getName().trim().equals(name.trim())) {
				return item;
			}
		}
		return null;
	}
	
	@Override
	public Dicts findTitleById(Long id) {
		List<Dicts> list= this.LoadTitleData();
		
		for(Dicts item : list) {
			if (item.getId().equals(id)) {
				return item;
			}
		}
		return null;
	}
	
	@Override
	public Dicts findTitleByName(String name) {
		List<Dicts> listCity = this.LoadTitleData();
		for(Dicts item : listCity) {
			if (item.getName().trim().equals(name.trim())) {
				return item;
			}
		}
		return null;
	}
	
	@Override
	public Dicts findSubjectById(Long id) {
		List<Dicts> list= this.LoadSubjectData();
		
		for(Dicts item : list) {
			if (item.getId().equals(id)) {
				return item;
			}
		}
		return null;
	}
	
	@Override
	public Dicts findSubjectByName(String name) {
		List<Dicts> listCity = this.LoadSubjectData();
		for(Dicts item : listCity) {
			if (item.getName().trim().equals(name.trim())) {
				return item;
			}
		}
		return null;
	}
	
	@Override
	public Dicts findCourseTypeById(Long id) {
		List<Dicts> list= this.LoadCourseTypeData();
		
		for(Dicts item : list) {
			if (item.getId().equals(id)) {
				return item;
			}
		}
		return null;
	}
	
	@Override
	public Dicts findCourseTypeByName(String name) {
		List<Dicts> listCity = this.LoadCourseTypeData();
		for(Dicts item : listCity) {
			if (item.getName().trim().equals(name.trim())) {
				return item;
			}
		}
		return null;
	}

}
