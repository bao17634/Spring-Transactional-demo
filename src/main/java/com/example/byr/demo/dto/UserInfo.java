package com.example.byr.demo.dto;

import com.example.byr.demo.domain.Account;
import com.example.byr.demo.domain.UOrder;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class UserInfo implements Serializable {

  private Integer id;

  private String name;

  private String pwd;

  private String phone;

  private String address;

  private Account account;

  private List<UOrder> orderList;


}
