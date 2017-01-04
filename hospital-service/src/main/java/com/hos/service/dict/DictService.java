package com.hos.service.dict;

import java.util.List;

import com.hos.po.model.dict.DictOrgs;
import com.hos.po.model.dict.Dicts;

public interface DictService {

	void loadData();

	List<DictOrgs> LoadOrgData();

	List<Dicts> LoadCityData();

	List<Dicts> LoadTitleData();

	List<Dicts> LoadSubjectData();

	List<Dicts> LoadCourseTypeData();

	List<Dicts> LoadNationData();

	List<Dicts> LoadEduData();

	List<Dicts> LoadDegreeData();

	List<Dicts> LoadIdTypeData();

	Dicts findById(Long id, String dictType);

	Dicts findByName(String name, String dictType);



}
