package kr.co.ch07.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ch07.vo.User3VO;
import kr.co.ch07.vo.User4VO;

public interface User4Repo extends JpaRepository<User4VO, Integer> {

}
