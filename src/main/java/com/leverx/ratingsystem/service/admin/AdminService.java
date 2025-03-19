package com.leverx.ratingsystem.service.admin;

import com.leverx.ratingsystem.dto.comment.AdminCommentDto;
import com.leverx.ratingsystem.dto.user.AdminUserDto;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    List<AdminCommentDto> getPendingComments();

    void approveComment(UUID commentId);

    void rejectComment(UUID commentId);

    List<AdminUserDto> getPendingUsers();

    void approveUser(UUID userId);

    void rejectUser(UUID userId);
}
