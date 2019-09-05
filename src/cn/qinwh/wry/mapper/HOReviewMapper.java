package cn.qinwh.wry.mapper;

import java.util.List;

import cn.qinwh.wry.po.HOReview;

public interface HOReviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HOReview record);

    int insertSelective(HOReview record);

    HOReview selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HOReview record);

    int updateByPrimaryKeyWithBLOBs(HOReview record);

    int updateByPrimaryKey(HOReview record);
    
    HOReview selectByOrderId(Integer order_id);
    
	List<HOReview> selectAll();
	
	List<HOReview> selectByUserId(Integer user_id);
	
	List<HOReview> selectByKeyword(String keyword);
}