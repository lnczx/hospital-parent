package com.hos.service.dict;

import java.util.List;

import com.hos.po.model.dict.DictOrgs;
import com.hos.po.model.dict.Dicts;

public interface DictService {

	void loadData();

	List<DictOrgs> LoadOrgData();

	Dicts findCityById(Long cityId);

	List<Dicts> LoadCityData();

	List<Dicts> LoadTitleData();

	List<Dicts> LoadSubjectData();

	List<Dicts> LoadCourseTypeData();

	Dicts findCityByName(String name);

	Dicts findTitleById(Long titleId);

	Dicts findTitleByName(String name);

	Dicts findSubjectById(Long id);

	Dicts findSubjectByName(String name);

	Dicts findCourseTypeById(Long id);

	Dicts findCourseTypeByName(String name);

	List<Dicts> LoadNationData();

	List<Dicts> LoadEduData();

	List<Dicts> LoadDegreeData();

	List<Dicts> LoadIdTypeData();



}
