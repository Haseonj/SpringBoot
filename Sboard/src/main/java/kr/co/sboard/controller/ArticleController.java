package kr.co.sboard.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.sboard.entity.UserEntity;
import kr.co.sboard.security.MyUserDetails;
import kr.co.sboard.service.ArticleService;
import kr.co.sboard.vo.ArticleVO;
import kr.co.sboard.vo.FileVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ArticleController {

	@Autowired
	private ArticleService service;
	
	@GetMapping("list")
	public String list(Model model, String pg) {
		
		int currentPage = service.getCurrentpage(pg);
		int start = service.getLimitStart(currentPage);
		int total = service.getTotalCount();
		int lastPage = service.getLastPageNum(total);
		int pageStartNum = service.getPageStartNum(total, start); 
		int groups[] = service.getPageGroup(currentPage, lastPage); 
		
		List<ArticleVO> articles = service.selectArticles(start);
		
		
		model.addAttribute("articles", articles);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("pageStartNum", pageStartNum);
		model.addAttribute("groups", groups);
		
		return "list";
	}
	
	@GetMapping("modify")
	public String modify(@AuthenticationPrincipal MyUserDetails myUser, int no, Model model) {
		UserEntity user = myUser.getUser();
		ArticleVO article = service.selectArticle(no, user);
		model.addAttribute("article", article);
		return "modify";
	}
	
	@PostMapping("modify")
	public String modify(ArticleVO vo) {
		service.updateArticle(vo);
		return "redirect:/view?no=" + vo.getNo();
	}
	
	@GetMapping("view")
	public String view(@AuthenticationPrincipal MyUserDetails myUser, int no, Model model) {
		UserEntity user = myUser.getUser();
		ArticleVO article = service.selectArticle(no, user);
		model.addAttribute("article", article);
		return "view";
	}
	
	@GetMapping("write")
	public String write() {
		return "write";
	}
	
	@PostMapping("write")
	public String write(ArticleVO vo) {
		service.insertArticle(vo);
		return "redirect:/list";
	}
	
	@GetMapping("download")
	public ResponseEntity<Resource> download(int fno) throws IOException {
		
		FileVO vo = service.selectFile(fno);
		
		ResponseEntity<Resource> respEntity = service.fileDownload(vo);
		return respEntity;
	}
	
	@GetMapping("delete")
	public String delete(int no) {
		log.info("no : " + no);
		service.deleteArticle(no);
		return "redirect:/list";
	}
	
	@GetMapping("writeComment")
	public String writeComment(@RequestParam("parent") int parent ,@RequestParam("uid") String uid, @RequestParam("content") String content) {
		ArticleVO vo = new ArticleVO();
		vo.setParent(parent);
		vo.setUid(uid);
		vo.setContent(content);
		
		service.insertComment(vo);
		
		return "";
	}
	
}
