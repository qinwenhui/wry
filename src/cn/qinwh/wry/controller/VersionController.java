package cn.qinwh.wry.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.qinwh.wry.po.Version;
import cn.qinwh.wry.service.VersionService;
import cn.qinwh.wry.utils.BaseJson;

@Controller
@RequestMapping("api/version")
public class VersionController {
	
	@Autowired
	private VersionService service;

	/*
	 * 查詢所有版本
	 */
	@RequestMapping(value="/queryAllVersion",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryAllVersion() throws Exception{
		BaseJson baseJsn = null;
		List<Version> versions = service.queryAllVersion();
		if(versions.size() == 0){
			//暂无数据
			baseJsn = new BaseJson(1,"暂无数据",null);
		}else{
			baseJsn = new BaseJson(0,"共有"+versions.size()+"条数据",versions);
		}
		String json = new ObjectMapper().writeValueAsString(baseJsn);
		return json;
	}
	
	/*
	 * 获取最高版本
	 */
	@RequestMapping(value="/queryMaxVersion",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryMaxVersion() throws Exception{
		BaseJson baseJsn = null;
		Version version = service.queryMaxVersion();
		if(version == null){
			//暂无数据
			baseJsn = new BaseJson(1,"暂无数据",null);
		}else{
			baseJsn = new BaseJson(0,"最高版本"+version.getVersionName(),version);
		}
		String json = new ObjectMapper().writeValueAsString(baseJsn);
		return json;
	}
	
	/*
	 * 获取某个版本
	 */
	@RequestMapping(value="/queryVersionById",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryVersionById(int id) throws Exception{
		BaseJson baseJsn = null;
		Version version = service.queryVersionById(id);
		if(version == null){
			//暂无数据
			baseJsn = new BaseJson(1,"暂无数据",null);
		}else{
			baseJsn = new BaseJson(0,"查询成功",version);
		}
		String json = new ObjectMapper().writeValueAsString(baseJsn);
		return json;
	}
	
	/*
	 * 添加新版本
	 */
	@RequestMapping(value="/addVersion",produces="application/json; charset=utf-8")
	@ResponseBody
	public String addVersion(Version version) throws Exception{
		version.setPublishTime(new Date());
		BaseJson baseJsn = null;
		if(service.addVersion(version)){
			//添加成功
			baseJsn = new BaseJson(0,"添加最新版本成功",version);
		}else{
			baseJsn = new BaseJson(1,"添加最新版本失败",null);
		}
		String json = new ObjectMapper().writeValueAsString(baseJsn);
		return json;
	}
	
	/*
	 * 删除某个版本
	 */
	@RequestMapping(value="/deleteVersionById",produces="application/json; charset=utf-8")
	@ResponseBody
	public String deleteVersionById(int id) throws Exception{
		BaseJson baseJsn = null;
		if(service.deleteVersion(id)){
			//删除成功
			baseJsn = new BaseJson(0,"删除成功",null);
		}else{
			baseJsn = new BaseJson(1,"删除失败",null);
		}
		String json = new ObjectMapper().writeValueAsString(baseJsn);
		return json;
	}
	
	/*
	 * 修改某个版本
	 */
	@RequestMapping(value="/updateVersionById",produces="application/json; charset=utf-8")
	@ResponseBody
	public String updateVersionById(Version version) throws Exception{
		BaseJson baseJsn = null;
		if(service.updateVersion(version)){
			//删除成功
			baseJsn = new BaseJson(0,"更新成功",null);
		}else{
			baseJsn = new BaseJson(1,"更新失败",null);
		}
		String json = new ObjectMapper().writeValueAsString(baseJsn);
		return json;
	}
	
	/*
	 * 模糊查询
	 */
	@RequestMapping(value="/queryVersionByKeyword",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryVersionByKeyword(String keyword) throws Exception{
		BaseJson baseJsn = null;
		List<Version> versions = service.queryVersionByKeyword(keyword);
		if(versions.size() == 0){
			//暂无数据
			baseJsn = new BaseJson(1,"暂无数据",null);
		}else{
			baseJsn = new BaseJson(0,"共有"+versions.size()+"条数据",versions);
		}
		String json = new ObjectMapper().writeValueAsString(baseJsn);
		return json;
	}
}
