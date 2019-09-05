package cn.qinwh.wry.service;

import java.util.List;

import cn.qinwh.wry.Vo.HOReviewVo;
import cn.qinwh.wry.po.HOReview;

public interface HouseworkOrderReviewService {
	//通过id查询某个评论
	public HOReviewVo queryReviewById(int id) throws Exception;
	
	//通过订单id查询某个评论
	public HOReviewVo queryReviewByOrderId(int order_id) throws Exception;
	
	//增加评论
	public boolean addReview(HOReview review) throws Exception;
	
	//查询所有的评论
	public List<HOReviewVo> queryAllReview() throws Exception;
	
	//删除某个评论
	public boolean deleteReviewById(int id) throws Exception;
	
	//查询某用户的所有的订单的评论
	public List<HOReviewVo> queryByUserId(int user_id) throws Exception;
	
	public List<HOReviewVo> queryByKeyword(String keyword) throws Exception;
}
