package cn.qinwh.wry.service;

import java.util.List;

import cn.qinwh.wry.Vo.NoticeVo;
import cn.qinwh.wry.po.Notice;

public interface NoticeService {

	public List<Notice> queryAllNotice() throws Exception;
	
	public List<NoticeVo> queryAllNoticeVo() throws Exception;
	
	public List<NoticeVo> queryNoticeByUserId(int user_id) throws Exception;
	
	public NoticeVo queryNoticeById(int id) throws Exception;
	
	public List<NoticeVo> queryNoticeByKeyword(String keyword) throws Exception;
	
	public boolean saveNotice(Notice notice) throws Exception;
	
	public boolean updateNotice(Notice notice) throws Exception;
	
	public boolean deleteNoticeById(int id) throws Exception;

	public boolean addNotice(Notice notice) throws Exception;
}
