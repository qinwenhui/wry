package cn.qinwh.wry.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qinwh.wry.mapper.HOrderMapper;
import cn.qinwh.wry.po.HOrder;
import cn.qinwh.wry.service.HOrderService;

@Service
public class HOrderServiceImpl implements HOrderService {

	@Autowired
	private HOrderMapper mapper;
	
	@Override
	public List<HOrder> queryAll() throws Exception {
		
		return mapper.selectAll();
	}

	@Override
	public List<HOrder> queryByWaiterId(int waiter_id) throws Exception {
		
		return mapper.selectByWaiterId(waiter_id);
	}

	@Override
	public List<HOrder> queryByState(int state) throws Exception {
		
		return mapper.selectByState(state);
	}

	@Override
	public List<HOrder> queryByUserId(int user_id) throws Exception {
		
		return mapper.selectByUserId(user_id);
	}

	@Override
	public HOrder queryHOrderById(int id) throws Exception {
		
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateHOrder(HOrder order) throws Exception {
		int count = mapper.updateByPrimaryKeySelective(order);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public boolean deleteById(int id) throws Exception {
		int count = mapper.deleteByPrimaryKey(id);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public boolean addHOrder(HOrder order) throws Exception {
		order.setState(2);
		order.setTransactionTime(new Date());
		order.setSatisfaction(100);
		int count = mapper.insertSelective(order);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public List<HOrder> queryByPrice(double minPrice, double maxPrice) {
		List<HOrder> orders = new ArrayList<>();
		if(minPrice > maxPrice){
			return orders;
		}
		return mapper.selectByPrice(minPrice, maxPrice);
	}

}
