package kr.co.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.bookstore.service.BookService;
import kr.co.bookstore.vo.BookVO;

@Controller
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	private BookService service;
	
	@GetMapping("/list")
	public String list(Model model) {
		// BookService에 selectBooks 호출하여, 리턴값으로 db 정보를 목록화 하여 받아옴 
		List<BookVO> books = service.selectBooks();
		// 목록화 된 db 정보를 view로 전달
		model.addAttribute("books", books);
		return "/book/list";
	}
	
	@GetMapping("/register")
	public String register() {
		return "/book/register";
	}
	
	@PostMapping("/register")
	public String register(BookVO vo) {
		// view에서 받아온 vo 정보를 BookSerivce로 전달
		service.insertBook(vo);
		return "redirect:/book/list";
	}
	
	@GetMapping("/modify")
	public String modify(int bookId, Model model) {
		// a태그에 있던 해당 정보의 도서번호 값을 받아와 BookService로 전달
		BookVO book = service.selectBook(bookId);
		// 도서번호로 조회한 db 정보를 view로 전달
		model.addAttribute("book", book);
		return "/book/modify";
	}
	
	@PostMapping("/modify")
	public String modify(BookVO vo) {
		// 도서수정 에서 받아온 정보를 BookService로 전달
		service.updateBook(vo);
		// 위 코드가 실행된 후 목록으로 redirect 
		return "redirect:/book/list";
	}
	
	@GetMapping("/delete")
	public String delete(int bookId) {
		// a태그에 있던 해당 정보의 도서번호 값을 받아와 BookService로 전달
		service.deleteBook(bookId);
		// 위 코드가 실행된 후 목록으로 redirect 
		return "redirect:/book/list";
	}
}
