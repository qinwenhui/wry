package cn.qinwh.wry.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qinwh.wry.mapper.UserMapper;
import cn.qinwh.wry.po.Notice;
import cn.qinwh.wry.po.User;
import cn.qinwh.wry.service.NoticeService;
import cn.qinwh.wry.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private NoticeService nService;
	@Override
	public User queryUserById(int id) throws Exception {
		
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public User getUserByUsernameAndPassword(String username, String password) {

		return userMapper.selectByUsernameAndPassword(username,password);
	}

	@Override
	public boolean register(String username, String password) throws Exception {
		//先判断是否存在该用户
		User user = userMapper.selectByUsername(username);
		if(user == null){
			//不存在该用户。可以注册
			user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user.setNickname("用户"+username);
			user.setTrustNumber(0);
			user.setServiceNumber(0);
			user.setRegisterTime(new Date());
			user.setState(0);
			user.setCredit(100);
			int count = userMapper.insertSelective(user);
			if(count == 1){
				//注册成功，自动给用户发送一个欢迎通知
				int user_id = user.getId();
				Notice notice = new Notice();
				notice.setAdminId(0);
				notice.setTitle("您好，欢迎加入无人忧");
				notice.setContent("无人忧是一个线上线下的网络托管与家务清洁平台；在这里，我们将真诚为您服务！");
				notice.setPublishTime(new Date());
				notice.setState(0);
				notice.setUserId(user_id);
				nService.saveNotice(notice);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean updateUserIcon(int id, String icon) throws Exception {
		int count = userMapper.updateIconByPrimaryKey(id, icon);
		if(count == 1){
			return true;
		}
		return false;
	}

	@Override
	public boolean updateUserSelective(User user) throws Exception {
		int count = userMapper.updateByPrimaryKeySelective(user);
		if(count == 1){
			return true;
		}
		return false;
	}

	@Override
	public List<User> queryAllUser() throws Exception {
		List<User> users = userMapper.selectAllUser();
		return users;
	}

	@Override
	public User queryUserByPhone(String phone_number) throws Exception {
		
		return userMapper.selectByPhone(phone_number);
	}

	@Override
	public List<User> queryUserByNickname(String nickname) throws Exception {

		return userMapper.selectByNickname(nickname);
	}

	@Override
	public List<User> queryUserByKeyword(String keyword) throws Exception {
		return userMapper.selectByUsernameOrNicknameOrAddress(keyword);
	}

	@Override
	public boolean deleteUserById(int id) throws Exception {
		int count = userMapper.deleteByPrimaryKey(id);
		if(count == 1){
			return true;
		}
		return false;
	}

	@Override
	public int queryUserNumber() throws Exception {
		
		return userMapper.selectNumber();
	}

}
