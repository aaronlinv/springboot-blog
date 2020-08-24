package com.al.blog.web.admin;

import com.al.blog.po.Blog;
import com.al.blog.service.BlogService;
import com.al.blog.service.TagService;
import com.al.blog.service.TypeService;
import com.al.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/admin")
public class BlogController {
    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:admin/blogs";
    
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    
    @Autowired
    private TagService tagService;
    
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2,sort = "updateTime",direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog , Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable,blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2,sort = "updateTime",direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog , Model model){
        model.addAttribute("page", blogService.listBlog(pageable,blog));
        // 返回这个地址下的blogList片段 ，实现局部渲染
        return "/admin/blogs ::blogList";
    }
    
    @GetMapping("/blogs/input")
    public String input(Model model){
        // 初始化避免页面报错
        model.addAttribute("blog", new Blog());
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        return INPUT;
    }
}
