package cn.qinwh.wry.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qinwh.wry.Vo.FeedbackVo;
import cn.qinwh.wry.mapper.FeedbackMapper;
import cn.qinwh.wry.mapper.UserMapper;
import cn.qinwh.wry.po.Feedback;
import cn.qinwh.wry.po.User;
import cn.qinwh.wry.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackMapper fMapper;
	@Autowired
	private UserMapper uMapper;
	
	@Override
	public boolean saveFeedback(int user_id, String content) throws Exception {
		Feedback feedback = new Feedback();
		feedback.setContent(content);
		feedback.setUserId(user_id);
		feedback.setPublishTime(new Date());
		feedback.setState(0);
		int count = fMapper.insert(feedback);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public List<Feedback> queryAllFeedback() throws Exception {
		List<Feedback> feedbacks = fMapper.selectAll();
		return feedbacks;
	}

	@Override
	public List<FeedbackVo> queryAllFeedbackAndUser() throws Exception {
		List<FeedbackVo> feedbackVos = new ArrayList<>();
		List<Feedback> feedbacks = fMapper.selectAll();
		for(Feedback f : feedbacks){
			User user = uMapper.selectByPrimaryKey(f.getUserId());
			FeedbackVo fv = new FeedbackVo();
			fv.setFeedback(f);
			fv.setUser(user);
			feedbackVos.add(fv);
		}
		return feedbackVos;
	}

	@Override
	public List<Feedback> queryFeedbackByUserId(int user_id) throws Exception {

		return fMapper.selectByUserId(user_id);
	}

	@Override
	public boolean updateFeedbackById(Feedback feedback) throws Exception {
		int count = fMapper.updateByPrimaryKeySelective(feedback);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public List<FeedbackVo> queryAllFeedbackVo() throws Exception {
		List<Feedback> fs = fMapper.selectAll();
		List<FeedbackVo> fvs = new ArrayList<>();
		for(Feedback f:fs){
			FeedbackVo fv = new FeedbackVo();
			fv.setFeedback(f);
			int user_id = f.getUserId();
			User user = uMapper.selectByPrimaryKey(user_id);
			fv.setUser(user);
			fvs.add(fv);
		}
		return fvs;
	}

	@Override
	public boolean deleteFeedbackById(int id) throws Exception {
		int count = fMapper.deleteByPrimaryKey(id);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public FeedbackVo queryFeedbackVoById(int id) throws Exception {
		Feedback f = fMapper.selectByPrimaryKey(id);
		FeedbackVo fv = new FeedbackVo();
		fv.setFeedback(f);
		int user_id = f.getUserId();
		User user = uMapper.selectByPrimaryKey(user_id);
		fv.setUser(user);
		return fv;
	}

	@Override
	public List<FeedbackVo> queryFeedbackVoLikeKeyword(String keyword) throws Exception {
		List<Feedback> fs = fMapper.selectLikeContent(keyword);
		List<FeedbackVo> fvs = new ArrayList<>();
		for(Feedback f:fs){
			FeedbackVo fv = new FeedbackVo();
			fv.setFeedback(f);
			int user_id = f.getUserId();
			User user = uMapper.selectByPrimaryKey(user_id);
			fv.setUser(user);
			fvs.add(fv);
		}
		return fvs;
	}

}
