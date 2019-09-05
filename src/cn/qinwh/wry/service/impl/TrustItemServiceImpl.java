package cn.qinwh.wry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qinwh.wry.mapper.TrustItemMapper;
import cn.qinwh.wry.po.TrustItem;
import cn.qinwh.wry.service.TrustItemService;

@Service
public class TrustItemServiceImpl implements TrustItemService {

	@Autowired
	private TrustItemMapper mapper;
	
	@Override
	public List<TrustItem> queryAll() throws Exception {
		
		return mapper.selectAll();
	}

	@Override
	public List<TrustItem> queryByUserId(int user_id) throws Exception {
		
		return mapper.selectByUserId(user_id);
	}

	@Override
	public List<TrustItem> queryByKindId(int kind_id) throws Exception {
		
		return mapper.selectByKindId(kind_id);
	}

	@Override
	public List<TrustItem> queryLikeName(String name) throws Exception {
		
		return mapper.selectLikeName(name);
	}

	@Override
	public boolean addTrustItem(TrustItem item) throws Exception {
		int count = mapper.insertSelective(item);
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
	public boolean updateById(TrustItem item) throws Exception {
		int count = mapper.updateByPrimaryKeySelective(item);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public TrustItem queryById(int id) throws Exception {
		
		return mapper.selectByPrimaryKey(id);
	}

}
