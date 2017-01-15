package com.hos.service.impl.dict;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hos.po.dao.dict.DictOrgsMapper;
import com.hos.po.model.dict.DictOrgs;
import com.hos.service.dict.DictOrgService;
import com.hos.service.dict.DictService;
import com.hos.vo.dict.DictOrgSearchVo;
import com.meijia.utils.BoyerMooreUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;


@Service
public class DictOrgServiceImpl implements DictOrgService {

	@Autowired
	private DictOrgsMapper dictOrgMapper;

	@Autowired
	private DictService dictService;
	
	@Override
	public int insertSelective(DictOrgs record) {
		return dictOrgMapper.insert(record);
	}
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return dictOrgMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(DictOrgs record) {
		return dictOrgMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public DictOrgs initDictOrg() {
		DictOrgs record = new DictOrgs();
		record.setOrgId(0L);
		record.setOrgCode("");
		record.setParentCode("");
		record.setName("");
		record.setShortName("");
		record.setRemark("");
		record.setAdminId(0L);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public DictOrgs selectByPrimaryKey(Long id) {
		return dictOrgMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<DictOrgs> selectByAll() {
		return dictOrgMapper.selectByAll();
	}
	
	@Override
	public List<DictOrgs> selectBySearchVo(DictOrgSearchVo searchVo) {
		return dictOrgMapper.selectBySearchVo(searchVo);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(DictOrgSearchVo searchVo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<DictOrgs> list = dictOrgMapper.selectByListPage(searchVo);
		PageInfo info = new PageInfo(list);
		return info;
	}
	
	@Override
	public DictOrgs findByOrgCode(String orgCode) {
		
		List<DictOrgs> list = dictService.LoadOrgData();
		
		for(DictOrgs item : list) {
			if (item.getOrgCode().equals(orgCode)) {
				return item;
			}
		}
		return null;
	}
	
	@Override
	public DictOrgs findByName(String name) {
		
		List<DictOrgs> list = dictService.LoadOrgData();
		
		for(DictOrgs item : list) {
			if (item.getName().equals(name)) {
				return item;
			}
		}
		return null;
	}
	
	@Override
	public DictOrgs findById(Long orgId) {
		
		List<DictOrgs> list = dictService.LoadOrgData();
		
		for(DictOrgs item : list) {
			if (item.getOrgId().equals(orgId)) {
				return item;
			}
		}
		return null;
	}
	
	/** 
	 * 查找匹配单位
	 * 1. 是否名称相同
	 * 2. 是否简称相同
	 * 3. 是否匹配全部简称。
	 */
	@Override
	public List<DictOrgs> findByMatchName(String matchName) {
		
		List<DictOrgs> list = dictService.LoadOrgData();
		
		List<DictOrgs> resultData = new ArrayList<DictOrgs>();
		//是否名称相同 是否简称相同
		for(DictOrgs item : list) {
			if (item.getName().equals(matchName)) {
				resultData.add(item);
				continue;
			}
			
			if (item.getShortName().equals(matchName)) {
				resultData.add(item);
				continue;
			}
		}
		
		if (!resultData.isEmpty()) return resultData;
		
		//去掉特殊字符
		matchName = StringUtil.StringFilter(matchName);
		//去掉空格
		matchName = matchName.replace(" ", "");
		BoyerMooreUtil bm = new BoyerMooreUtil();
		
		
		//是否匹配全部简称.
		for(DictOrgs item : list) {
			
			if (item.getParentId().equals(0L)) continue;
			
			
			String shortName = item.getShortName();
			
			if (matchName.indexOf(shortName) >= 0) {
				resultData.add(item);
				continue;
			}			
		}
		return resultData;
	}
	
	/** 
	 * 查找匹配单位
	 * 1. 是否名称相同
	 * 2. 是否简称相同
	 * 3. 是否匹配全部简称。
	 */
	@Override
	public List<DictOrgs> findByMatchNameLike(String matchName) {
		
		List<DictOrgs> list = dictService.LoadOrgData();
		
		List<DictOrgs> resultData = new ArrayList<DictOrgs>();
		//是否名称相同 是否简称相同
		for(DictOrgs item : list) {
			if (item.getName().equals(matchName)) {
				resultData.add(item);
				continue;
			}
			
			if (item.getShortName().equals(matchName)) {
				resultData.add(item);
				continue;
			}
		}
		
		if (!resultData.isEmpty()) return resultData;
		
		//去掉特殊字符
		matchName = StringUtil.StringFilter(matchName);
		//去掉空格
		matchName = matchName.replace(" ", "");
		BoyerMooreUtil bm = new BoyerMooreUtil();
		
		
		//是否匹配全部简称.
		for(DictOrgs item : list) {
			
			if (item.getParentId().equals(0L)) continue;
			
			
			String shortName = item.getShortName();
			
			if (matchName.indexOf(shortName) >= 0) {
				resultData.add(item);
				continue;
			}
			
			//去掉医院这两个字，属于特殊处理，干扰字段
			shortName = shortName.replace("院", "");
			matchName = matchName.replace("院", "");
			if (shortName.equals(matchName)) {
				resultData.add(item);
				continue;
			}
			
			String text = shortName;
			String pattern = matchName;
			
			if (matchName.length() > shortName.length()) {
				text = matchName;
				pattern = shortName;
			}
			int matchCount = shortName.length();
			int bmMatchCount = bm.boyerMoore(pattern, text);
			if (bmMatchCount == matchCount) {
				resultData.add(item);
				continue;
			}
			
		}
		return resultData;
	}	

}
