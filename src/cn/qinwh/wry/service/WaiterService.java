package cn.qinwh.wry.service;

import java.util.List;

import cn.qinwh.wry.po.Waiter;

public interface WaiterService {
	
	//查询所有的服务员
	public List<Waiter> queryAllWaiter() throws Exception;
	
	//查询某个服务员
	public Waiter queryWaiterById(int id) throws Exception;
	
	//删除某个服务员
	public boolean deleteWaiterById(int id) throws Exception;
	
	//增加服务员
	public boolean addWaiter(Waiter waiter) throws Exception;
	
	//修改某个值
	public boolean updateWaiterSelective(Waiter waiter) throws Exception;

	public List<Waiter> queryWaiterByKeyword(String keyword);

	public boolean updateWaiterIcon(int id, String icon);
	
	public int queryWaiterNumber() throws Exception;

}
