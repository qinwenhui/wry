package cn.qinwh.wry.Vo;

import cn.qinwh.wry.po.HOReview;
import cn.qinwh.wry.po.HOrder;
import cn.qinwh.wry.po.User;

public class HOReviewVo extends HOReview{
	
	private User user;
	private HOrder order;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public HOrder getOrder() {
		return order;
	}
	public void setOrder(HOrder order) {
		this.order = order;
	}
	public void setReview(HOReview review) {
		this.setId(review.getId());
		this.setContent(review.getContent());
		this.setOrderId(review.getOrderId());
		this.setPublishTime(review.getPublishTime());
		this.setUserId(review.getUserId());
	}
}
