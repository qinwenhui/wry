package cn.qinwh.wry.service;

import java.util.List;

import cn.qinwh.wry.Vo.FeedbackVo;
import cn.qinwh.wry.po.Feedback;

public interface FeedbackService {

	// 添加反馈
	public boolean saveFeedback(int user_id, String content) throws Exception;

	// 获取所有的反馈信息
	public List<Feedback> queryAllFeedback() throws Exception;
	
	//获取所有反馈信息包括该信息的用户信息
	public List<FeedbackVo> queryAllFeedbackAndUser() throws Exception;
	
	//获取某个用户的所有反馈信息
	public List<Feedback> queryFeedbackByUserId(int user_id) throws Exception;
	
	//改变某个反馈信息的状态
	public boolean updateFeedbackById(Feedback feedback) throws Exception;

	public List<FeedbackVo> queryAllFeedbackVo() throws Exception;

	public boolean deleteFeedbackById(int id) throws Exception;

	public FeedbackVo queryFeedbackVoById(int id) throws Exception;

	public List<FeedbackVo> queryFeedbackVoLikeKeyword(String keyword) throws Exception;
	
}
