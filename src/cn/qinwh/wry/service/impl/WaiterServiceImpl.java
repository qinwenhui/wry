package cn.qinwh.wry.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qinwh.wry.mapper.WaiterMapper;
import cn.qinwh.wry.po.Waiter;

@Service
public class WaiterServiceImpl implements cn.qinwh.wry.service.WaiterService {

	@Autowired
	private WaiterMapper mapper;
	
	@Override
	public List<Waiter> queryAllWaiter() throws Exception {
		
		return mapper.selectAll();
	}

	@Override
	public Waiter queryWaiterById(int id) throws Exception {
		
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean deleteWaiterById(int id) throws Exception {
		int count = mapper.deleteByPrimaryKey(id);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public boolean addWaiter(Waiter waiter) throws Exception {
		waiter.setWorkTime(new Date());
		int count = mapper.insertSelective(waiter);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public List<Waiter> queryWaiterByKeyword(String keyword) {
		
		return mapper.selectByUsernameOrNicknameOrAddress(keyword);
	}

	@Override
	public boolean updateWaiterIcon(int id, String icon) {
		Waiter waiter = new Waiter();
		waiter.setId(id);
		waiter.setIcon(icon);
		int count = mapper.updateByPrimaryKeySelective(waiter);
		if(count == 1){
			return true;
		}
		return false;
	}

	@Override
	public boolean updateWaiterSelective(Waiter waiter) throws Exception {
		int count = mapper.updateByPrimaryKeySelective(waiter);
		if(count == 1){
			return true;
		}
		return false;
	}

	@Override
	public int queryWaiterNumber() throws Exception {
		
		return mapper.selectNumber();
	}

}
