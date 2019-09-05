package cn.qinwh.wry.mapper;

import java.util.List;

import cn.qinwh.wry.po.Feedback;

public interface FeedbackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Feedback record);

    int insertSelective(Feedback record);

    Feedback selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Feedback record);

    int updateByPrimaryKeyWithBLOBs(Feedback record);

    int updateByPrimaryKey(Feedback record);
    
    List<Feedback> selectAll();
    
    List<Feedback> selectByUserId(Integer user_id);

	List<Feedback> selectLikeContent(String keyword);
}