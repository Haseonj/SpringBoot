package kr.co.sboard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.sboard.dao.UserDAO;
import kr.co.sboard.repository.UserRepo;
import kr.co.sboard.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class SboardApplicationTests {

	
	void contextLoads() {
	}
	
	@Autowired
	private UserDAO dao;
	
	@Autowired
	private UserRepo repo;
	
	public void insertTest() {
		UserVO vo = UserVO.builder()
					.uid("gildong1")
					.pass("1234")
					.name("홍길동")
					.nick("gildong")
					.email("gil1@gmail.com")
					.hp("010-1234-1005")
					.regip("0:0:0:0:0:0:0:1")
					.build();
		
		
		int result = dao.insertUser(vo);
		
		log.info("result :", result);
	}
	
	@Test
	public void countUid() {
		int result = repo.countByUid("gktjswn11");
		
		log.info("result :" + result);
	}

}
