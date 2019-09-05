package cn.qinwh.wry.service;

import java.util.List;

import cn.qinwh.wry.po.TrustItem;

public interface TrustItemService {

	public List<TrustItem> queryAll() throws Exception;
	
	public List<TrustItem> queryByUserId(int user_id) throws Exception;
	
	public List<TrustItem> queryByKindId(int kind_id) throws Exception;
	
	public List<TrustItem> queryLikeName(String name) throws Exception;
	
	public TrustItem queryById(int id) throws Exception;
	
	public boolean addTrustItem(TrustItem item) throws Exception;
	
	public boolean deleteById(int id) throws Exception;
	
	public boolean updateById(TrustItem item) throws Exception;
}
