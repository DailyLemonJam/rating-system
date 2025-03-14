package com.leverx.ratingsystem.mapper;

import com.leverx.ratingsystem.dto.comment.CommentDto;
import com.leverx.ratingsystem.model.comment.Comment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper {

    public CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getMessage(), comment.getGrade(),
                comment.getCreatedAt(), comment.getSeller().getId());
    }

    public Comment toComment(CommentDto commentDto) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public List<CommentDto> toDto(List<Comment> comments) {
        var dtos = new ArrayList<CommentDto>();
        for (var comment : comments) {
            dtos.add(toDto(comment));
        }
        return dtos;
    }

    public List<Comment> toComment(List<CommentDto> commentDtos) {
        var comments = new ArrayList<Comment>();
        for (var dto : commentDtos) {
            comments.add(toComment(dto));
        }
        return comments;
    }

}
