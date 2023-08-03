package kr.co.bookstore1.service;

import kr.co.bookstore1.dao.BookDAO;
import kr.co.bookstore1.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookDAO dao;

    public void insertBook(BookVO vo) {
        dao.insertBook(vo);
    }

    public BookVO selectBook(int bookId) {
        return dao.selectBook(bookId);
    }

    public List<BookVO> selectBooks() {
        List<BookVO> books = dao.selectBooks();
        return books;
    }

    public void updateBook(BookVO vo) {
        dao.updateBook(vo);
    }

    public void deleteBook(int bookId) {
        dao.deleteBook(bookId);
    }
}
