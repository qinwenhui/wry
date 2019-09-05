package cn.qinwh.wry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.qinwh.wry.po.ItemKind;
import cn.qinwh.wry.service.ItemKindService;
import cn.qinwh.wry.utils.BaseJson;

@Controller
@RequestMapping("api/kind")
public class ItemKindController {

	@Autowired
	private ItemKindService service;
	
	@RequestMapping(value="/queryKind", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryKind() throws Exception{
		BaseJson baseJson = null;
		List<ItemKind> kinds = service.queryKindAll();
		if(kinds.size() == 0){
			//没有数据
			baseJson = new BaseJson(1,"暂无数据",null);
		}else{
			baseJson = new BaseJson(0,"共有"+kinds.size()+"条数据",kinds);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	/*
	 * 通过id查询种类
	 */
	@RequestMapping(value="/queryKindById", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryKindById(int id) throws Exception{
		BaseJson baseJson = null;
		ItemKind kind = service.queryKindById(id);
		if(kind == null){
			//没有数据
			baseJson = new BaseJson(1,"暂无数据",null);
		}else{
			baseJson = new BaseJson(0,"查询成功",kind);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	/*
	 * 模糊查询种类
	 */
	@RequestMapping(value="/queryKindByKeyword", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryKindByKeyword(String keyword) throws Exception{
		BaseJson baseJson = null;
		List<ItemKind> kinds = service.queryKindByKeyword(keyword);
		if(kinds.size() == 0){
			//没有数据
			baseJson = new BaseJson(1,"暂无数据",null);
		}else{
			baseJson = new BaseJson(0,"共有"+kinds.size()+"条数据",kinds);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	
	@RequestMapping(value="/addKind", produces="application/json; charset=utf-8")
	@ResponseBody
	public String addKind(String name, String description, double price) throws Exception{
		BaseJson baseJson = null;
		ItemKind kind = new ItemKind();
		kind.setName(name);
		kind.setDescription(description);
		kind.setPrice(price);
		if(service.addKind(kind)){
			baseJson = new BaseJson(0,"添加成功",kind);
		}else{
			baseJson = new BaseJson(1,"添加失败",kind);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	@RequestMapping(value="/updateKind", produces="application/json; charset=utf-8")
	@ResponseBody
	public String updateKind(ItemKind kind) throws Exception{
		BaseJson baseJson = null;
		if(service.updateKindById(kind)){
			baseJson = new BaseJson(0,"修改成功",kind);
		}else{
			baseJson = new BaseJson(1,"修改失败",kind);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	@RequestMapping(value="/deleteKind", produces="application/json; charset=utf-8")
	@ResponseBody
	public String deleteKind(int id) throws Exception{
		BaseJson baseJson = null;
		if(service.deleteKindById(id)){
			baseJson = new BaseJson(0,"删除成功",id);
		}else{
			baseJson = new BaseJson(1,"删除失败",id);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
}
