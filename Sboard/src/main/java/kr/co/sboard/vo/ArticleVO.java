package kr.co.sboard.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleVO {

	private int no; 
	private int parent;
	private int comment;
	private String cate;
	private String title;
	private String content;
	private int file;
	private MultipartFile fname;
	private int hit;
	private String uid;
	private String regip;
	private String rdate;
	
	// 추가 필드
	private String nick;
	private FileVO fileVO;
	
	
	public String getRdate() {
		return rdate.substring(2 ,10);
	}
}
