package com.leverx.ratingsystem.mapper.comment;

import com.leverx.ratingsystem.dto.comment.CommentDto;
import com.leverx.ratingsystem.mapper.ModelDtoMapper;
import com.leverx.ratingsystem.model.comment.Comment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper implements ModelDtoMapper<CommentDto, Comment> {

    @Override
    public CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getMessage(),
                comment.getGrade(),
                comment.getCreatedAt(),
                comment.getUser().getId());
    }

    @Override
    public Comment toModel(CommentDto commentDto) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<CommentDto> toDto(List<Comment> comments) {
        var commentDtos = new ArrayList<CommentDto>();
        for (var comment : comments) {
            commentDtos.add(toDto(comment));
        }
        return commentDtos;
    }

    @Override
    public List<Comment> toModel(List<CommentDto> commentDtos) {
       var comments = new ArrayList<Comment>();
       for (var comment : commentDtos) {
           comments.add(toModel(comment));
       }
       return comments;
    }
}
