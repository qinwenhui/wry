package cn.qinwh.wry.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.qinwh.wry.Vo.UserVo;
import cn.qinwh.wry.po.Token;
import cn.qinwh.wry.po.User;
import cn.qinwh.wry.service.TokenService;
import cn.qinwh.wry.service.UserService;
import cn.qinwh.wry.utils.BaseJson;
import cn.qinwh.wry.utils.CharacterUtils;

@Controller
@RequestMapping("api/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;
	
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
	
	
	
	@RequestMapping(value="/queryUser", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryUserById(int user_id) throws Exception{
		User user = userService.queryUserById(user_id);
		String jsonStr = "";
		if(user == null){
			//不存在该用户
			BaseJson json = new BaseJson(1, "用户不存在", user);
			jsonStr = new ObjectMapper().writeValueAsString(json);
			
		}else{
			BaseJson json = new BaseJson(0, "查询成功", user);
			jsonStr = new ObjectMapper().writeValueAsString(json);
		}
		return jsonStr;
	}
	
	/*
	 * 查询所有用户
	 */
	@RequestMapping(value="/queryUserAll", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryUserAll() throws Exception{
		List<User> users = userService.queryAllUser();
		String jsonStr = "";
		if(users.size() == 0){
			//暂无数据
			BaseJson json = new BaseJson(1, "暂无数据", null);
			jsonStr = new ObjectMapper().writeValueAsString(json);
			
		}else{
			BaseJson json = new BaseJson(0, "查询成功", users);
			jsonStr = new ObjectMapper().writeValueAsString(json);
		}
		return jsonStr;
	}
	/*
	 * 根据关键词查询用户
	 */
	@RequestMapping(value="/queryUserByKeyword", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryUserByKeyword(String keyword) throws Exception{
		List<User> users = userService.queryUserByKeyword(keyword);
		String jsonStr = "";
		if(users.size() == 0){
			//暂无数据
			BaseJson json = new BaseJson(1, "暂无数据", null);
			jsonStr = new ObjectMapper().writeValueAsString(json);
			
		}else{
			BaseJson json = new BaseJson(0, "查询成功", users);
			jsonStr = new ObjectMapper().writeValueAsString(json);
		}
		return jsonStr;
	}
	
	/*
	 * 查询用户的数量
	 */
	@RequestMapping(value="/queryUserNumber", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryUserNumber() throws Exception{
		BaseJson baseJson = null;
		int number = userService.queryUserNumber();
		baseJson = new BaseJson(0,"查询成功",number);
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	/*
	 * 注册
	 */
	@RequestMapping(value="/register", produces="application/json; charset=utf-8", method=RequestMethod.POST)
	@ResponseBody
	public String register(String username, String password) throws Exception{
		boolean ok = userService.register(username, password);
		String jsonStr = "";
		if(ok){
			//注册成功，返回注册成功信息
			BaseJson json = new BaseJson(0, "注册成功", null);
			jsonStr = new ObjectMapper().writeValueAsString(json);
		}else{
			BaseJson json = new BaseJson(1, "注册失败，用户可能已存在", null);
			jsonStr = new ObjectMapper().writeValueAsString(json);
		}
		return jsonStr;
	}
	
	/*
	 * 登录
	 */
	@RequestMapping(value="/login", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	@ResponseBody
	public String login(String username,String password,String device) throws Exception{
		User user = userService.getUserByUsernameAndPassword(username,password);
		String jsonStr = "";
		if(user == null){
			//不存在该用户
			BaseJson json = new BaseJson(1, "用户名或密码错误", null);
			jsonStr = new ObjectMapper().writeValueAsString(json);
			
		}else{
			//生成相应的token
			Token token = new Token();
			token.setToken(CharacterUtils.getRandomString(20)+System.currentTimeMillis());
			token.setCreatedTime(new Date());
			token.setDevice(device);
			token.setUserId(user.getId());
			token.setState(0);
			if(tokenService.saveToken(token)){
				UserVo userVo = new UserVo();
				userVo.setUser(user);
				userVo.setToken(token.getToken());
				BaseJson json = new BaseJson(0, "登录成功", userVo);
				jsonStr = new ObjectMapper().writeValueAsString(json);
			}else{
				BaseJson json = new BaseJson(2, "登录出错,生成Token失败", null);
				jsonStr = new ObjectMapper().writeValueAsString(json);
			}
			
		}
		return jsonStr;
	}
	
	/*
	 * 检查登录状态，token
	 */
	@RequestMapping(value="/checkLogin", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String checkLogin(String token) throws Exception{
		Token tokenPo = tokenService.getTokenByToken(token);
		String jsonStr = "";
		if(tokenPo != null){
			//查询到了存在该token
			//查询对应的用户
			User user = userService.queryUserById(tokenPo.getUserId());
			//用户已经登录
			BaseJson json = new BaseJson(0, "用户已经登录", user);
			jsonStr = new ObjectMapper().writeValueAsString(json);
		}else{
			BaseJson json = new BaseJson(1, "暂未登录或登录已过期", null);
			jsonStr = new ObjectMapper().writeValueAsString(json);
		}
		return jsonStr;
	}
	
	
	/*
	 * 修改昵称
	 */
	@RequestMapping(value="/updateNickname", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateNickname(int user_id, String nickname) throws Exception{
		User user = new User();
		user.setId(user_id);
		user.setNickname(nickname);
		BaseJson baseJson = null;
		if(userService.updateUserSelective(user)){
			baseJson = new BaseJson(0, "修改成功", nickname);
		}else{
			baseJson = new BaseJson(1, "修改失败", nickname);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	
	/*
	 * 修改密码
	 */
	@RequestMapping(value="/updatePassword", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updatePassword(int user_id, String password) throws Exception{
		User user = new User();
		user.setId(user_id);
		user.setPassword(password);
		BaseJson baseJson = null;
		if(userService.updateUserSelective(user)){
			baseJson = new BaseJson(0, "修改成功", password);
		}else{
			baseJson = new BaseJson(1, "修改失败", password);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	/*
	 * 修改地址
	 */
	@RequestMapping(value="/updateAddress", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateAddress(int user_id, String address) throws Exception{
		User user = new User();
		user.setId(user_id);
		user.setAddress(address);
		BaseJson baseJson = null;
		if(userService.updateUserSelective(user)){
			baseJson = new BaseJson(0, "修改成功", address);
		}else{
			baseJson = new BaseJson(1, "修改失败", address);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	/*
	 * 删除用户
	 */
	@RequestMapping(value="/deleteUserById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deleteUserById(int id) throws Exception{
		BaseJson baseJson = null;
		if(userService.deleteUserById(id)){
			baseJson = new BaseJson(0, "删除成功", null);
		}else{
			baseJson = new BaseJson(1, "删除失败", null);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	/*
	 * 判断用户手机是否已经有人注册
	 */
	@RequestMapping(value="/checkPhoneNumber", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String checkPhoneNumber(String phoneNumber) throws Exception{
		BaseJson baseJson = null;
		User user = userService.queryUserByPhone(phoneNumber);
		if(user != null){
			//该手机号已经被人注册
			baseJson = new BaseJson(1, "该手机号已经被人注册", null);
		}else{
			baseJson = new BaseJson(0, "该手机号可以正常使用", null);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	/*
	 * 修改用户手机号
	 */
	@RequestMapping(value="/updatePhoneNumber", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updatePhoneNumber(int id, String phoneNumber, String codeStr, HttpSession session) throws Exception{
		BaseJson baseJson = null;
		System.out.println(id+" "+phoneNumber+" "+codeStr);
		//先判断短信验证码是否正确
		if(session.getAttribute("code") != null){
			String trueCode = (String) session.getAttribute("code");
			if(codeStr.equals(trueCode)){
				//验证码正确，可以进行修改操作
				User user = new User();
				user.setId(id);
				user.setPhoneNumber(phoneNumber);
				if(userService.updateUserSelective(user)){
					baseJson = new BaseJson(0, "修改成功", null);
					//修改成功后清除保存在session里面的验证码数据
					session.setAttribute("code", null);
				}else{
					baseJson = new BaseJson(3, "修改失败", null);
				}
			}else{
				baseJson = new BaseJson(2, "验证码不正确", null);
			}
		}else{
			baseJson = new BaseJson(1, "请先获取验证码", null);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	
}
