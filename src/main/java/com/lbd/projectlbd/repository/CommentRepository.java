package com.lbd.projectlbd.repository;

import com.lbd.projectlbd.entity.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Timestamp;
import java.util.List;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c JOIN c.delegation d WHERE d.id = :delegationId")
    List<Comment> getAllByDelegationId(Long delegationId);

    List<Comment> getAllByDate(Timestamp date);

    @Query("SELECT c FROM Comment c JOIN c.comment parent WHERE c.id = :commentId OR parent.id = :commentId")
    List<Comment> getAllByUpComment(Long commentId);
}