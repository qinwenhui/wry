package cn.qinwh.wry.service;

import java.util.List;

import cn.qinwh.wry.po.TrustOrder;

public interface TrustOrderService {

	public List<TrustOrder> queryAll() throws Exception;
	
	public List<TrustOrder> queryByWaiterId(int waiter_id) throws Exception;
	
	public TrustOrder queryByItemId(int item_id) throws Exception;
	
	public List<TrustOrder> queryByState(int state) throws Exception;
	
	public List<TrustOrder> queryByUserId(int user_id) throws Exception;
	
	public List<TrustOrder> queryByStateAndItemId(int state, int item_id) throws Exception;
	
	public TrustOrder queryTrustOrderById(int id) throws Exception;
	
	public boolean updateTrustOrder(TrustOrder order) throws Exception;
	
	public boolean deleteById(int id) throws Exception;
	
	public boolean addTrustOrder(TrustOrder order) throws Exception;

	public List<TrustOrder> queryByPrice(double minPrice, double maxPrice);
}
