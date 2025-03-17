package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.dto.comment.AdminCommentDto;
import com.leverx.ratingsystem.dto.user.AdminUserDto;
import com.leverx.ratingsystem.model.comment.CommentStatus;
import com.leverx.ratingsystem.model.user.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final CommentService commentService;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<AdminCommentDto> getPendingComments() {
        return commentService.getCommentsWithStatus(CommentStatus.PENDING);
    }

    @Transactional
    public void approveComment(UUID commentId) {
        commentService.changeCommentStatus(commentId, CommentStatus.APPROVED);
        // TODO: Tell Rating system to update Seller rating
    }

    @Transactional
    public void rejectComment(UUID commentId) {
        commentService.changeCommentStatus(commentId, CommentStatus.REJECTED);
    }

    @Transactional(readOnly = true)
    public List<AdminUserDto> getPendingUsers() {
        return userService.getUsersWithStatus(UserStatus.PENDING);
    }

    @Transactional
    public void approveUser(UUID userId) {
        userService.changeUserStatus(userId, UserStatus.APPROVED);
    }

    @Transactional
    public void rejectUser(UUID userId) {
        userService.changeUserStatus(userId, UserStatus.REJECTED);
    }
}
