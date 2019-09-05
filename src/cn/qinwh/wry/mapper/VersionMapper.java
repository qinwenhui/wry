package cn.qinwh.wry.mapper;

import java.util.List;

import cn.qinwh.wry.po.Version;

public interface VersionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Version record);

    int insertSelective(Version record);

    Version selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Version record);

    int updateByPrimaryKey(Version record);
    
    List<Version> selectAll();
    
    Version selectMaxVersion();

	List<Version> selectByKeyword(String keyword);
}