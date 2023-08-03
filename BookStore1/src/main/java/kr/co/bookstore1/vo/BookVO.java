package kr.co.bookstore1.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookVO {
    private int bookId;
    private String bookName;
    private String publisher;
    private int price;
}
