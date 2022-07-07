package com.mysite.sbb.article.controller;

import com.mysite.sbb.article.dao.ArticleRepository;
import com.mysite.sbb.article.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/usr/article")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping("/test")
    @ResponseBody
    public String testFunc() {
        return "test";
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<Article> showList() {
        return articleRepository.findAll();
    }

}