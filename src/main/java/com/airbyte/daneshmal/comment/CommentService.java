package com.airbyte.daneshmal.comment;

import com.airbyte.daneshmal.common.ParentService;
import com.airbyte.daneshmal.dto.CommentDTO;
import com.airbyte.daneshmal.models.Comment;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CommentService extends ParentService<Comment, CommentRepository, CommentDTO> {

    public CommentService(CommentRepository repository) {
        super(repository);
    }

    @Override
    public Comment updateModelFromDto(Comment comment, CommentDTO commentDTO) {
        return null;
    }

    @Override
    public Comment convertDTO(CommentDTO dto) {
        Comment comment = new Comment();
        comment.setEmail(dto.getEmail());
        comment.setFullName(dto.getFullName());
        comment.setMessage(dto.getMessage());
        return comment;
    }

    @Override
    public List<Comment> getWithSearch(CommentDTO search) {
        return null;
    }

    @Override
    protected List<Comment> postFetch(List<Comment> comments) {
        return comments
                .stream()
                .sorted(Comparator.comparing(Comment::getDate).reversed())
                .toList();
    }
}
