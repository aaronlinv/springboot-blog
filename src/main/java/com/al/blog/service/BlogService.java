package com.al.blog.service;

import com.al.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    
    Blog getBlog(Long id);

    // 按照条件查询
    Page<Blog> listBlog(Pageable pageable, Blog blog);

    Blog save(Blog blog);


    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);
}
