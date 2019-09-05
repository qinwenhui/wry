package cn.qinwh.wry.mapper;

import java.util.List;

import cn.qinwh.wry.po.HOrder;

public interface HOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HOrder record);

    int insertSelective(HOrder record);

    HOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HOrder record);

    int updateByPrimaryKey(HOrder record);
    
    List<HOrder> selectAll();
    
    List<HOrder> selectByWaiterId(Integer waiter_id);
    
    List<HOrder> selectByUserId(Integer user_id);
    
    List<HOrder> selectByState(Integer state);
    
    List<HOrder> selectByPrice(double minPrice, double maxPrice);
}