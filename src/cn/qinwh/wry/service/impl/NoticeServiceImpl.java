package cn.qinwh.wry.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qinwh.wry.Vo.NoticeVo;
import cn.qinwh.wry.mapper.AdminMapper;
import cn.qinwh.wry.mapper.NoticeMapper;
import cn.qinwh.wry.mapper.UserMapper;
import cn.qinwh.wry.po.Admin;
import cn.qinwh.wry.po.Notice;
import cn.qinwh.wry.po.User;
import cn.qinwh.wry.service.NoticeService;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeMapper noticeMapper;
	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private UserMapper userMapper;
	@Override
	public List<Notice> queryAllNotice() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NoticeVo> queryNoticeByUserId(int user_id) throws Exception {
		List<NoticeVo> noticesVos = new ArrayList<NoticeVo>();
		List<Notice> notices = noticeMapper.selectByUserId(user_id);
		for(Notice n : notices){
			//查询用户
			User user = userMapper.selectByPrimaryKey(n.getUserId());
			//查询发送该消息的管理员
			Admin admin = adminMapper.selectByPrimaryKey(n.getAdminId());
			//把所有信息放到NoticeVo展示类里
			NoticeVo noticesVo = new NoticeVo();
			noticesVo.setNotice(n);
			noticesVo.setUser(user);
			noticesVo.setAdmin(admin);
			noticesVos.add(noticesVo);
		}
		return noticesVos;
	}

	@Override
	public boolean saveNotice(Notice notice) throws Exception {
		int count = noticeMapper.insertSelective(notice);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public boolean updateNotice(Notice notice) throws Exception {
		int count = noticeMapper.updateByPrimaryKeySelective(notice);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public List<NoticeVo> queryAllNoticeVo() throws Exception {
		List<NoticeVo> noticesVos = new ArrayList<NoticeVo>();
		List<Notice> notices = noticeMapper.selectAll();
		for(Notice n : notices){
			//查询用户
			User user = userMapper.selectByPrimaryKey(n.getUserId());
			//查询发送该消息的管理员
			Admin admin = adminMapper.selectByPrimaryKey(n.getAdminId());
			//把所有信息放到NoticeVo展示类里
			NoticeVo noticesVo = new NoticeVo();
			noticesVo.setNotice(n);
			noticesVo.setUser(user);
			noticesVo.setAdmin(admin);
			noticesVos.add(noticesVo);
		}
		return noticesVos;
	}

	@Override
	public List<NoticeVo> queryNoticeByKeyword(String keyword) throws Exception {
		List<NoticeVo> noticesVos = new ArrayList<NoticeVo>();
		List<Notice> notices = noticeMapper.selectLikeTitleAndContent(keyword);
		for(Notice n : notices){
			//查询用户
			User user = userMapper.selectByPrimaryKey(n.getUserId());
			//查询发送该消息的管理员
			Admin admin = adminMapper.selectByPrimaryKey(n.getAdminId());
			//把所有信息放到NoticeVo展示类里
			NoticeVo noticesVo = new NoticeVo();
			noticesVo.setNotice(n);
			noticesVo.setUser(user);
			noticesVo.setAdmin(admin);
			noticesVos.add(noticesVo);
		}
		return noticesVos;
	}

	@Override
	public boolean deleteNoticeById(int id) throws Exception {
		int count = noticeMapper.deleteByPrimaryKey(id);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public NoticeVo queryNoticeById(int id) throws Exception {
		Notice n = noticeMapper.selectByPrimaryKey(id);
		//查询用户
		User user = userMapper.selectByPrimaryKey(n.getUserId());
		//查询发送该消息的管理员
		Admin admin = adminMapper.selectByPrimaryKey(n.getAdminId());
		//把所有信息放到NoticeVo展示类里
		NoticeVo noticesVo = new NoticeVo();
		noticesVo.setNotice(n);
		noticesVo.setUser(user);
		noticesVo.setAdmin(admin);
		return noticesVo;
	}

	@Override
	public boolean addNotice(Notice notice) throws Exception {
		notice.setState(0);
		notice.setPublishTime(new Date());
		int count = noticeMapper.insertSelective(notice);
		if(count == 1)
			return true;
		return false;
	}

}
