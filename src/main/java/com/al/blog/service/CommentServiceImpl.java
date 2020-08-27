package com.al.blog.service;

import com.al.blog.dao.CommentRepository;
import com.al.blog.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    
    
    
    
    @Override
    public List<Comment> listCommentByBlogId(Long id) {
        Sort sort = Sort.by("createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndAndParentCommentNull(id,sort);
        
        // 原来的评论回复列表是一种树状结构 一层接着一层，现在把回复的都存到一个list里
        return eachComment(comments);
    }

    /**
     * 循环顶层结点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        // 合并各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
        
    }

    private void combineChildren(List<Comment> comments) {
        for (Comment comment : comments) {
            List<Comment> replies = comment.getReplyComments();
            for (Comment reply : replies) {
                // 循环找出子代
                recursively(reply);
            }
            comment.setReplyComments(tempReplies);
            // 清除临时存放区域

            tempReplies = new ArrayList<>();
        }
    }
    
    private List<Comment> tempReplies = new ArrayList<>();
    /**
     * 迭代找出子代集合
     */
    private void recursively(Comment comment) {
        tempReplies.add(comment);// 顶节点添加到临时存放集合
        if(comment.getReplyComments().size()>0){
            List<Comment> replies = comment.getReplyComments();
            for (Comment reply : replies) {
                tempReplies.add(reply);
                if( reply.getReplyComments().size()>0){
                    recursively(reply);
                }
            }
        }
    }

    @Transactional
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
