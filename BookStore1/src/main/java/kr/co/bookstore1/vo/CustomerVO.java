package kr.co.bookstore1.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerVO {
    private int custId;
    private String name;
    private String address;
    private String phone;
}
