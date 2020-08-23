package com.al.blog.web.admin;

import com.al.blog.po.Type;
import com.al.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    TypeService typeService;
    
    @GetMapping("/types")
    public String list(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) 
                                   Pageable pageable, Model model){
        
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }
    
    @GetMapping("/types/input")
    public String input(Model model){
        // 为了前端校验type输入非空
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }
    
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        // 不允许添加重复类型
        Type typeByName = typeService.getTypeByName(type.getName());
        if(typeByName!=null){
            // 属性名 定义变量名 返回参数
            result.rejectValue("name", "nameError","不能重复添加分类");
        }
            
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.save(type);
        if (t == null) {
            attributes.addFlashAttribute("message","添加失败！");
        }else{
            attributes.addFlashAttribute("message","添加成功！");
        }
        return "redirect:/admin/types";
    }
}
