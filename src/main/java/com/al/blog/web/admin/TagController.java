package com.al.blog.web.admin;

import com.al.blog.po.Tag;
import com.al.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tagsPage(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.DESC)
                                   Pageable pageable, Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "/admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "/admin/tags-input";

    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        System.out.println("tag == >"+tag);
        Tag tagByName = tagService.getTagByName(tag.getName());
        if (tagByName != null) {
            result.rejectValue("name", "nameError", "不能添加重复分类");
        }

        if (result.hasErrors()) {
            return "/admin/tags-input";
        }

        Tag t = tagService.save(tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "添加失败！");
        } else {
            attributes.addFlashAttribute("message", "添加成功！");
        }
        return "redirect:/admin/tags";
    }
    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }
    
    
}
