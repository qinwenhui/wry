package cn.qinwh.wry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qinwh.wry.mapper.VersionMapper;
import cn.qinwh.wry.po.Version;
import cn.qinwh.wry.service.VersionService;

@Service
public class VersionServiceImpl implements VersionService {

	@Autowired
	private VersionMapper mapper;
	
	@Override
	public List<Version> queryAllVersion() throws Exception {
		
		return mapper.selectAll();
	}

	@Override
	public Version queryMaxVersion() throws Exception {
		return mapper.selectMaxVersion();
	}

	@Override
	public boolean deleteVersion(int id) throws Exception {
		int count = mapper.deleteByPrimaryKey(id);
		if(count ==1)
			return true;
		return false;
	}

	@Override
	public boolean updateVersion(Version version) throws Exception {
		int count = mapper.updateByPrimaryKeySelective(version);
		if(count ==1)
			return true;
		return false;
	}

	@Override
	public boolean addVersion(Version version) throws Exception {
		int count = mapper.insertSelective(version);
		if(count ==1)
			return true;
		return false;
	}

	@Override
	public List<Version> queryVersionByKeyword(String keyword) {
		
		return mapper.selectByKeyword(keyword);
	}

	@Override
	public Version queryVersionById(int id) throws Exception {
		
		return mapper.selectByPrimaryKey(id);
	}

}
