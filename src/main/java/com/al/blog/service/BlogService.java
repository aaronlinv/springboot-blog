package com.al.blog.service;

import com.al.blog.po.Blog;
import com.al.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {

    Blog getBlog(Long id);
    
    Blog getAndConvert(Long id);

    // 按照条件查询
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);
    
    Page<Blog> listBlog(String query,Pageable pageable);
    
    List<Blog> listRecommendBlogTop(Integer size);

    Blog save(Blog blog);


    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);
}
