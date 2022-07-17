package com.mysite.sbb.article.controller;

import com.mysite.sbb.article.dao.ArticleRepository;
import com.mysite.sbb.article.domain.Article;
import com.mysite.sbb.user.dao.UserRepository;
import com.mysite.sbb.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usr/article")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("list")
    public String showList(Model model) {
        List<Article> articles = articleRepository.findAll();

        model.addAttribute("articles", articles);
        return "usr/article/list";
    }

    @RequestMapping("doModify")
    @ResponseBody
    public Article doModify(long id, String title, String body) {
        Article article = articleRepository.findById(id).get();

        if (title != null) {
            article.setTitle(title);
        }

        if (body != null) {
            article.setBody(body);
        }

        article.setUpdateDate(LocalDateTime.now());

        articleRepository.save(article);

        return article;
    }

    @RequestMapping("doDelete")
    @ResponseBody
    public String doDelete(long id) {
        if (articleRepository.existsById(id) == false) {
            return "%d번 게시물은 이미 삭제되었거나 존재하지 않습니다.".formatted(id);
        }

        articleRepository.deleteById(id);
        return "%d번 게시물이 삭제되었습니다.".formatted(id);
    }


    @RequestMapping("write")
    public String showWrite() {
        return "usr/article/write";
    }

    @RequestMapping("detail")
    public String showDelate(long id, Model model) {
        Optional<Article> oPArticle = articleRepository.findById(id);
        Article article = oPArticle.get();

        model.addAttribute("article", article);
        return "usr/article/detail";
    }

    @RequestMapping("modify")
    public String showmodify(long id, Model model) {
        Optional<Article> oPArticle = articleRepository.findById(id);
        Article article = oPArticle.get();

        model.addAttribute("article", article);
        return "usr/article/modify";
    }

    @RequestMapping("/doWrite")
    @ResponseBody
    public String doWrite(String title, String body) {
        if (title == null || title.trim().length() == 0) {
            return "제목을 입력해주세요.";
        }

        title = title.trim();

        if (body == null || body.trim().length() == 0) {
            return "내용을 입력해주세요.";
        }

        body = body.trim();

        Article article = new Article();
        article.setRegDate(LocalDateTime.now());
        article.setUpdateDate(LocalDateTime.now());
        article.setTitle(title);
        article.setBody(body);
        User user = userRepository.findById(1L).get();
        article.setUser(user);

        articleRepository.save(article);

        return """
                        <script>
                        alert('%d번 게시물이 생성되었습니다.');
                        location.replace('list');
                        </script>
                """.formatted(article.getId());
    }
}