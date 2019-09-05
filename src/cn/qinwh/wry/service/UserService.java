package cn.qinwh.wry.service;

import java.util.List;

import cn.qinwh.wry.po.User;

public interface UserService {

	public User queryUserById(int id) throws Exception;

	public User getUserByUsernameAndPassword(String username, String password) throws Exception;

	public boolean register(String username, String password) throws Exception;
	
	public boolean updateUserIcon(int id, String icon) throws Exception;
	
	//修改用户的某个值
	public boolean updateUserSelective(User user) throws Exception;
	
	//删除某个用户
	public boolean deleteUserById(int id) throws Exception;
	
	//查询所有用户
	public List<User> queryAllUser() throws Exception;
	
	//根据手机号查询
	public User queryUserByPhone(String phone_number) throws Exception;
	
	//根据昵称模糊查询
	public List<User> queryUserByNickname(String nickname) throws Exception;

	public List<User> queryUserByKeyword(String keyword) throws Exception;
	
	public int queryUserNumber() throws Exception;

}
