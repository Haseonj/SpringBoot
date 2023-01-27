package kr.co.farmstory.controller;

import kr.co.farmstory.entity.UserEntity;
import kr.co.farmstory.security.MyUserDetails;
import kr.co.farmstory.service.ArticleService;
import kr.co.farmstory.vo.ArticleVO;
import kr.co.farmstory.vo.FileVO;
import kr.co.farmstory.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class ArticleController {

    @Autowired
    private ArticleService service;

    @GetMapping("board/list")
    public String list(String group, String cate, String pg, Model model){

        int currentPage = service.getCurrentpage(pg);
        int start = service.getLimitStart(currentPage);
        int total = service.getTotalCount(cate);
        int lastPage = service.getLastPageNum(total);
        int pageStartNum = service.getPageStartNum(total, start);
        int groups[] = service.getPageGroup(currentPage, lastPage);

        List<ArticleVO> articles = service.selectArticles(start, cate);

        model.addAttribute("articles", articles);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("pageStartNum", pageStartNum);
        model.addAttribute("groups", groups);
        model.addAttribute("group", group);
        model.addAttribute("cate", cate);

        return "board/list";
    }
    @GetMapping("board/write")
    public String write(@AuthenticationPrincipal MyUserDetails myUser, String group, String cate, Model model){
        UserEntity user = myUser.getUser();
        model.addAttribute("cate", cate);
        model.addAttribute("group", group);
        model.addAttribute("user", user);
        return "board/write";
    }

    @PostMapping("board/write")
    public String write(ArticleVO vo, String group,HttpServletRequest req){
        vo.setRegip(req.getRemoteAddr());
        service.insertArticle(vo);
        return "redirect:/board/list?group="+group+"&cate="+vo.getCate();
    }

    @GetMapping("board/view")
    public String view(@AuthenticationPrincipal MyUserDetails myUser, int no, String group, Model model){
        UserEntity user = myUser.getUser();
        ArticleVO article = service.selectArticle(no, user);
        List<ArticleVO> comments = service.selectComments(no);
        model.addAttribute("article", article);
        model.addAttribute("comment", comments);
        model.addAttribute("group", group);
        model.addAttribute("cate", article.getCate());
        model.addAttribute("user", user);
        return "board/view";
    }

    @GetMapping("board/writeComment")
    public int writeComment(ArticleVO vo){
        log.info("controller parent : "+ vo.getParent());
        int result = service.insertComment(vo);
        return result;
    }

    @GetMapping("board/download")
    public ResponseEntity<Resource> download(int fno) throws IOException {
        FileVO vo = service.selectFile(fno);
        ResponseEntity<Resource> respEntity = service.fileDownload(vo);
        return respEntity;
    }

    @GetMapping("board/modify")
    public String modify(@AuthenticationPrincipal MyUserDetails myUser, int no, String group, Model model){
        UserEntity user = myUser.getUser();
        ArticleVO article = service.selectArticle(no, user);
        model.addAttribute("article", article);
        model.addAttribute("group", group);
        model.addAttribute("cate", article.getCate());
        return "board/modify";
    }

    @PostMapping("board/modify")
    public String modify(ArticleVO vo, String group){
        service.updateArticle(vo);
        return "redirect:/board/view?no="+vo.getNo()+"&group="+group+"&cate="+vo.getCate();
    }

    @GetMapping("board/delete")
    public String delete(int no, String group, String cate, int file){
        service.deleteArticle(no, file);
        return "redirect:/board/list?group="+group+"&cate="+cate;
    }

}
