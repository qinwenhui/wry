package cn.qinwh.wry.service;

import cn.qinwh.wry.po.Admin;

public interface AdminService {

	//登录
	public Admin login(String username, String password) throws Exception;
	
	//注册
	public boolean register(String username, String password) throws Exception;
	
	//修改头像
	public boolean updateIcon(int id,String icon) throws Exception;
	
	//修改密码
	public boolean updatePassword(int id,String password) throws Exception;

	public Admin getAdminByPassword(int id, String password) throws Exception;

	public int queryAdminNumber() throws Exception;
}
