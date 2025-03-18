package com.leverx.ratingsystem.mapper.comment;

import com.leverx.ratingsystem.dto.comment.AdminCommentDto;
import com.leverx.ratingsystem.mapper.ModelDtoMapper;
import com.leverx.ratingsystem.model.comment.Comment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdminCommentMapper implements ModelDtoMapper<AdminCommentDto, Comment> {

    @Override
    public AdminCommentDto toDto(Comment comment) {
        return new AdminCommentDto(comment.getUser().getId(),
                comment.getId(),
                comment.getMessage(),
                comment.getGrade(),
                comment.getCreatedAt(),
                comment.getStatus(),
                comment.getEditCount());
    }

    @Override
    public Comment toModel(AdminCommentDto adminCommentDto) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<AdminCommentDto> toDto(List<Comment> comments) {
        var adminCommentDtos = new ArrayList<AdminCommentDto>();
        for (var comment : comments) {
            adminCommentDtos.add(toDto(comment));
        }
        return adminCommentDtos;
    }

    @Override
    public List<Comment> toModel(List<AdminCommentDto> adminCommentDtos) {
        var comments = new ArrayList<Comment>();
        for (var adminComment : adminCommentDtos) {
            comments.add(toModel(adminComment));
        }
        return comments;
    }
}
