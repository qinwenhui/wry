package cn.qinwh.wry.service;

import java.util.List;

import cn.qinwh.wry.po.HOrder;

public interface HOrderService {

	public List<HOrder> queryAll() throws Exception;
	
	public List<HOrder> queryByWaiterId(int waiter_id) throws Exception;
	
	public List<HOrder> queryByState(int state) throws Exception;
	
	public List<HOrder> queryByUserId(int user_id) throws Exception;
	
	public HOrder queryHOrderById(int id) throws Exception;
	
	public boolean updateHOrder(HOrder order) throws Exception;
	
	public boolean deleteById(int id) throws Exception;
	
	public boolean addHOrder(HOrder order) throws Exception;

	public List<HOrder> queryByPrice(double minPrice, double maxPrice);
}
