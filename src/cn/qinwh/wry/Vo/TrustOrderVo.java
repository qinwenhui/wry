package cn.qinwh.wry.Vo;

import cn.qinwh.wry.po.TrustItem;
import cn.qinwh.wry.po.TrustOrder;
import cn.qinwh.wry.po.User;
import cn.qinwh.wry.po.Waiter;

public class TrustOrderVo extends TrustOrder{

	private TrustItem item;
	private Waiter waiter;
	private User user;
	public TrustItem getItem() {
		return item;
	}
	public void setItem(TrustItem item) {
		this.item = item;
	}
	public Waiter getWaiter() {
		return waiter;
	}
	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}
	public void setTrustOrder(TrustOrder order){
		setId(order.getId());
		setItemId(order.getItemId());
		setPrice(order.getPrice());
		setSatisfaction(order.getSatisfaction());
		setState(order.getState());
		setTransactionTime(order.getTransactionTime());
		setTrustDuration(order.getTrustDuration());
		setWaiterId(order.getWaiterId());
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
