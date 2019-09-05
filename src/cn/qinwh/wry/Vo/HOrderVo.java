package cn.qinwh.wry.Vo;

import cn.qinwh.wry.po.HOrder;
import cn.qinwh.wry.po.User;
import cn.qinwh.wry.po.Waiter;

public class HOrderVo extends HOrder{
	private Waiter waiter;
	private User user;

	public Waiter getWaiter() {
		return waiter;
	}
	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}
	public void setHOrder(HOrder order){
		setId(order.getId());
		setPrice(order.getPrice());
		setSatisfaction(order.getSatisfaction());
		setState(order.getState());
		setTransactionTime(order.getTransactionTime());
		setUserId(order.getUserId());
		setWaiterId(order.getWaiterId());
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
