package kr.co.farmstory.service;

import kr.co.farmstory.repository.UserRepo;
import kr.co.farmstory.vo.ArticleVO;
import kr.co.farmstory.vo.TermsVO;
import kr.co.farmstory.dao.UserDAO;
import kr.co.farmstory.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public interface EmailService{
        String sendSimpleMessage(String to)throws Exception;
    }
}
