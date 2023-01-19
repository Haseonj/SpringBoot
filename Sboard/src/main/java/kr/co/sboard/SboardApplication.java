package kr.co.sboard;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SpringBootApplication
public class SboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SboardApplication.class, args);
	}
	
	@GetMapping(value = {"", "index"})
	public String index(Principal principal) {
		
		// 로그인 필터 처리
		if(principal != null) {
			// 로그인을 했을 경우
			return "redirect:/list";
		}else {
			// 로그인을 안했을 경우
			return "redirect:/user/login";
		}
	}

}
