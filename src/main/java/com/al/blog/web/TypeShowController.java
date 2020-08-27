package com.al.blog.web;

import com.al.blog.po.Blog;
import com.al.blog.po.Type;
import com.al.blog.service.BlogService;
import com.al.blog.service.TypeService;
import com.al.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.temporal.Temporal;
import java.util.List;

@Controller
// Spring 不允许同名Controller（即使不在同一个包下） 
public class TypeShowController {
    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 2, direction = Sort.Direction.DESC, sort = "updateTime") Pageable pageable,
                        @PathVariable Long id, Model model) {

        List<Type> types = typeService.listTypeTop(10000);
        // 获取最多数目的类别
        if (id == -1) {
            id = types.get(0).getId();
        }
        model.addAttribute("types", types);
        
        BlogQuery blogQuery = new BlogQuery();
        System.out.println("blogQuery == >" +blogQuery);
        blogQuery.setTypeId(id);
        
        Page<Blog> blogs = blogService.listBlog(pageable, blogQuery);
        model.addAttribute("page",blogs );
        model.addAttribute("activeTypeId", id);
        return "types";
    }

}
