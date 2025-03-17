package com.example.project_orders_manager.models.enums;

public enum OrderStatus {
    PENDING(0),
    PAID(1),
    INVOICED(2),
    SENT(3),
    DELIVERED(4);

    private final int code;

    OrderStatus(int code) {
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }

    public static OrderStatus fromCode(int code){
        for (OrderStatus status : OrderStatus.values()){
            if (status.getCode()==code){
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid order status code: " + code);
    }
}
