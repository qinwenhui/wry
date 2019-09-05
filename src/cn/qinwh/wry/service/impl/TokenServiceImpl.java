package cn.qinwh.wry.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qinwh.wry.mapper.TokenMapper;
import cn.qinwh.wry.po.Token;
import cn.qinwh.wry.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private TokenMapper tokenMapper;
	
	@Override
	public Token getTokenById(int id) throws Exception {
		
		return tokenMapper.selectByPrimaryKey(id);
	}

	@Override
	public Token getTokenByToken(String token) throws Exception {
		
		return tokenMapper.selectByToken(token);
	}

	@Override
	public int countTokenByUserId(int user_id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countToken() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateStateById(int id) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateStateByToken(String token) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean saveToken(Token token) throws Exception {
		int count = tokenMapper.insert(token);
		if(count == 1){
			return true;
		}
		return false;
	}

}
