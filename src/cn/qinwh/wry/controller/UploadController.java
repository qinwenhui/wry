package cn.qinwh.wry.controller;

import java.io.File;
import java.util.Date;

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

import cn.qinwh.wry.service.UserService;
import cn.qinwh.wry.service.WaiterService;
import cn.qinwh.wry.utils.BaseJson;

@Controller
@RequestMapping("api/upload")
public class UploadController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private WaiterService waiterService;
	
	@RequestMapping(value = "/uploadUserIcon", produces = "application/json;charset=utf-8")
	public @ResponseBody String uploadUserIcon(@RequestParam MultipartFile file1, @RequestParam int user_id, HttpServletRequest request) {
		BaseJson json;
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
					File fdir = new File(serverPath+"/upload/userIcon");
					if (!fdir.exists()) {
						fdir.mkdirs();
					}
					// 文件上传到路径下
					File file = new File(fdir, fileName);
					FileUtils.copyInputStreamToFile(file1.getInputStream(), file);
					//把路径存入数据库
					//相对路径
					String user_icon = "/upload/userIcon/"+file.getName();
					if(userService.updateUserIcon(user_id, user_icon)){
						json = new BaseJson(0, "上传成功", user_icon);
					}else{
						json = new BaseJson(500, "上传文件失败", null);
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
	
	@RequestMapping(value = "/uploadItemImage", produces = "application/json;charset=utf-8")
	public @ResponseBody String uploadItemImage(@RequestParam MultipartFile file1, HttpServletRequest request) {
		BaseJson json;
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
					File fdir = new File(serverPath+"/upload/itemImages");
					if (!fdir.exists()) {
						fdir.mkdirs();
					}
					// 文件上传到路径下
					File file = new File(fdir, fileName);
					FileUtils.copyInputStreamToFile(file1.getInputStream(), file);
					//把路径存入数据库
					//相对路径
					String image = "/upload/itemImages/"+file.getName();
					json = new BaseJson(0, "上传成功", image);
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
	 * 上传服务员的头像
	 */
	@RequestMapping(value = "/uploadWaiterIcon", produces = "application/json;charset=utf-8")
	public @ResponseBody String uploadWaiterIcon(@RequestParam MultipartFile file1, @RequestParam int id, HttpServletRequest request) {
		BaseJson json;
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
					//把路径存入数据库
					//相对路径
					String icon = "/upload/waiterIcon/"+file.getName();
					if(waiterService.updateWaiterIcon(id, icon)){
						json = new BaseJson(0, "上传成功", icon);
					}else{
						json = new BaseJson(500, "上传文件失败", null);
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
}
