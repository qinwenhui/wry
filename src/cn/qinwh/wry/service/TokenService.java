package cn.qinwh.wry.service;

import cn.qinwh.wry.po.Token;

public interface TokenService {

	public Token getTokenById(int id) throws Exception;
	
	public Token getTokenByToken(String token) throws Exception;
	
	public int countTokenByUserId(int user_id) throws Exception;
	
	public int countToken() throws Exception;
	
	public void updateStateById(int id) throws Exception;
	
	public void updateStateByToken(String token) throws Exception;
	
	public boolean saveToken(Token token) throws Exception;
	
}
