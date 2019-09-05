package cn.qinwh.wry.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qinwh.wry.mapper.AdminMapper;
import cn.qinwh.wry.po.Admin;
import cn.qinwh.wry.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;
	@Override
	public Admin login(String username, String password) throws Exception {

		Admin resultAdmin = adminMapper.selectByUsernameAndPassword(username, password);
		return resultAdmin;
	}

	@Override
	public boolean register(String username, String password) throws Exception {
		//先检查看看有没有存在该用户
		Admin resultAdmin = adminMapper.selectByUsername(username);
		if(resultAdmin == null){
			//可以注册
			Admin admin = new Admin();
			admin.setUsername(username);
			admin.setPassword(password);
			if(adminMapper.insertSelective(admin) == 1)
				return true;
		}else{
			return false;
		}
		
		return false;
	}

	@Override
	public boolean updateIcon(int id, String icon) throws Exception {
		Admin admin = new Admin();
		admin.setIcon(icon);
		admin.setId(id);
		if(adminMapper.updateByPrimaryKeySelective(admin) == 1)
			return true;
		return false;
	}

	@Override
	public boolean updatePassword(int id, String password) throws Exception {
		Admin admin = new Admin();
		admin.setPassword(password);
		admin.setId(id);
		if(adminMapper.updateByPrimaryKeySelective(admin) == 1)
			return true;
		return false;
	}

	@Override
	public Admin getAdminByPassword(int id, String password) throws Exception {
		Admin admin = null;
		admin = adminMapper.selectByPassword(id, password);
		return admin;
	}

	@Override
	public int queryAdminNumber() throws Exception {
		
		return adminMapper.selectNumber();
	}

}
