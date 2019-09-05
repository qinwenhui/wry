package cn.qinwh.wry.Vo;

import cn.qinwh.wry.po.Admin;
import cn.qinwh.wry.po.Notice;
import cn.qinwh.wry.po.User;

public class NoticeVo extends Notice{

	private User user;
	private Admin admin;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public void setNotice(Notice notice) {
		this.setId(notice.getId());
		this.setUserId(notice.getUserId());
		this.setAdminId(notice.getAdminId());
		this.setPublishTime(notice.getPublishTime());
		this.setTitle(notice.getTitle());
		this.setContent(notice.getContent());
		this.setState(notice.getState());
	}
	
}
