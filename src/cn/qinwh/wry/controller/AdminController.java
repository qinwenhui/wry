package cn.qinwh.wry.controller;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.qinwh.wry.po.Admin;
import cn.qinwh.wry.service.AdminService;
import cn.qinwh.wry.utils.BaseJson;
import cn.qinwh.wry.utils.ClientUtils;

@Controller
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	/*
	 * 统一异常处理
	 */
	@ExceptionHandler
	@ResponseBody
	public String exception(Exception e){
		System.out.println(e.getMessage());
		BaseJson json = new BaseJson(400, "请求出错,请检查参数"+e.getMessage(), null);
		String jsonStr = "";
		try {
			jsonStr = new ObjectMapper().writeValueAsString(json);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return jsonStr;
	}
	
	
	//登录
	@RequestMapping(value="/login", produces="application/json; charset=utf-8")
	@ResponseBody
	public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) throws Exception{
		Admin admin = adminService.login(username, password);
		BaseJson baseJson = null;
		if(admin != null){
			//登录成功
			//存入session
			HttpSession session = request.getSession();
			session.setAttribute("admin", admin);
			//保存七天
			session.setMaxInactiveInterval(60*60*24*7);
			baseJson = new BaseJson(0,"登录成功",admin);
		}else{
			//登录失败
			baseJson = new BaseJson(1,"登录失败",admin);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	/*
	 * 检查登录
	 */
	@RequestMapping(value="/checkLogin", produces="application/json; charset=utf-8")
	@ResponseBody
	public String login(HttpServletRequest request) throws Exception{
		//通过session获取登录信息
		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		BaseJson baseJson = null;
		if(admin != null){
			//登录成功
			baseJson = new BaseJson(0,"已经登录",admin);
		}else{
			//登录失败
			baseJson = new BaseJson(1,"暂未登录",admin);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	/*
	 * 注销登录
	 */
	@RequestMapping(value="/cleanLogin", produces="application/json; charset=utf-8")
	@ResponseBody
	public String cleanLogin(HttpServletRequest request) throws Exception{
		//通过session获取登录信息
		HttpSession session = request.getSession();
		session.setAttribute("admin", null);
		BaseJson baseJson = null;
		baseJson = new BaseJson(0,"注销成功",null);
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	//注册
	@RequestMapping(value="/register", produces="application/json; charset=utf-8")
	@ResponseBody
	public String register (@RequestParam String username, @RequestParam String password, HttpServletRequest request) throws Exception{
		BaseJson baseJson = null;
		if(adminService.register(username, password)){
			//注册成功
			baseJson = new BaseJson(0,"注册成功",null);
		}else{
			//注册失败
			baseJson = new BaseJson(1,"注册失败,用户名或密码可能已经存在",null);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	/*
	 * 获取客户端的ip地址并返回给前端显示
	 */
	@RequestMapping(value="/queryClientIp", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryClientIp(HttpServletRequest request) throws Exception{
		String ip = ClientUtils.getIpAddress(request);
		BaseJson baseJson = new BaseJson(0,"获取ip地址成功",ip);
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}

	/*
	 * 检查密码是否正确
	 */
	@RequestMapping(value = "/checkPassword", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String checkPassword(int id, String password)
			throws Exception {
		BaseJson baseJson = null;
		Admin admin = adminService.getAdminByPassword(id, password);
		if (admin != null) {
			// 存在用戶
			baseJson = new BaseJson(0, "查詢成功", admin);
		} else {
			baseJson = new BaseJson(1, "不存在該用戶", null);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	/*
	 * 修改管理员密码
	 */
	@RequestMapping(value="/updatePassword", produces="application/json; charset=utf-8")
	@ResponseBody
	public String updatePassword(int id, String password) throws Exception{
		BaseJson baseJson = null;
		boolean ok = adminService.updatePassword(id, password);
		if(ok){
			//修改成功
			baseJson = new BaseJson(0,"修改成功",null);
		}else{
			//修改失败
			baseJson = new BaseJson(1,"修改失败",null);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	/*
	 * 查询管理员的数量
	 */
	@RequestMapping(value="/queryAdminNumber", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryAdminNumber() throws Exception{
		BaseJson baseJson = null;
		int number = adminService.queryAdminNumber();
		baseJson = new BaseJson(0,"查询成功",number);
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	/*
	 * 修改管理员头像
	 */
	@RequestMapping(value="/updateIcon", produces="application/json; charset=utf-8")
	@ResponseBody
	public String updateIcon(MultipartFile file1, HttpServletRequest request, int id){
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
					String icon = "/upload/userIcon/"+file.getName();
					//把修改后的信息保存到session里，不然用户的session不变必须要登录时前端页面菜能显示新头像
					if(request.getSession().getAttribute("admin") != null){
						Admin admin = (Admin) request.getSession().getAttribute("admin");
						admin.setIcon(icon);
						request.getSession().setAttribute("admin", admin);
					}
					if(adminService.updateIcon(id, icon)){
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
