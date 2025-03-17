package com.leverx.ratingsystem.mapper.user;

import com.leverx.ratingsystem.dto.user.AdminUserDto;
import com.leverx.ratingsystem.mapper.ModelDtoMapper;
import com.leverx.ratingsystem.model.user.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdminUserMapper implements ModelDtoMapper<AdminUserDto, User> {

    @Override
    public AdminUserDto toDto(User user) {
        return new AdminUserDto(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt());
    }

    @Override
    public User toModel(AdminUserDto adminUserDto) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<AdminUserDto> toDto(List<User> users) {
        var adminUserDtos = new ArrayList<AdminUserDto>();
        for (var user : users) {
            adminUserDtos.add(toDto(user));
        }
        return adminUserDtos;
    }

    @Override
    public List<User> toModel(List<AdminUserDto> adminUserDtos) {
        var users = new ArrayList<User>();
        for (var adminUserDto : adminUserDtos) {
            users.add(toModel(adminUserDto));
        }
        return users;
    }
}
