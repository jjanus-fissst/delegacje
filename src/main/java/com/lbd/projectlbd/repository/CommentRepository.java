package com.lbd.projectlbd.repository;

import com.lbd.projectlbd.entity.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
