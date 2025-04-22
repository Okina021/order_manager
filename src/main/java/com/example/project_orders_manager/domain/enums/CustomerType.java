package com.example.project_orders_manager.domain.enums;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum CustomerType {
    PESSOA_FISICA("PF"),
    PESSOA_JURIDICA("PJ");

    private final String code;

    CustomerType(String code) {
        this.code = code;
    }

    public static CustomerType fromCode(String code){
      for (CustomerType type : CustomerType.values()){
          if (Objects.equals(code, type.getCode())){
              return type;
          }
      }
      throw  new IllegalArgumentException("Customer type not found");
    }
}
