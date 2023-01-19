package kr.co.sboard.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.sboard.dao.ArticleDAO;
import kr.co.sboard.repository.ArticleRepo;
import kr.co.sboard.vo.ArticleVO;
import kr.co.sboard.vo.FileVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArticleService {

	@Autowired
	private ArticleDAO dao;
	
	private ArticleRepo repo;
	
	@Transactional
	public int insertArticle(ArticleVO vo) {
		
		// 글 등록
		int result = dao.insertArticle(vo);
		
		// 파일 업로드
		FileVO fvo = fileUpload(vo);
		
		if(fvo != null) {
			// 파일 등록
			dao.insertFile(fvo);
		}
		
		return result;
	}
	
	public ArticleVO selectArticle(int no) {
		return dao.selectArticle(no);
	}
	
	public List<ArticleVO> selectArticles(int start) {
		return dao.selectArticles(start);
	}
	
	public int updateArticle(ArticleVO vo) {
		return dao.updateArticle(vo);
	}
	
	public int deleteArticle(int no) {
		return dao.deleteArticle(no);
	}
	
	@Value("${spring.servlet.multipart.location}")
	private String uploadPath;
	
	public FileVO fileUpload(ArticleVO vo) {
		// 첨부 파일
		MultipartFile file = vo.getFname();
		FileVO fvo = null;
		
		if(!file.isEmpty()) {
			// 시스템 경로
			String path = new File(uploadPath).getAbsolutePath();
			log.info("path : " + path);
			
			// 새 파일명 생성
			String oName = file.getOriginalFilename();
			String ext = oName.substring(oName.lastIndexOf("."));
			String nName = UUID.randomUUID().toString()+ext;
			
			// 파일 저장
			try {
				file.transferTo(new File(path, nName));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			fvo = FileVO.builder()
					.parent(vo.getNo())
					.OriName(oName)
					.newName(nName)
					.build();
		}
		return fvo;
	}
	
	// 페이지 시작값
	public int getLimitStart(int currentPage) {
		return (currentPage - 1) * 10;
	}
	
	public int getCurrentpage(String pg) {
		int currentPage = 1;
		
		if(pg != null) {
			currentPage = Integer.parseInt(pg);
		}
		return currentPage;
	}
	
	// 전체 게시물 갯수
	public int getTotalCount() {
		return dao.selectCountTotal();
	}
	
	// 마지막 페이지 번호
	public int getLastPageNum(int total) {
		
		int lastPage = 0;
		
		if(total % 10 == 0) {
			lastPage = total / 10;
		}else {
			lastPage = total / 10 + 1;
		}
		return lastPage;
	}
	
	// 페이지 시작 번호
	public int getPageStartNum(int total, int start) {
		return total - start;
	}
	
	// 페이지 그룹
	public int[] getPageGroup(int currentPage, int lastPage) {
		
		int groupCurrent = (int) Math.ceil(lastPage / 10.0);
		int groupStart = (groupCurrent - 1) * 10 + 1;
		int groupEnd = groupCurrent * 10;
		
		if(groupEnd > lastPage) {
			groupEnd = lastPage;
		}
		int[] groups = {groupStart, groupEnd};
		return groups;
	}
	
}
