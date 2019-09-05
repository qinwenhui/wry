package cn.qinwh.wry.mapper;

import cn.qinwh.wry.po.Token;

public interface TokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Token record);

    int insertSelective(Token record);

    Token selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Token record);

    int updateByPrimaryKey(Token record);
    
    Token selectByToken(String token);
}