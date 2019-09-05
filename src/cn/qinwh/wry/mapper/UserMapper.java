package cn.qinwh.wry.mapper;

import java.util.List;

import cn.qinwh.wry.po.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	int updateIconByPrimaryKey(int id, String icon);

	User selectByUsername(String username);

	User selectByUsernameAndPassword(String username, String password);
	
	List<User> selectAllUser();
	
	User selectByPhone(String phone_number);
	
	List<User> selectByNickname(String nickname);

	List<User> selectByUsernameOrNicknameOrAddress(String keyword);
	
	int selectNumber();
}