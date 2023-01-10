package kr.co.bookstore.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookVO {

	// view와 model의 정보를 받아 상호 전달해 줄 객체 (book 테이블)
	private int bookId;
	private String bookName;
	private String publisher;
	private int price;
}
