package cn.qinwh.wry.Vo;

import cn.qinwh.wry.po.TOReview;
import cn.qinwh.wry.po.TrustOrder;
import cn.qinwh.wry.po.User;

public class TOReviewVo extends TOReview{
	
	private User user;
	private TrustOrder order;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public TrustOrder getOrder() {
		return order;
	}
	public void setOrder(TrustOrder order) {
		this.order = order;
	}
	public void setReview(TOReview review) {
		this.setId(review.getId());
		this.setContent(review.getContent());
		this.setOrderId(review.getOrderId());
		this.setPublishTime(review.getPublishTime());
		this.setUserId(review.getUserId());
	}

}
