package kr.co.bookstore1.service;

import kr.co.bookstore1.dao.CustomerDAO;
import kr.co.bookstore1.vo.CustomerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerDAO dao;

    public void insertCustomer(CustomerVO vo) {
        dao.insertCustomer(vo);
    }

    public CustomerVO selectCustomer(int custId) {
        return dao.selectCustomer(custId);
    }

    public List<CustomerVO> selectCustomers() {
        return dao.selectCustomers();
    }

    public void updateCustomer(CustomerVO vo) {
        dao.updateCustomer(vo);
    }

    public void deleteCustomer(int custId) {
        dao.deleteCustomer(custId);
    }
}
