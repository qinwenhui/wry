package cn.qinwh.wry.Vo;

import cn.qinwh.wry.po.Feedback;
import cn.qinwh.wry.po.User;

public class FeedbackVo extends Feedback{
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void setFeedback(Feedback feedback){
		setId(feedback.getId());
		setContent(feedback.getContent());
		setUserId(feedback.getUserId());
		setPublishTime(feedback.getPublishTime());
		setState(feedback.getState());
	}

}
