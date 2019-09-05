package cn.qinwh.wry.service;

import java.util.List;

import cn.qinwh.wry.Vo.TOReviewVo;
import cn.qinwh.wry.po.TOReview;

public interface TrustOrderReviewService {

	//通过id查询某个评论
	public TOReviewVo queryReviewById(int id) throws Exception;
	
	//通过订单id查询某个评论
	public TOReviewVo queryReviewByOrderId(int order_id) throws Exception;
	
	//增加评论
	public boolean addReview(TOReview review) throws Exception;
	
	//查询所有的评论
	public List<TOReviewVo> queryAllReview() throws Exception;
	
	//删除某个评论
	public boolean deleteReviewById(int id) throws Exception;
	
	//查询某用户的所有的订单的评论
	public List<TOReviewVo> queryByUserId(int user_id) throws Exception;

	public List<TOReviewVo> queryByKeyword(String keyword) throws Exception;
}
