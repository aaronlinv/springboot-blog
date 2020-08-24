package com.al.blog.service;

import com.al.blog.NotFoundException;
import com.al.blog.dao.BlogRepository;
import com.al.blog.po.Blog;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, Blog blog) {
        return null;
    }

    @Override
    public Blog save(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blogById = blogRepository.getOne(id);
        if (blogById == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, blogById);
        return blogRepository.save(blogById);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
