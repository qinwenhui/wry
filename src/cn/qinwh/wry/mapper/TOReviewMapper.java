package cn.qinwh.wry.mapper;

import java.util.List;

import cn.qinwh.wry.po.TOReview;

public interface TOReviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TOReview record);

    int insertSelective(TOReview record);

    TOReview selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TOReview record);

    int updateByPrimaryKeyWithBLOBs(TOReview record);

    int updateByPrimaryKey(TOReview record);

	TOReview selectByOrderId(Integer order_id);
	
	List<TOReview> selectAll();
	
	List<TOReview> selectByUserId(Integer user_id);

	List<TOReview> selectByKeyword(String keyword);
}