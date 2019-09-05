package cn.qinwh.wry.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import cn.qinwh.wry.po.Waiter;
import cn.qinwh.wry.service.WaiterService;
import cn.qinwh.wry.utils.BaseJson;

@Controller
@RequestMapping("api/waiter")
public class WaiterController {

	@Autowired
	private WaiterService service;
	
	/*
	 * 查询所有服务员
	 */
	@RequestMapping(value="/queryAllWaiter", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryAllWaiter() throws Exception{
		BaseJson baseJson = null;
		List<Waiter> waiters = service.queryAllWaiter();
		if(waiters.size() == 0){
			//没有数据
			baseJson = new BaseJson(1,"暂无数据",null);
		}else{
			baseJson = new BaseJson(0,"共有"+waiters.size()+"条数据",waiters);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	/*
	 * 关键词查询服务员
	 */
	@RequestMapping(value="/queryWaiterByKeyword", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryWaiterByKeyword(String keyword) throws Exception{
		BaseJson baseJson = null;
		List<Waiter> waiters = service.queryWaiterByKeyword(keyword);
		if(waiters.size() == 0){
			//没有数据
			baseJson = new BaseJson(1,"暂无数据",null);
		}else{
			baseJson = new BaseJson(0,"共有"+waiters.size()+"条数据",waiters);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	/*
	 * 查询某个服务员
	 */
	@RequestMapping(value="/queryWaiterById", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryWaiterById(int id) throws Exception{
		BaseJson baseJson = null;
		Waiter waiter = service.queryWaiterById(id);
		if(waiter == null){
			//没有数据
			baseJson = new BaseJson(1,"暂无数据",null);
		}else{
			baseJson = new BaseJson(0,"查询成功",waiter);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	/*
	 * 查询服务员的数量
	 */
	@RequestMapping(value="/queryWaiterNumber", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryWaiterNumber() throws Exception{
		BaseJson baseJson = null;
		int number = service.queryWaiterNumber();
		baseJson = new BaseJson(0,"查询成功",number);
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	/*
	 * 添加服务员，包括头像
	 */
	@RequestMapping(value = "/addWaiter", produces = "application/json;charset=utf-8")
	public @ResponseBody String addWaiter(@RequestParam MultipartFile file1, Waiter waiter,
			HttpServletRequest request) {
		BaseJson json = null;
		String jsonStr = "";
		if (file1.isEmpty()) {
			json = new BaseJson(405, "请求出错,请检查参数,文件不能为空", null);
		} else {
			//文件全名
			String originalFilename = file1.getOriginalFilename();
			//文件前缀名
			String fileBaseName = FilenameUtils.getBaseName(originalFilename);
			//文件扩展名
			String extensionName = FilenameUtils.getExtension(originalFilename);
			if(extensionName.equals("jpg")||extensionName.equals("jpeg")||extensionName.equals("png")){
				//后缀名正确
				//生成时间戳
				long time = new Date().getTime();
				//生成的文件名
				String fileName = fileBaseName+time+"."+extensionName;
				//服务器目录
				String serverPath = System.getProperty("wry.webapp");
				try {
					// 创建要上传的路径
					File fdir = new File(serverPath+"/upload/waiterIcon");
					if (!fdir.exists()) {
						fdir.mkdirs();
					}
					// 文件上传到路径下
					File file = new File(fdir, fileName);
					FileUtils.copyInputStreamToFile(file1.getInputStream(), file);
					//把路径存入waiter对象
					//相对路径
					String icon = "/upload/waiterIcon/"+file.getName();
					waiter.setIcon(icon);
					if(service.addWaiter(waiter)){
						json = new BaseJson(0, "添加成功", waiter);
					}else{
						json = new BaseJson(1, "添加失败", waiter);
					}
				} catch (Exception e) {
					json = new BaseJson(500, "请求出错,文件转移失败"+e.getMessage(), null);
				}
			}else{
				//后缀名不符合图片规范
				json = new BaseJson(501, "上传出错,只能上传jpg,jpeg,png格式的图像", originalFilename);
			}
		}
		try {
			 jsonStr = new ObjectMapper().writeValueAsString(json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	/*
	 * 删除服务员
	 */
	@RequestMapping(value="/deleteWaiterById", produces="application/json; charset=utf-8")
	@ResponseBody
	public String deleteWaiterById(int id) throws Exception{
		BaseJson baseJson = null;
		if(service.deleteWaiterById(id)){
			baseJson = new BaseJson(0,"删除成功",null);
		}else{
			baseJson = new BaseJson(1,"删除失败",null);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
}
