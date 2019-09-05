package cn.qinwh.wry.mapper;

import java.util.List;

import cn.qinwh.wry.po.TrustItem;

public interface TrustItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TrustItem record);

    int insertSelective(TrustItem record);

    TrustItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TrustItem record);

    int updateByPrimaryKeyWithBLOBs(TrustItem record);

    int updateByPrimaryKey(TrustItem record);
    
    List<TrustItem> selectAll();
    
    List<TrustItem> selectByUserId(Integer userId);
    
    List<TrustItem> selectLikeName(String name);
    
    List<TrustItem> selectByKindId(Integer kindId);
}