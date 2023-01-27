package kr.co.farmstory.service;


import kr.co.farmstory.dao.ArticleDAO;
import kr.co.farmstory.entity.UserEntity;
import kr.co.farmstory.vo.ArticleVO;
import kr.co.farmstory.vo.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ArticleService {

    @Autowired
    private ArticleDAO dao;

    @Transactional
    public int insertArticle(ArticleVO vo) {

        // 글 등록
        int result = dao.insertArticle(vo);

        // 파일 업로드
        FileVO fvo = fileUpload(vo);

        if(fvo != null) {
            // 파일 등록
            dao.insertFile(fvo);
        }

        return result;
    }

    @Transactional
    public int insertComment(ArticleVO vo) {
        log.info("vo.getParent :" + vo.getParent());
        dao.insertComment(vo);
        dao.updateArticleComment(vo.getParent());
        return 0;
    }

    public List<ArticleVO> selectComments(int no){
        List<ArticleVO> comments = dao.selectComments(no);
        return comments;
    }

    @Transactional
    public ArticleVO selectArticle(int no, UserEntity user) {
        ArticleVO article = dao.selectArticle(no);

        // 글 작성자가 아닌 사용자만 조회수 증가
        if(!article.getUid().equals(user.getUid())) {
            dao.updateArticleHit(no);
        }

        return article;
    }

    @Transactional
    public FileVO selectFile(int fno) {
        FileVO vo = dao.selectFile(fno);
        dao.updateDownload(fno);

        return vo;
    }

    public List<ArticleVO> selectArticles(int start, String cate) {
        return dao.selectArticles(start, cate);
    }

    public int updateArticle(ArticleVO vo) {
        return dao.updateArticle(vo);
    }

    @Transactional
    public int deleteArticle(int no, int file) {
        int result = dao.deleteArticle(no);
        if(file == 1) {
            dao.deleteFile(no);
        }
        return result;
    }

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    public FileVO fileUpload(ArticleVO vo) {
        // 첨부 파일
        MultipartFile file = vo.getFname();
        FileVO fvo = null;

        if(!file.isEmpty()) {
            // 시스템 경로
            String path = new File(uploadPath).getAbsolutePath();
            log.info("path : " + path);

            // 새 파일명 생성
            String oName = file.getOriginalFilename();
            String ext = oName.substring(oName.lastIndexOf("."));
            String nName = UUID.randomUUID().toString()+ext;

            // 파일 저장
            try {
                file.transferTo(new File(path, nName));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            fvo = FileVO.builder()
                    .parent(vo.getNo())
                    .OriName(oName)
                    .newName(nName)
                    .build();
        }
        return fvo;
    }

    public ResponseEntity<Resource> fileDownload(FileVO vo) throws IOException {

        Path path = Paths.get(uploadPath+vo.getNewName());
        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition
                .builder("attachment")
                .filename(vo.getOriName(), StandardCharsets.UTF_8)
                .build());

        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);

    }

    // 페이지 시작값
    public int getLimitStart(int currentPage) {
        return (currentPage - 1) * 10;
    }

    public int getCurrentpage(String pg) {
        int currentPage = 1;

        if(pg != null) {
            currentPage = Integer.parseInt(pg);
        }
        return currentPage;
    }

    // 전체 게시물 갯수
    public int getTotalCount(String cate) {
        return dao.selectCountTotal(cate);
    }

    // 마지막 페이지 번호
    public int getLastPageNum(int total) {

        int lastPage = 0;

        if(total % 10 == 0) {
            lastPage = total / 10;
        }else {
            lastPage = total / 10 + 1;
        }
        return lastPage;
    }

    // 페이지 시작 번호
    public int getPageStartNum(int total, int start) {
        return total - start;
    }

    // 페이지 그룹
    public int[] getPageGroup(int currentPage, int lastPage) {

        int groupCurrent = (int) Math.ceil(lastPage / 10.0);
        int groupStart = (groupCurrent - 1) * 10 + 1;
        int groupEnd = groupCurrent * 10;

        if(groupEnd > lastPage) {
            groupEnd = lastPage;
        }
        int[] groups = {groupStart, groupEnd};
        return groups;
    }
}