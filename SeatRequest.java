package com.example.ordering.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

public class SeatRequest {
    @Schema(description = "用户名", example = "alice")
    @NotBlank
    private String username;

    @Schema(description = "桌号(>=1)", example = "3")
    @Min(1)
    private int tableNumber;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
}


