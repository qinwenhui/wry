package cn.qinwh.wry.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qinwh.wry.mapper.TrustItemMapper;
import cn.qinwh.wry.mapper.TrustOrderMapper;
import cn.qinwh.wry.po.TrustItem;
import cn.qinwh.wry.po.TrustOrder;
import cn.qinwh.wry.service.TrustOrderService;

@Service
public class TrustOrderServiceImpl implements TrustOrderService{

	@Autowired
	private TrustOrderMapper mapper;
	@Autowired
	private TrustItemMapper tiMapper;
	
	@Override
	public List<TrustOrder> queryAll() throws Exception {
		
		return mapper.selectAll();
	}

	@Override
	public List<TrustOrder> queryByWaiterId(int waiter_id) throws Exception {
		
		return mapper.selectByWaiterId(waiter_id);
	}

	@Override
	public TrustOrder queryByItemId(int item_id) throws Exception {
		
		return mapper.selectByItemId(item_id);
	}

	@Override
	public List<TrustOrder> queryByState(int state) throws Exception {
		
		return mapper.selectByState(state);
	}

	@Override
	public List<TrustOrder> queryByStateAndItemId(int state, int item_id) throws Exception {
		
		return mapper.selectByStateAndItemId(state, item_id);
	}

	@Override
	public boolean updateTrustOrder(TrustOrder order) throws Exception {
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
	public boolean addTrustOrder(TrustOrder order) throws Exception {
		int count = mapper.insertSelective(order);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public List<TrustOrder> queryByUserId(int user_id) throws Exception {
		//先查询该用户的托管的物品
		List<TrustItem> uItems = tiMapper.selectByUserId(user_id);
		//再通过每个物品id查询对应的订单
		List<TrustOrder> orders = new ArrayList<>();
		for(TrustItem i:uItems){
			TrustOrder order = mapper.selectByItemId(i.getId());
			if(order != null)
				orders.add(order);
		}
		return orders;
	}

	@Override
	public TrustOrder queryTrustOrderById(int id) throws Exception {
		
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<TrustOrder> queryByPrice(double minPrice, double maxPrice) {
		List<TrustOrder> orders = new ArrayList<>();
		if(minPrice > maxPrice){
			return orders;
		}
		return mapper.selectByPrice(minPrice, maxPrice);
	}

}
