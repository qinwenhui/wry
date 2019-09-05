package cn.qinwh.wry.mapper;

import java.util.List;

import cn.qinwh.wry.po.ItemKind;

public interface ItemKindMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemKind record);

    int insertSelective(ItemKind record);

    ItemKind selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemKind record);

    int updateByPrimaryKey(ItemKind record);
    
    List<ItemKind> selectAll();

	List<ItemKind> selectByKeyword(String keyword);
}