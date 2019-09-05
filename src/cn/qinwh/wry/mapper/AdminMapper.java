package cn.qinwh.wry.mapper;

import cn.qinwh.wry.po.Admin;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
    
    Admin selectByUsernameAndPassword(String username, String password);
    
    Admin selectByUsername(String username);

	Admin selectByPassword(int id, String password);

	int selectNumber();
}