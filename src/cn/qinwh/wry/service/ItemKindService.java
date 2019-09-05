package cn.qinwh.wry.service;

import java.util.List;

import cn.qinwh.wry.po.ItemKind;

public interface ItemKindService {

	//查询所有种类
	public List<ItemKind> queryKindAll() throws Exception;
	
	//查询某个种类
	public ItemKind queryKindById(int id) throws Exception;
	
	//增加种类
	public boolean addKind(ItemKind kind) throws Exception;
	
	//删除某个种类
	public boolean deleteKindById(int id) throws Exception;
	
	//更新某个种类
	public boolean updateKindById(ItemKind kind) throws Exception;

	public List<ItemKind> queryKindByKeyword(String keyword) throws Exception;
}
