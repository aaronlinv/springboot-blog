package com.al.blog.web;

import com.al.blog.NotFoundException;
import com.al.blog.service.BlogService;
import com.al.blog.service.TagService;
import com.al.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    
    @Autowired
    private TypeService typeService;
    
    @Autowired 
    private TagService tagService;
    
    @GetMapping("/")
    public String index(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) 
                                    Pageable pageable, Model model) {
/*        // int i= 10/0;
        String blog= null;
        if(blog == null){
            throw new  NotFoundException("博客不存在");
        }*/
        
        // System.out.println("-------index----------");
        
        
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));        
        model.addAttribute("tags", tagService.listTagTop(10));        
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));        
        return "index";
    }  
    
    @PostMapping("/search")
    public String index(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) 
                                    Pageable pageable, Model model,@RequestParam String query) {
        
        model.addAttribute("page", blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model) {
        // System.out.println("-------blog----------");
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }
    
    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }
}
