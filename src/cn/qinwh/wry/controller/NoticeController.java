package cn.qinwh.wry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.qinwh.wry.Vo.NoticeVo;
import cn.qinwh.wry.po.Notice;
import cn.qinwh.wry.service.NoticeService;
import cn.qinwh.wry.utils.BaseJson;

@Controller
@RequestMapping("/api/notice")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	/*
	 * 查询所有通知
	 */
	@RequestMapping(value="/queryNoticeVo", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryNoticeVo() throws Exception{
		List<NoticeVo> noticeVos = noticeService.queryAllNoticeVo();
		BaseJson json = null;
		if(noticeVos.size() > 0){
			//有数据
			json = new BaseJson(0, "查询成功", noticeVos);
		}else{
			json = new BaseJson(1, "暂无数据", null);
		}
		String jsonStr = new ObjectMapper().writeValueAsString(json);
		return jsonStr;
		
	}
	
	/*
	 * 按关键词查询所有通知
	 */
	@RequestMapping(value="/queryNoticeVoByKeyword", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryNoticeVoByKeyword(String keyword) throws Exception{
		List<NoticeVo> noticeVos = noticeService.queryNoticeByKeyword(keyword);
		BaseJson json = null;
		if(noticeVos.size() > 0){
			//有数据
			json = new BaseJson(0, "查询成功", noticeVos);
		}else{
			json = new BaseJson(1, "暂无数据", null);
		}
		String jsonStr = new ObjectMapper().writeValueAsString(json);
		return jsonStr;
		
	}
	
	@RequestMapping(value="/queryNoticeByUserId", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryNoticeByUserId(int user_id) throws Exception{
		List<NoticeVo> noticeVos = noticeService.queryNoticeByUserId(user_id);
		BaseJson json = null;
		if(noticeVos.size() > 0){
			//有数据
			json = new BaseJson(0, "查询成功", noticeVos);
		}else{
			json = new BaseJson(1, "暂无数据", null);
		}
		String jsonStr = new ObjectMapper().writeValueAsString(json);
		return jsonStr;
		
	}
	
	/*
	 * 通过id获取通知信息
	 */
	@RequestMapping(value="/queryNoticeById", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryNoticeById(int id) throws Exception{
		NoticeVo noticeVo = noticeService.queryNoticeById(id);
		BaseJson json = null;
		if(noticeVo != null){
			//有数据
			json = new BaseJson(0, "查询成功", noticeVo);
		}else{
			json = new BaseJson(1, "暂无数据", null);
		}
		String jsonStr = new ObjectMapper().writeValueAsString(json);
		return jsonStr;
		
	}
	
	@RequestMapping(value="/updateStateById", produces="application/json; charset=utf-8")
	@ResponseBody
	public String updateStateById(int id, int state) throws Exception{
		Notice notice = new Notice();
		notice.setId(id);
		notice.setState(state);
		BaseJson json = null;
		if(noticeService.updateNotice(notice)){
			json = new BaseJson(0, "修改成功", null);
		}else{
			json = new BaseJson(1, "修改失败", null);
		}
		String jsonStr = new ObjectMapper().writeValueAsString(json);
		return jsonStr;
		
	}
	
	/*
	 * 更新
	 */
	@RequestMapping(value="/updateNotice", produces="application/json; charset=utf-8")
	@ResponseBody
	public String updateStateById(Notice notice) throws Exception{
		BaseJson json = null;
		if(noticeService.updateNotice(notice)){
			json = new BaseJson(0, "修改成功", null);
		}else{
			json = new BaseJson(1, "修改失败", null);
		}
		String jsonStr = new ObjectMapper().writeValueAsString(json);
		return jsonStr;
		
	}
	
	/*
	 * 删除
	 */
	@RequestMapping(value="/deleteNoticeById", produces="application/json; charset=utf-8")
	@ResponseBody
	public String deleteNoticeById(int id) throws Exception{
		BaseJson json = null;
		if(noticeService.deleteNoticeById(id)){
			json = new BaseJson(0, "删除成功", null);
		}else{
			json = new BaseJson(1, "删除失败", null);
		}
		String jsonStr = new ObjectMapper().writeValueAsString(json);
		return jsonStr;
		
	}
	
	/*
	 * 添加通知
	 */
	@RequestMapping(value="/addNotice", produces="application/json; charset=utf-8")
	@ResponseBody
	public String addNotice(Notice notice) throws Exception{
		BaseJson json = null;
		if(noticeService.addNotice(notice)){
			json = new BaseJson(0, "添加成功", null);
		}else{
			json = new BaseJson(1, "添加失败", null);
		}
		String jsonStr = new ObjectMapper().writeValueAsString(json);
		return jsonStr;
		
	}
}
