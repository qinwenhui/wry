package cn.qinwh.wry.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.qinwh.wry.utils.BaseJson;
import cn.qinwh.wry.utils.ImageUtil;

@Controller
@RequestMapping("/other")
public class OtherController {

	/*
	 * 图片验证码的接口
	 */
    @RequestMapping("/imageCode")
    public void imageCode(HttpServletResponse response,HttpSession session) throws Exception{
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = ImageUtil.createImage();
        //将验证码存入Session
        session.setAttribute("imageCode",objs[0]);
        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
        
    }
	
	
	/*
	 * 判断图片验证码
	 */
    @RequestMapping(value="/checkCode", produces="application/json; charset=utf-8")
	@ResponseBody
	public String checkCode(@RequestParam String code, HttpSession session) throws Exception{
		BaseJson baseJson = null;
		if(session.getAttribute("imageCode") != null){
			if(code.equalsIgnoreCase((String) session.getAttribute("imageCode"))){
				//验证码正确
				baseJson = new BaseJson(0, "验证码正确", null);
			}else{
				//验证码错误
				baseJson = new BaseJson(1, "验证码错误", null);
			}
		}else{
			baseJson = new BaseJson(1, "验证码错误", null);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
    
    
    /*
	 * 短信验证码的接口
	 */
    @RequestMapping(value="/msgCode", produces="application/json; charset=utf-8")
    @ResponseBody
    public String msgCode(String phoneNumber, HttpSession session) throws JsonProcessingException{
    	BaseJson baseJson = null;
        //生成一个随机四位数(1000-9999)
    	String code = (new Random().nextInt(8999)+1000)+"";
    	//保存进session
    	session.setAttribute("code", code);
    	//发送短信到手机用户（使用第三方接口）
    	String url = "http://106.ihuyi.com/webservice/sms.php?method=Submit";
    	String apiid = "C23194924";
    	String apikey = "4dff9e89eb84911d687a3aa49f9e4fc1";
    	String content = new String("您的验证码是：" + code + "。请不要把验证码泄露给其他人。");
    	//拼接参数
    	String postData = "account="+apiid+"&password="+apikey+"&mobile="+phoneNumber+"&content="+content+"&format=json";
    	byte[] data = postData.getBytes();
    	//返回的字符串
    	String result = "";
    	try{
    		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        	conn.setRequestMethod("POST");
        	conn.setDoOutput(true);
        	conn.setDoInput(true);
        	conn.setUseCaches(false);
        	conn.getOutputStream().write(data);
        	BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        	String line = "";
        	while((line=br.readLine()) != null){
        		result += line;
        	}
        	if(result.equals("")){
        		baseJson = new BaseJson(1, "获取验证码失败", null);
        	}else{
        		//解析返回的json数据
        		JsonNode jsonNode = new ObjectMapper().readTree(result);
        		int resultCode = jsonNode.path("code").asInt();
        		String msg = jsonNode.path("msg").asText();
        		if(resultCode == 2){
        			//发送验证码成功
        			baseJson = new BaseJson(0, "发送验证码成功", msg);
        		}else{
        			//发送验证码失败
        			baseJson = new BaseJson(3, "发送验证码失败", msg);
        		}
        	}
    	}catch(Exception e){
    		baseJson = new BaseJson(2, "请求验证码失败:"+e.getMessage(), null);
    	}
    	String json = new ObjectMapper().writeValueAsString(baseJson);
    	return json;
    }
    
    /*
	 * 验证
	 */
    @RequestMapping(value="/checkSession", produces="application/json; charset=utf-8")
	@ResponseBody
	public String checkSession(HttpSession session) throws Exception{
		if(session.getAttribute("yanzheng") == null){
			session.setAttribute("yanzheng", "a");
			return "不存在session,no";
		}else{
			return "存在session,yes";
		}
	}
}
