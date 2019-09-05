package cn.qinwh.wry.Vo;

import cn.qinwh.wry.po.User;

/*
 * 展示类(UserCustom扩展类)
 */
public class UserVo extends User{

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public void setUser(User user){
		this.setAddress(user.getAddress());
		this.setCredit(user.getCredit());
		this.setIcon(user.getIcon());
		this.setId(user.getId());
		this.setNickname(user.getNickname());
		this.setPassword(user.getPassword());
		this.setPhoneNumber(user.getPhoneNumber());
		this.setRegisterTime(user.getRegisterTime());
		this.setServiceNumber(user.getServiceNumber());
		this.setState(user.getState());
		this.setTrustNumber(user.getTrustNumber());
		this.setUsername(user.getUsername());
		this.setSeat(user.getSeat());
	}
}
