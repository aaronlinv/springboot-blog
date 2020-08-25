package com.al.blog.web.admin;

import com.al.blog.po.Blog;
import com.al.blog.po.Type;
import com.al.blog.po.User;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class BlogController {
    private static final String INPUT = "/admin/blogs-input";
    private static final String LIST = "/admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        // title 默认竟然是'null' 难怪首次进入查询不到数据
        blog.setTitle("");
        System.out.println("blog == >" +blog);
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        System.out.println("listBlog == >"+blogService.listBlog(pageable, blog).getTotalElements());
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        System.out.println("search blog == >" +blog);
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        System.out.println("listBlog == >"+blogService.listBlog(pageable, blog).getTotalElements());

        // 返回这个地址下的blogList片段 ，实现局部渲染
        return "/admin/blogs ::blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model) {
        // 初始化避免页面报错
        Blog blog = new Blog();
        blog.setType(new Type());
        model.addAttribute("blog", blog);
        setTagsAndTypes(model);
        return INPUT;
    }
    private void setTagsAndTypes(Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }
    
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        // 初始化避免页面报错
        Blog blog = blogService.getBlog(id);
        // 转tag list 为字符串 1,2,3
        blog.init();
        model.addAttribute("blog", blog);
        setTagsAndTypes(model);
        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes) {
        User user = (User) session.getAttribute("user");
        blog.setUser(user);
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        
        
        Blog b = blogService.save(blog);
        if (b == null) {
            attributes.addFlashAttribute("message","新增失败");
        } else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return REDIRECT_LIST;
    }
}
