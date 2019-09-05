package cn.qinwh.wry.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qinwh.wry.Vo.HOReviewVo;
import cn.qinwh.wry.mapper.HOReviewMapper;
import cn.qinwh.wry.mapper.HOrderMapper;
import cn.qinwh.wry.mapper.UserMapper;
import cn.qinwh.wry.po.HOReview;
import cn.qinwh.wry.po.HOrder;
import cn.qinwh.wry.po.User;
import cn.qinwh.wry.service.HouseworkOrderReviewService;

@Service
public class HouseworkOrderReviewServiceImpl implements HouseworkOrderReviewService {

	@Autowired
	private HOReviewMapper horMapper;
	@Autowired
	private UserMapper uMapper;
	@Autowired
	private HOrderMapper hoMapper;
	
	
	@Override
	public HOReviewVo queryReviewById(int id) throws Exception {
		HOReview review = horMapper.selectByPrimaryKey(id);
		if(review == null){
			return null;
		}
		int user_id = review.getUserId();
		User user = uMapper.selectByPrimaryKey(user_id);
		int order_id = review.getOrderId();
		HOrder order = hoMapper.selectByPrimaryKey(order_id);
		HOReviewVo reviewVo = new HOReviewVo();
		reviewVo.setReview(review);
		reviewVo.setUser(user);
		reviewVo.setOrder(order);
		return reviewVo;
	}

	@Override
	public HOReviewVo queryReviewByOrderId(int order_id) throws Exception {
		HOReview review = horMapper.selectByOrderId(order_id);
		if(review == null){
			return null;
		}
		int user_id = review.getUserId();
		User user = uMapper.selectByPrimaryKey(user_id);
		HOrder order = hoMapper.selectByPrimaryKey(order_id);
		HOReviewVo reviewVo = new HOReviewVo();
		reviewVo.setReview(review);
		reviewVo.setUser(user);
		reviewVo.setOrder(order);
		return reviewVo;
	}

	@Override
	public boolean addReview(HOReview review) throws Exception {
		review.setPublishTime(new Date());
		int count = horMapper.insertSelective(review);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public List<HOReviewVo> queryAllReview() throws Exception {
		List<HOReviewVo> reviewVos = new ArrayList<>();
		List<HOReview> reviews = horMapper.selectAll();
		for(HOReview hor:reviews){
			User user = uMapper.selectByPrimaryKey(hor.getUserId());
			HOrder order = hoMapper.selectByPrimaryKey(hor.getOrderId());
			HOReviewVo reviewVo = new HOReviewVo();
			reviewVo.setReview(hor);
			reviewVo.setUser(user);
			reviewVo.setOrder(order);
			reviewVos.add(reviewVo);
		}
		return reviewVos;
	}

	@Override
	public boolean deleteReviewById(int id) throws Exception {
		int count = horMapper.deleteByPrimaryKey(id);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public List<HOReviewVo> queryByUserId(int user_id) throws Exception {
		List<HOReviewVo> reviewVos = new ArrayList<>();
		List<HOReview> reviews = horMapper.selectByUserId(user_id);
		for(HOReview hor:reviews){
			User user = uMapper.selectByPrimaryKey(hor.getUserId());
			HOrder order = hoMapper.selectByPrimaryKey(hor.getOrderId());
			HOReviewVo reviewVo = new HOReviewVo();
			reviewVo.setReview(hor);
			reviewVo.setUser(user);
			reviewVo.setOrder(order);
			reviewVos.add(reviewVo);
		}
		return reviewVos;
	}

	@Override
	public List<HOReviewVo> queryByKeyword(String keyword) throws Exception {
		List<HOReviewVo> reviewVos = new ArrayList<>();
		List<HOReview> reviews = horMapper.selectByKeyword(keyword);
		for(HOReview hor:reviews){
			User user = uMapper.selectByPrimaryKey(hor.getUserId());
			HOrder order = hoMapper.selectByPrimaryKey(hor.getOrderId());
			HOReviewVo reviewVo = new HOReviewVo();
			reviewVo.setReview(hor);
			reviewVo.setUser(user);
			reviewVo.setOrder(order);
			reviewVos.add(reviewVo);
		}
		return reviewVos;
	}
}
