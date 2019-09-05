package cn.qinwh.wry.mapper;

import java.util.List;

import cn.qinwh.wry.po.Notice;

public interface NoticeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKeyWithBLOBs(Notice record);

    int updateByPrimaryKey(Notice record);

	List<Notice> selectByUserId(Integer user_id);
	
	List<Notice> selectAll();
	
	List<Notice> selectLikeTitleAndContent(String keyword);
}