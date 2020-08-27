package com.al.blog.service;

import com.al.blog.po.Comment;

import java.util.List;

public interface CommentService{
    List<Comment> listCommentByBlogId(Long id);

    Comment saveComment(Comment comment);
}
