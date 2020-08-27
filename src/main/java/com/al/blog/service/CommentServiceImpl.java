package com.al.blog.service;

import com.al.blog.dao.CommentRepository;
import com.al.blog.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    
    
    
    
    @Override
    public List<Comment> listCommentByBlogId(Long id) {
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
        return commentRepository.findByBlogId(id,sort);
    }

    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId!=-1) {
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        }else{
            // 传了-1 parentComment对象其实已经被初始化了，所以要设置为null
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

}
