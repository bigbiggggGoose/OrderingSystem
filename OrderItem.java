package com.example.ordering.dto;
/*
 * 订单项
 */
public class OrderItem {
    @io.swagger.v3.oas.annotations.media.Schema(description = "菜品ID", example = "1")
    @jakarta.validation.constraints.Min(1)
    private long dishId;
    @io.swagger.v3.oas.annotations.media.Schema(description = "数量", example = "2")
    @jakarta.validation.constraints.Min(1)
    private int quantity;
    @io.swagger.v3.oas.annotations.media.Schema(description = "单价(由服务端填充)", example = "28")
    private int unitPrice;

    public long getDishId() {
        return dishId;
    }

    public void setDishId(long dishId) {
        this.dishId = dishId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }
}


