package com.airbyte.daneshmal.comment;

import com.airbyte.daneshmal.dto.CommentDTO;
import com.airbyte.daneshmal.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.airbyte.daneshmal.security.permission.ManagePermission.COMPANY_WRITE;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<Comment> createComment(@RequestBody CommentDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }


    @GetMapping("/findAll")
    @PreAuthorize(COMPANY_WRITE)
    public ResponseEntity<Page<Comment>> findAllComments(Pageable pageable) {
        return new ResponseEntity<>(service.getAll(pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(COMPANY_WRITE)
    public ResponseEntity<Void> deleteComment(@PathVariable String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
