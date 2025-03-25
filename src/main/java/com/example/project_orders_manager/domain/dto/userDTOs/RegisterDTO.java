package com.example.project_orders_manager.domain.dto.userDTOs;

import com.example.project_orders_manager.domain.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
