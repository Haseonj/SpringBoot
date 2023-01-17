package kr.co.ch10.vo;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data // args, getter, setter 포함해줌
@Entity
@Table(name = "member")
public class MemberVO {

	@Id
	private String uid;
	private String name;
	private String hp;
	private String pos;
	private int dep;
	
	@CreationTimestamp
	private LocalDateTime rdate;
}
