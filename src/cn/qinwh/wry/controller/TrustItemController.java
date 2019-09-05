package cn.qinwh.wry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.qinwh.wry.po.TrustItem;
import cn.qinwh.wry.service.TrustItemService;
import cn.qinwh.wry.utils.BaseJson;

@Controller
@RequestMapping("api/item")
public class TrustItemController {

	@Autowired
	private TrustItemService service;
	
	@RequestMapping(value="/queryItem", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryItem() throws Exception{
		BaseJson baseJson = null;
		List<TrustItem> items = service.queryAll();
		if(items.size() == 0){
			//没有数据
			baseJson = new BaseJson(1,"暂无数据",null);
		}else{
			baseJson = new BaseJson(0,"共有"+items.size()+"条数据",items);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	/*
	 * 模糊查询
	 */
	@RequestMapping(value="/queryItemByKeywork", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryItemByKeywork(String keywork) throws Exception{
		BaseJson baseJson = null;
		List<TrustItem> items = service.queryLikeName(keywork);
		if(items.size() == 0){
			//没有数据
			baseJson = new BaseJson(1,"暂无数据",null);
		}else{
			baseJson = new BaseJson(0,"共有"+items.size()+"条数据",items);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	@RequestMapping(value="/addItem", produces="application/json; charset=utf-8")
	@ResponseBody
	public String addItem(TrustItem item) throws Exception{
		BaseJson baseJson = null;
		if(service.addTrustItem(item)){
			//item = g
			baseJson = new BaseJson(0,"添加成功",item);
		}else{
			baseJson = new BaseJson(1,"添加失败",item);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	@RequestMapping(value="/updateItem", produces="application/json; charset=utf-8")
	@ResponseBody
	public String updateItem(TrustItem item) throws Exception{
		BaseJson baseJson = null;
		if(service.updateById(item)){
			baseJson = new BaseJson(0,"修改成功",item);
		}else{
			baseJson = new BaseJson(1,"修改失败",item);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	@RequestMapping(value="/deleteItem", produces="application/json; charset=utf-8")
	@ResponseBody
	public String deleteItem(int id) throws Exception{
		BaseJson baseJson = null;
		if(service.deleteById(id)){
			baseJson = new BaseJson(0,"删除成功",id);
		}else{
			baseJson = new BaseJson(1,"删除失败",id);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
}
