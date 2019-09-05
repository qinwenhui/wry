package cn.qinwh.wry.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.qinwh.wry.Vo.TrustOrderVo;
import cn.qinwh.wry.po.TrustItem;
import cn.qinwh.wry.po.TrustOrder;
import cn.qinwh.wry.po.User;
import cn.qinwh.wry.po.Waiter;
import cn.qinwh.wry.service.TrustItemService;
import cn.qinwh.wry.service.TrustOrderService;
import cn.qinwh.wry.service.UserService;
import cn.qinwh.wry.service.WaiterService;
import cn.qinwh.wry.utils.BaseJson;

@Controller
@RequestMapping("api/trustOrder")
public class TrustOrderController {

	@Autowired
	private TrustOrderService service;
	@Autowired
	private TrustItemService iService;
	@Autowired
	private WaiterService wService;
	@Autowired
	private UserService uService;
	
	/*
	 * 查询所有托管订单
	 */
	@RequestMapping(value="/queryAllTrustOrder",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryAllTrustOrder() throws Exception{
		BaseJson baseJsn = null;
		List<TrustOrder> orders = service.queryAll();
		if(orders.size() == 0){
			//暂无数据
			baseJsn = new BaseJson(1,"暂无数据",null);
		}else{
			baseJsn = new BaseJson(0,"共有"+orders.size()+"条数据",orders);
		}
		String json = new ObjectMapper().writeValueAsString(baseJsn);
		return json;
	}
	
	/*
	 * 通过服务员查询订单
	 */
	@RequestMapping(value="/queryTrustOrderByWaiter",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryTrustOrderByWaiter(int waiter_id) throws Exception{
		BaseJson baseJsn = null;
		List<TrustOrder> orders = service.queryByWaiterId(waiter_id);
		if(orders.size() == 0){
			//暂无数据
			baseJsn = new BaseJson(1,"暂无数据",null);
		}else{
			baseJsn = new BaseJson(0,"共有"+orders.size()+"条数据",orders);
		}
		String json = new ObjectMapper().writeValueAsString(baseJsn);
		return json;
	}
	/*
	 * 通过物品id查询订单
	 */
	@RequestMapping(value="/queryTrustOrderByItem",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryTrustOrderByItem(int item_id) throws Exception{
		BaseJson baseJsn = null;
		TrustOrder order = service.queryByItemId(item_id);
		if(order == null){
			//暂无数据
			baseJsn = new BaseJson(1,"暂无数据",null);
		}else{
			baseJsn = new BaseJson(0,"查询成功",order);
		}
		String json = new ObjectMapper().writeValueAsString(baseJsn);
		return json;
	}
	/*
	 * 通过用户id查询订单
	 */
	@RequestMapping(value="/queryTrustOrderByUser",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryTrustOrderByUser(int user_id) throws Exception{
		BaseJson baseJsn = null;
		List<TrustOrder> orders = service.queryByUserId(user_id);
		if(orders.size() == 0){
			//暂无数据
			baseJsn = new BaseJson(1,"暂无数据",null);
		}else{
			baseJsn = new BaseJson(0,"共有"+orders.size()+"条数据",orders);
		}
		String json = new ObjectMapper().writeValueAsString(baseJsn);
		return json;
	}
	/*
	 * 通过状态查询订单
	 */
	@RequestMapping(value="/queryTrustOrderByState",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryTrustOrderByState(int state) throws Exception{
		BaseJson baseJsn = null;
		List<TrustOrder> orders = service.queryByState(state);
		if(orders.size() == 0){
			//暂无数据
			baseJsn = new BaseJson(1,"暂无数据",null);
		}else{
			baseJsn = new BaseJson(0,"共有"+orders.size()+"条数据",orders);
		}
		String json = new ObjectMapper().writeValueAsString(baseJsn);
		return json;
	}
	/*
	 * 通过状态查询订单VO
	 */
	@RequestMapping(value="/queryTrustOrderVoByState",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryTrustOrderVoByState(int state) throws Exception{
		BaseJson baseJsn = null;
		List<TrustOrder> orders = service.queryByState(state);
		List<TrustOrderVo> orderVos = new ArrayList<>();
		for(int i=0;i<orders.size();i++){
			TrustOrder order = orders.get(i);
			//查询关联的物品以及用户和服务员信息
			TrustItem item = iService.queryById(order.getItemId());
			User user = uService.queryUserById(item.getUserId());
			Waiter waiter = wService.queryWaiterById(order.getWaiterId());
			TrustOrderVo vo = new TrustOrderVo();
			vo.setItem(item);
			vo.setTrustOrder(order);
			vo.setUser(user);
			vo.setWaiter(waiter);
			orderVos.add(vo);
		}
		if(orderVos.size() == 0){
			//暂无数据
			baseJsn = new BaseJson(1,"暂无数据",null);
		}else{
			baseJsn = new BaseJson(0,"共有"+orderVos.size()+"条数据",orderVos);
			
		}
		String json = new ObjectMapper().writeValueAsString(baseJsn);
		return json;
	}
	/*
	 * 通过状态和物品id查询订单
	 */
	@RequestMapping(value="/queryTrustOrderByStateAndItemId",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryTrustOrderByStateAndItemId(int state, int item_id) throws Exception{
		BaseJson baseJsn = null;
		List<TrustOrder> orders = service.queryByStateAndItemId(state, item_id);
		if(orders.size() == 0){
			//暂无数据
			baseJsn = new BaseJson(1,"暂无数据",null);
		}else{
			baseJsn = new BaseJson(0,"共有"+orders.size()+"条数据",orders);
		}
		String json = new ObjectMapper().writeValueAsString(baseJsn);
		return json;
	}
	
	@RequestMapping(value="/addTrustOrder", produces="application/json; charset=utf-8")
	@ResponseBody
	public String addTrustOrder(TrustOrder order) throws Exception{
		order.setTransactionTime(new Date());
		order.setSatisfaction(100);
		order.setState(2);
		BaseJson baseJson = null;
		if(service.addTrustOrder(order)){
			//增加用户的托管次数
			int user_id = iService.queryById(order.getItemId()).getUserId();
			User user = uService.queryUserById(user_id);
			user.setTrustNumber(user.getTrustNumber()+1);
			uService.updateUserSelective(user);
			//增加服务员的服务次数
			Waiter waiter = wService.queryWaiterById(order.getWaiterId());
			waiter.setId(order.getWaiterId());
			waiter.setServiceNumber(waiter.getServiceNumber()+1);
			wService.updateWaiterSelective(waiter);
			baseJson = new BaseJson(0,"添加成功",order);
		}else{
			baseJson = new BaseJson(1,"添加失败",order);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	@RequestMapping(value="/updateTrustOrder", produces="application/json; charset=utf-8")
	@ResponseBody
	public String updateTrustOrder(TrustOrder order) throws Exception{
		BaseJson baseJson = null;
		if(service.updateTrustOrder(order)){
			baseJson = new BaseJson(0,"修改成功",order);
		}else{
			baseJson = new BaseJson(1,"修改失败",order);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	@RequestMapping(value="/deleteTrustOrder", produces="application/json; charset=utf-8")
	@ResponseBody
	public String deleteTrustOrder(int id) throws Exception{
		BaseJson baseJson = null;
		//减少用户的托管次数
		TrustOrder order = service.queryTrustOrderById(id);
		int user_id = iService.queryById(order.getItemId()).getUserId();
		User user = uService.queryUserById(user_id);
		user.setTrustNumber(user.getTrustNumber()-1);
		//减少服务员的服务次数
		Waiter waiter = wService.queryWaiterById(order.getWaiterId());
		waiter.setId(order.getWaiterId());
		waiter.setServiceNumber(waiter.getServiceNumber()-1);
		if(uService.updateUserSelective(user) && wService.updateWaiterSelective(waiter)){
			if(service.deleteById(id)){
				baseJson = new BaseJson(0,"删除成功",id);
			}else{
				baseJson = new BaseJson(1,"删除失败",id);
			}
		}else{
			baseJson = new BaseJson(1,"删除失败",id);
		}
		
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	/*
	 * 查询所有包括该订单的物品，服务员信息(VO)
	 */
	@RequestMapping(value="/queryAllTrustOrderVo",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryAllTrustOrderVo() throws Exception{
		BaseJson baseJsn = null;
		List<TrustOrder> orders = service.queryAll();
		List<TrustOrderVo> orderVos = new ArrayList<>();
		for(int i=0;i<orders.size();i++){
			TrustOrder order = orders.get(i);
			//查询关联的物品以及用户和服务员信息
			TrustItem item = iService.queryById(order.getItemId());
			User user = uService.queryUserById(item.getUserId());
			Waiter waiter = wService.queryWaiterById(order.getWaiterId());
			TrustOrderVo vo = new TrustOrderVo();
			vo.setItem(item);
			vo.setTrustOrder(order);
			vo.setUser(user);
			vo.setWaiter(waiter);
			orderVos.add(vo);
		}
		if(orderVos.size() == 0){
			//暂无数据
			baseJsn = new BaseJson(1,"暂无数据",null);
		}else{
			baseJsn = new BaseJson(0,"共有"+orderVos.size()+"条数据",orderVos);
			
		}
		String json = new ObjectMapper().writeValueAsString(baseJsn);
		return json;
	}
	
	/*
	 * 通过用户id查询订单(VO)
	 */
	@RequestMapping(value="/queryTrustOrderVoByUser",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryTrustOrderVoByUser(int user_id) throws Exception{
		BaseJson baseJsn = null;
		List<TrustOrder> orders = service.queryByUserId(user_id);
		List<TrustOrderVo> orderVos = new ArrayList<>();
		for(int i=0;i<orders.size();i++){
			TrustOrder order = orders.get(i);
			//查询关联的物品以及用户和服务员信息
			System.err.println(order.getItemId());
			TrustItem item = iService.queryById(order.getItemId());
			User user = uService.queryUserById(item.getUserId());
			Waiter waiter = wService.queryWaiterById(order.getWaiterId());
			TrustOrderVo vo = new TrustOrderVo();
			vo.setItem(item);
			vo.setTrustOrder(order);
			vo.setUser(user);
			vo.setWaiter(waiter);
			orderVos.add(vo);
		}
		if(orderVos.size() == 0){
			//暂无数据
			baseJsn = new BaseJson(1,"暂无数据",null);
		}else{
			baseJsn = new BaseJson(0,"共有"+orderVos.size()+"条数据",orderVos);
		}
		String json = new ObjectMapper().writeValueAsString(baseJsn);
		return json;
	}
	
	/*
	 * 通过订单id查询出某个订单VO
	 */
	@RequestMapping(value="/queryTrustOrderVoById",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryTrustOrderVoById(int id) throws Exception{
		BaseJson baseJsn = null;
		TrustOrder order = service.queryTrustOrderById(id);
		if(order != null){
			TrustOrderVo orderVo = new TrustOrderVo();
			//查询关联的物品以及用户和服务员信息
			TrustItem item = iService.queryById(order.getItemId());
			User user = uService.queryUserById(item.getUserId());
			Waiter waiter = wService.queryWaiterById(order.getWaiterId());
			orderVo.setItem(item);
			orderVo.setTrustOrder(order);
			orderVo.setUser(user);
			orderVo.setWaiter(waiter);
			baseJsn = new BaseJson(0,"查询成功",orderVo);
		}else{
			//暂无数据
			baseJsn = new BaseJson(1,"暂无数据",null);
		}
		String json = new ObjectMapper().writeValueAsString(baseJsn);
		return json;
	}
	
	/*
	 * 通过订单金额查找VO
	 */
	@RequestMapping(value="/queryTrustOrderVoByPrice",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryTrustOrderVoByPrice(double minPrice, double maxPrice) throws Exception{
		BaseJson baseJsn = null;
		List<TrustOrder> orders = service.queryByPrice(minPrice, maxPrice);
		List<TrustOrderVo> orderVos = new ArrayList<>();
		for(int i=0;i<orders.size();i++){
			TrustOrder order = orders.get(i);
			//查询关联的物品以及用户和服务员信息
			System.err.println(order.getItemId());
			TrustItem item = iService.queryById(order.getItemId());
			User user = uService.queryUserById(item.getUserId());
			Waiter waiter = wService.queryWaiterById(order.getWaiterId());
			TrustOrderVo vo = new TrustOrderVo();
			vo.setItem(item);
			vo.setTrustOrder(order);
			vo.setUser(user);
			vo.setWaiter(waiter);
			orderVos.add(vo);
		}
		if(orderVos.size() == 0){
			//暂无数据
			baseJsn = new BaseJson(1,"暂无数据",null);
		}else{
			baseJsn = new BaseJson(0,"共有"+orderVos.size()+"条数据",orderVos);
		}
		String json = new ObjectMapper().writeValueAsString(baseJsn);
		return json;
	}
}
