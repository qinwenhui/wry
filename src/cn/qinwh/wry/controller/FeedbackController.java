package cn.qinwh.wry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.qinwh.wry.Vo.FeedbackVo;
import cn.qinwh.wry.po.Feedback;
import cn.qinwh.wry.service.FeedbackService;
import cn.qinwh.wry.utils.BaseJson;

@Controller
@RequestMapping("/api/feedback")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;
	
	/*
	 * 获取所有的反馈信息
	 */
	@RequestMapping(value="/getAllFeedback", produces="application/json; charset=utf-8")
	public @ResponseBody String getAllFeedback() throws Exception{
		List<Feedback> feedbacks = feedbackService.queryAllFeedback();
		BaseJson baseJson = null;
		if(feedbacks.size() > 0){
			//有数据
			baseJson = new BaseJson(0, "查到"+feedbacks.size()+"条数据", feedbacks);
		}else{
			//没有数据
			baseJson = new BaseJson(1, "暂无数据", null);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
		
	}
	
	/*
	 * 获取所有的反馈信息(Vo类型)
	 */
	@RequestMapping(value="/getAllFeedbackVo", produces="application/json; charset=utf-8")
	public @ResponseBody String getAllFeedbackVo() throws Exception{
		List<FeedbackVo> feedbacks = feedbackService.queryAllFeedbackVo();
		BaseJson baseJson = null;
		if(feedbacks.size() > 0){
			//有数据
			baseJson = new BaseJson(0, "查到"+feedbacks.size()+"条数据", feedbacks);
		}else{
			//没有数据
			baseJson = new BaseJson(1, "暂无数据", null);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
		
	}
	
	/*
	 * 模糊查询获取所有的反馈信息(Vo类型)
	 */
	@RequestMapping(value="/getFeedbackVoLikeKeyword", produces="application/json; charset=utf-8")
	public @ResponseBody String getFeedbackVoLikeKeyword(String keyword) throws Exception{
		List<FeedbackVo> feedbacks = feedbackService.queryFeedbackVoLikeKeyword(keyword);
		BaseJson baseJson = null;
		if(feedbacks.size() > 0){
			//有数据
			baseJson = new BaseJson(0, "查到"+feedbacks.size()+"条数据", feedbacks);
		}else{
			//没有数据
			baseJson = new BaseJson(1, "暂无数据", null);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
		
	}
	
	/*
	 * 获取某个用户的反馈信息
	 */
	@RequestMapping(value="/getFeedbackByUserId", produces="application/json; charset=utf-8")
	public @ResponseBody String getFeedbackByUserId(int user_id) throws Exception{
		List<Feedback> feedbacks = feedbackService.queryFeedbackByUserId(user_id);
		BaseJson baseJson = null;
		if(feedbacks.size() > 0){
			//有数据
			baseJson = new BaseJson(0, "查到"+feedbacks.size()+"条数据", feedbacks);
		}else{
			//没有数据
			baseJson = new BaseJson(1, "暂无数据", null);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
		
	}
	
	/*
	 * 获取某个反馈信息Vo
	 */
	@RequestMapping(value="/getFeedbackVoById", produces="application/json; charset=utf-8")
	public @ResponseBody String getFeedbackVoById(int id) throws Exception{
		FeedbackVo feedback = feedbackService.queryFeedbackVoById(id);
		BaseJson baseJson = null;
		if(feedback != null){
			//有数据
			baseJson = new BaseJson(0, "查询成功", feedback);
		}else{
			//没有数据
			baseJson = new BaseJson(1, "暂无数据", null);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
		
	}
	
	/*
	 * 发一个反馈信息
	 */
	@RequestMapping(value="/sendFeedback", produces="application/json; charset=utf-8")
	public @ResponseBody String sendFeedback(int user_id, String content) throws Exception{
		BaseJson baseJson = null;
		if(feedbackService.saveFeedback(user_id, content)){
			//发送成功
			baseJson = new BaseJson(0, "反馈成功", null);
		}else{
			baseJson = new BaseJson(1, "反馈失败", null);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	/*
	 * 删除某个反馈信息
	 */
	@RequestMapping(value="/deleteFeedback", produces="application/json; charset=utf-8")
	public @ResponseBody String deleteFeedback(int id) throws Exception{
		BaseJson baseJson = null;
		if(feedbackService.deleteFeedbackById(id)){
			//发送成功
			baseJson = new BaseJson(0, "删除成功", null);
		}else{
			baseJson = new BaseJson(1, "删除失败", null);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
}
