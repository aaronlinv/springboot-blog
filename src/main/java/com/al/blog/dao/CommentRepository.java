package com.al.blog.dao;

import com.al.blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository  extends JpaRepository<Comment,Long> {
    // parentComment为null
    List<Comment> findByBlogIdAndAndParentCommentNull(Long blogId, Sort sort);
}
