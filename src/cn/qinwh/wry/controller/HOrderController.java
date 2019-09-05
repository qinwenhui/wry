package cn.qinwh.wry.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.qinwh.wry.Vo.HOrderVo;
import cn.qinwh.wry.po.HOrder;
import cn.qinwh.wry.po.User;
import cn.qinwh.wry.po.Waiter;
import cn.qinwh.wry.service.HOrderService;
import cn.qinwh.wry.service.UserService;
import cn.qinwh.wry.service.WaiterService;
import cn.qinwh.wry.utils.BaseJson;

@Controller
@RequestMapping("api/hOrder")
public class HOrderController {

	@Autowired
	private HOrderService service;
	@Autowired
	private WaiterService wService;
	@Autowired
	private UserService uService;
	
	/*
	 * 查询所有家务订单
	 */
	@RequestMapping(value="/queryAllHOrder",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryAllHOrder() throws Exception{
		BaseJson baseJsn = null;
		List<HOrder> orders = service.queryAll();
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
	@RequestMapping(value="/queryHOrderByWaiter",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryHOrderByWaiter(int waiter_id) throws Exception{
		BaseJson baseJsn = null;
		List<HOrder> orders = service.queryByWaiterId(waiter_id);
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
	 * 通过用户id查询订单
	 */
	@RequestMapping(value="/queryHOrderByUser",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryHOrderByUser(int user_id) throws Exception{
		BaseJson baseJsn = null;
		List<HOrder> orders = service.queryByUserId(user_id);
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
	@RequestMapping(value="/queryHOrderByState",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryHOrderByState(int state) throws Exception{
		BaseJson baseJsn = null;
		List<HOrder> orders = service.queryByState(state);
		if(orders.size() == 0){
			//暂无数据
			baseJsn = new BaseJson(1,"暂无数据",null);
		}else{
			baseJsn = new BaseJson(0,"共有"+orders.size()+"条数据",orders);
		}
		String json = new ObjectMapper().writeValueAsString(baseJsn);
		return json;
	}
	
	@RequestMapping(value="/addHOrder", produces="application/json; charset=utf-8")
	@ResponseBody
	public String addHOrder(HOrder order) throws Exception{
		BaseJson baseJson = null;
		if(service.addHOrder(order)){
			User user = uService.queryUserById(order.getUserId());
			user.setServiceNumber(user.getServiceNumber()+1);
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
	
	@RequestMapping(value="/updateHOrder", produces="application/json; charset=utf-8")
	@ResponseBody
	public String updateHOrder(HOrder order) throws Exception{
		BaseJson baseJson = null;
		if(service.updateHOrder(order)){
			baseJson = new BaseJson(0,"修改成功",order);
		}else{
			baseJson = new BaseJson(1,"修改失败",order);
		}
		String json = new ObjectMapper().writeValueAsString(baseJson);
		return json;
	}
	
	@RequestMapping(value="/deleteHOrder", produces="application/json; charset=utf-8")
	@ResponseBody
	public String deleteHOrder(int id) throws Exception{
		BaseJson baseJson = null;
		//先把用户的服务订单数量减1
		HOrder order = service.queryHOrderById(id);
		User user = uService.queryUserById(order.getUserId());
		user.setServiceNumber(user.getServiceNumber()-1);
		//减少服务员的服务次数
		Waiter waiter = wService.queryWaiterById(order.getWaiterId());
		waiter.setId(order.getWaiterId());
		waiter.setServiceNumber(waiter.getServiceNumber()-1);
		if(uService.updateUserSelective(user) && wService.updateWaiterSelective(waiter)){
			//用户订单数量已经减1了，进行删除订单的操作
			if(service.deleteById(id)){
				//删除订单成功
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
	 * 查询所有包括该订单的用户，服务员信息(VO)
	 */
	@RequestMapping(value="/queryAllHOrderVo",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryAllHOrderVo() throws Exception{
		BaseJson baseJsn = null;
		List<HOrder> orders = service.queryAll();
		List<HOrderVo> orderVos = new ArrayList<>();
		for(int i=0;i<orders.size();i++){
			HOrder order = orders.get(i);
			//查询关联的用户和服务员信息
			User user = uService.queryUserById(order.getUserId());
			Waiter waiter = wService.queryWaiterById(order.getWaiterId());
			HOrderVo vo = new HOrderVo();
			vo.setHOrder(order);
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
	@RequestMapping(value="/queryHOrderVoByUser",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryHOrderVoByUser(int user_id) throws Exception{
		BaseJson baseJsn = null;
		List<HOrder> orders = service.queryByUserId(user_id);
		List<HOrderVo> orderVos = new ArrayList<>();
		for(int i=0;i<orders.size();i++){
			HOrder order = orders.get(i);
			//查询关联的用户和服务员信息
			User user = uService.queryUserById(order.getUserId());
			Waiter waiter = wService.queryWaiterById(order.getWaiterId());
			HOrderVo vo = new HOrderVo();
			vo.setHOrder(order);
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
	 * 通过状态查询订单VO
	 */
	@RequestMapping(value="/queryHOrderVoByState",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryHOrderVoByState(int state) throws Exception{
		BaseJson baseJsn = null;
		List<HOrder> orders = service.queryByState(state);
		List<HOrderVo> orderVos = new ArrayList<>();
		for(int i=0;i<orders.size();i++){
			HOrder order = orders.get(i);
			//查询关联的用户和服务员信息
			User user = uService.queryUserById(order.getUserId());
			Waiter waiter = wService.queryWaiterById(order.getWaiterId());
			HOrderVo vo = new HOrderVo();
			vo.setHOrder(order);
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
	 * 通过金额区间查询订单(VO)
	 */
	@RequestMapping(value="/queryHOrderVoByByPrice",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryHOrderVoByByPrice(double minPrice, double maxPrice) throws Exception{
		BaseJson baseJsn = null;
		List<HOrder> orders = service.queryByPrice(minPrice, maxPrice);
		List<HOrderVo> orderVos = new ArrayList<>();
		for(int i=0;i<orders.size();i++){
			HOrder order = orders.get(i);
			//查询关联的用户和服务员信息
			User user = uService.queryUserById(order.getUserId());
			Waiter waiter = wService.queryWaiterById(order.getWaiterId());
			HOrderVo vo = new HOrderVo();
			vo.setHOrder(order);
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
	@RequestMapping(value="/queryHOrderVoById",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryTrustOrderVoById(int id) throws Exception{
		BaseJson baseJsn = null;
		HOrder order = service.queryHOrderById(id);
		if(order != null){
			HOrderVo orderVo = new HOrderVo();
			//查询关联的物品以及用户和服务员信息
			User user = uService.queryUserById(order.getUserId());
			Waiter waiter = wService.queryWaiterById(order.getWaiterId());
			orderVo.setHOrder(order);
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
	
}
