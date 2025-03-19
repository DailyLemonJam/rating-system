package com.leverx.ratingsystem.service.admin;

import com.leverx.ratingsystem.dto.comment.AdminCommentDto;
import com.leverx.ratingsystem.dto.user.AdminUserDto;
import com.leverx.ratingsystem.exception.CommentNotFoundException;
import com.leverx.ratingsystem.exception.UserNotFoundException;
import com.leverx.ratingsystem.mapper.ModelDtoMapper;
import com.leverx.ratingsystem.model.comment.Comment;
import com.leverx.ratingsystem.model.comment.CommentStatus;
import com.leverx.ratingsystem.model.user.User;
import com.leverx.ratingsystem.model.user.UserStatus;
import com.leverx.ratingsystem.repository.CommentRepository;
import com.leverx.ratingsystem.repository.UserRepository;
import com.leverx.ratingsystem.service.rating.RatingService;
import com.leverx.ratingsystem.service.rating.RatingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ModelDtoMapper<AdminCommentDto, Comment> adminCommentMapper;
    private final ModelDtoMapper<AdminUserDto, User> adminUserMapper;
    private final RatingService ratingService;

    @Transactional(readOnly = true)
    @Override
    public List<AdminCommentDto> getPendingComments() {
        var pendingComments = commentRepository.findAllByStatus(CommentStatus.PENDING);
        return adminCommentMapper.toDto(pendingComments);
    }

    @Transactional
    @Override
    public void approveComment(UUID commentId) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        comment.setStatus(CommentStatus.APPROVED);
        commentRepository.save(comment);
        ratingService.updateUserRatingByUserId(comment.getUser().getId());
    }

    @Transactional
    @Override
    public void rejectComment(UUID commentId) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        comment.setStatus(CommentStatus.REJECTED);
        commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AdminUserDto> getPendingUsers() {
        var pendingUsers = userRepository.findAllByStatus(UserStatus.PENDING);
        return adminUserMapper.toDto(pendingUsers);
    }

    @Transactional
    @Override
    public void approveUser(UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setStatus(UserStatus.APPROVED);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void rejectUser(UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setStatus(UserStatus.REJECTED);
        userRepository.save(user);
    }
}
