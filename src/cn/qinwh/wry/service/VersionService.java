package cn.qinwh.wry.service;

import java.util.List;

import cn.qinwh.wry.po.Version;

public interface VersionService {

	public List<Version> queryAllVersion() throws Exception;
	
	public Version queryMaxVersion() throws Exception;
	
	public Version queryVersionById(int id) throws Exception;
	
	public boolean deleteVersion(int id) throws Exception;
	
	public boolean updateVersion(Version version) throws Exception;
	
	public boolean addVersion(Version version) throws Exception;

	public List<Version> queryVersionByKeyword(String keyword);
}
