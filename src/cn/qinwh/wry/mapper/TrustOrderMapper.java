package cn.qinwh.wry.mapper;

import java.util.List;

import cn.qinwh.wry.po.TrustOrder;

public interface TrustOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TrustOrder record);

    int insertSelective(TrustOrder record);

    TrustOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TrustOrder record);

    int updateByPrimaryKey(TrustOrder record);
    
    List<TrustOrder> selectAll();
    
    List<TrustOrder> selectByWaiterId(Integer waiter_id);
    
    TrustOrder selectByItemId(Integer item_id);
    
    List<TrustOrder> selectByState(Integer state);
    
    List<TrustOrder> selectByStateAndItemId(Integer state, Integer item_id);

	List<TrustOrder> selectByPrice(double minPrice, double maxPrice);
}