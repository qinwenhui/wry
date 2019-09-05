package cn.qinwh.wry.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qinwh.wry.Vo.TOReviewVo;
import cn.qinwh.wry.mapper.TOReviewMapper;
import cn.qinwh.wry.mapper.TrustOrderMapper;
import cn.qinwh.wry.mapper.UserMapper;
import cn.qinwh.wry.po.TOReview;
import cn.qinwh.wry.po.TrustOrder;
import cn.qinwh.wry.po.User;
import cn.qinwh.wry.service.TrustOrderReviewService;

@Service
public class TrustOrderReviewServiceImpl implements TrustOrderReviewService {

	@Autowired
	private TOReviewMapper torMapper;
	@Autowired
	private UserMapper uMapper;
	@Autowired
	private TrustOrderMapper toMapper;
	
	
	@Override
	public TOReviewVo queryReviewById(int id) throws Exception {
		TOReview review = torMapper.selectByPrimaryKey(id);
		if(review == null){
			return null;
		}
		int user_id = review.getUserId();
		User user = uMapper.selectByPrimaryKey(user_id);
		int order_id = review.getOrderId();
		TrustOrder order = toMapper.selectByPrimaryKey(order_id);
		TOReviewVo reviewVo = new TOReviewVo();
		reviewVo.setReview(review);
		reviewVo.setUser(user);
		reviewVo.setOrder(order);
		return reviewVo;
	}

	@Override
	public TOReviewVo queryReviewByOrderId(int order_id) throws Exception {
		TOReview review = torMapper.selectByOrderId(order_id);
		if(review == null){
			return null;
		}
		int user_id = review.getUserId();
		User user = uMapper.selectByPrimaryKey(user_id);
		TrustOrder order = toMapper.selectByPrimaryKey(order_id);
		TOReviewVo reviewVo = new TOReviewVo();
		reviewVo.setReview(review);
		reviewVo.setUser(user);
		reviewVo.setOrder(order);
		return reviewVo;
	}

	@Override
	public boolean addReview(TOReview review) throws Exception {
		review.setPublishTime(new Date());
		int count = torMapper.insertSelective(review);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public List<TOReviewVo> queryAllReview() throws Exception {
		List<TOReviewVo> reviewVos = new ArrayList<>();
		List<TOReview> reviews = torMapper.selectAll();
		for(TOReview tor:reviews){
			User user = uMapper.selectByPrimaryKey(tor.getUserId());
			TrustOrder order = toMapper.selectByPrimaryKey(tor.getOrderId());
			TOReviewVo reviewVo = new TOReviewVo();
			reviewVo.setReview(tor);
			reviewVo.setUser(user);
			reviewVo.setOrder(order);
			reviewVos.add(reviewVo);
		}
		return reviewVos;
	}

	@Override
	public boolean deleteReviewById(int id) throws Exception {
		int count = torMapper.deleteByPrimaryKey(id);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public List<TOReviewVo> queryByUserId(int user_id) throws Exception {
		List<TOReviewVo> reviewVos = new ArrayList<>();
		List<TOReview> reviews = torMapper.selectByUserId(user_id);
		for(TOReview tor:reviews){
			User user = uMapper.selectByPrimaryKey(tor.getUserId());
			TrustOrder order = toMapper.selectByPrimaryKey(tor.getOrderId());
			TOReviewVo reviewVo = new TOReviewVo();
			reviewVo.setReview(tor);
			reviewVo.setUser(user);
			reviewVo.setOrder(order);
			reviewVos.add(reviewVo);
		}
		return reviewVos;
	}

	@Override
	public List<TOReviewVo> queryByKeyword(String keyword) throws Exception {
		List<TOReviewVo> reviewVos = new ArrayList<>();
		List<TOReview> reviews = torMapper.selectByKeyword(keyword);
		for(TOReview tor:reviews){
			User user = uMapper.selectByPrimaryKey(tor.getUserId());
			TrustOrder order = toMapper.selectByPrimaryKey(tor.getOrderId());
			TOReviewVo reviewVo = new TOReviewVo();
			reviewVo.setReview(tor);
			reviewVo.setUser(user);
			reviewVo.setOrder(order);
			reviewVos.add(reviewVo);
		}
		return reviewVos;
	}

}
