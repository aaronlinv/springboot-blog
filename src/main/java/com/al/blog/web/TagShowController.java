package com.al.blog.web;

import com.al.blog.po.Blog;
import com.al.blog.po.Tag;
import com.al.blog.service.BlogService;
import com.al.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;
    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                               Pageable pageable, @PathVariable Long id, Model model) {
        List<Tag> tags = tagService.listTagTop(10000);
        if (id == -1) {
            id = tags.get(0).getId();
        }
        
        model.addAttribute("tags",tags);
        Page<Blog> blogs = blogService.listBlog(id, pageable);

        model.addAttribute("page", blogs);
        model.addAttribute("activeTagId", id);
        
        return "tags";
    }
}
