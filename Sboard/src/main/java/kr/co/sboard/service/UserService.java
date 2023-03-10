package kr.co.sboard.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.sboard.dao.UserDAO;
import kr.co.sboard.repository.UserRepo;
import kr.co.sboard.vo.TermsVO;
import kr.co.sboard.vo.UserVO;

@Service
public class UserService {

	@Autowired
	private UserDAO dao;
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public int insertUser(UserVO vo) {
		/*
		// encoder 주입 받아서 생략
		String pass = vo.getPass1();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String cryptedPass = encoder.encode(pass);
		vo.setPass(cryptedPass);
		*/
		vo.setPass(encoder.encode(vo.getPass1()));
		return dao.insertUser(vo);
	}
	
	public UserVO selectUser(String uid) {
		return dao.selectUser(uid);
	}
	
	public List<UserVO> selectUsers() {
		return dao.selectUsers();
	}
	
	public TermsVO selectTerms() {
		return dao.selectTerms();
	}
	
	public int updateUser(UserVO vo) {
		return dao.updateUser(vo);
	}
	
	public int deleteUser(String uid) {
		return dao.deleteUser(uid);
	}
	
	public int countByUid(String uid) {
		int result = repo.countByUid(uid);
		return result;
	}
	
	public int countByNick(String nick) {
		int result = repo.countByNick(nick);
		return result;
	}
}
