package kr.co.bookstore1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("kr.co.bookstore1.dao")
@SpringBootApplication
public class BookStore1Application {

	public static void main(String[] args) {
		SpringApplication.run(BookStore1Application.class, args);
	}

}