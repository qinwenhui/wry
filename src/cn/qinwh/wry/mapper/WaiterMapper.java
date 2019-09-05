package cn.qinwh.wry.mapper;

import java.util.List;

import cn.qinwh.wry.po.Waiter;

public interface WaiterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Waiter record);

    int insertSelective(Waiter record);

    Waiter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Waiter record);

    int updateByPrimaryKeyWithBLOBs(Waiter record);

    int updateByPrimaryKey(Waiter record);
    
    List<Waiter> selectAll();

	List<Waiter> selectByUsernameOrNicknameOrAddress(String keyword);
	
	int selectNumber();
}