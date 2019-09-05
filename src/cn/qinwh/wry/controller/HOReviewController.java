package cn.qinwh.wry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.qinwh.wry.Vo.HOReviewVo;
import cn.qinwh.wry.po.HOReview;
import cn.qinwh.wry.service.HouseworkOrderReviewService;
import cn.qinwh.wry.utils.BaseJson;

@RequestMapping("/api/houseworkOrderReview")
@Controller
public class HOReviewController {
	
	@Autowired
	private HouseworkOrderReviewService service;
	
	/*
	 * 
	 * 查询所有评论
	 */
	@RequestMapping(value="/queryAllReview", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryAllReview() throws Exception{
		BaseJson baseJson = null;
		List<HOReviewVo> reviewVos = service.queryAllReview();
		if(reviewVos.size() == 0){
			//没有查到相关数据
			baseJson = new BaseJson(1,"暂无数据",null);
		}else{
			baseJson = new BaseJson(0,"查到"+reviewVos.size()+"条评论数据",reviewVos);
		}
		return new ObjectMapper().writeValueAsString(baseJson);
	}
	
	/*
	 * 通过订单id查询某个评论
	 */
	@RequestMapping(value="/queryReviewByOrderId", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryReviewByOrderId(int order_id) throws Exception{
		BaseJson baseJson = null;
		HOReviewVo reviewVo = service.queryReviewByOrderId(order_id);
		if(reviewVo == null){
			//没有查到相关数据
			baseJson = new BaseJson(1,"暂无数据",null);
		}else{
			baseJson = new BaseJson(0,"查到1条数据",reviewVo);
		}
		return new ObjectMapper().writeValueAsString(baseJson);
	}
	
	/*
	 * 通过评论id查询某个评论
	 */
	@RequestMapping(value="/queryReviewById", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryReviewById(int id) throws Exception{
		BaseJson baseJson = null;
		HOReviewVo reviewVo = service.queryReviewById(id);
		if(reviewVo == null){
			//没有查到相关数据
			baseJson = new BaseJson(1,"暂无数据",null);
		}else{
			baseJson = new BaseJson(0,"查到1条数据",reviewVo);
		}
		return new ObjectMapper().writeValueAsString(baseJson);
	}
	
	/*
	 * 查询某个用户的所有评论
	 */
	@RequestMapping(value="/queryReviewByUserId", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryReviewByUserId(int user_id) throws Exception{
		BaseJson baseJson = null;
		List<HOReviewVo> reviewVos = service.queryByUserId(user_id);
		if(reviewVos.size() == 0){
			//没有查到相关数据
			baseJson = new BaseJson(1,"暂无数据",null);
		}else{
			baseJson = new BaseJson(0,"查到"+reviewVos.size()+"条评论数据",reviewVos);
		}
		return new ObjectMapper().writeValueAsString(baseJson);
	}
	/*
	 * 关键词查询所有评论
	 */
	@RequestMapping(value="/queryReviewByKeyword", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryReviewByKeyword(String keyword) throws Exception{
		BaseJson baseJson = null;
		List<HOReviewVo> reviewVos = service.queryByKeyword(keyword);
		if(reviewVos.size() == 0){
			//没有查到相关数据
			baseJson = new BaseJson(1,"暂无数据",null);
		}else{
			baseJson = new BaseJson(0,"查到"+reviewVos.size()+"条评论数据",reviewVos);
		}
		return new ObjectMapper().writeValueAsString(baseJson);
	}
	
	/*
	 * 增加评论
	 */
	@RequestMapping(value="/addReview", produces="application/json; charset=utf-8")
	@ResponseBody
	public String addReview(HOReview review) throws Exception{
		BaseJson baseJson = null;
		if(service.addReview(review)){
			//添加成功
			baseJson = new BaseJson(0,"添加成功",null);
		}else{
			baseJson = new BaseJson(1,"添加失败",null);
		}
		
		return new ObjectMapper().writeValueAsString(baseJson);
	}
	
	/*
	 * 删除评论
	 */
	@RequestMapping(value="/deleteReview", produces="application/json; charset=utf-8")
	@ResponseBody
	public String deleteReview(int id) throws Exception{
		BaseJson baseJson = null;
		if(service.deleteReviewById(id)){
			//删除成功
			baseJson = new BaseJson(0,"删除成功",null);
		}else{
			baseJson = new BaseJson(1,"删除失败",null);
		}
		
		return new ObjectMapper().writeValueAsString(baseJson);
	}
}
