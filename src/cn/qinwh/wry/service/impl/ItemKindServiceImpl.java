package cn.qinwh.wry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qinwh.wry.mapper.ItemKindMapper;
import cn.qinwh.wry.po.ItemKind;
import cn.qinwh.wry.service.ItemKindService;

@Service
public class ItemKindServiceImpl implements ItemKindService {

	@Autowired
	private ItemKindMapper mapper;
	
	@Override
	public List<ItemKind> queryKindAll() throws Exception {
		
		return mapper.selectAll();
	}

	@Override
	public ItemKind queryKindById(int id) throws Exception {
		
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean addKind(ItemKind kind) throws Exception {
		int count = mapper.insertSelective(kind);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public boolean deleteKindById(int id) throws Exception {
		int count = mapper.deleteByPrimaryKey(id);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public boolean updateKindById(ItemKind kind) throws Exception {
		int count = mapper.updateByPrimaryKeySelective(kind);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public List<ItemKind> queryKindByKeyword(String keyword) throws Exception {
		
		return mapper.selectByKeyword(keyword);
	}

}
