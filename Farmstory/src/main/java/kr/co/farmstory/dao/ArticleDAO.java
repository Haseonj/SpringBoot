package kr.co.farmstory.dao;

import kr.co.farmstory.vo.ArticleVO;
import kr.co.farmstory.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleDAO {

    public int insertArticle(ArticleVO vo);

    public int insertFile(FileVO vo);

    public int insertComment(ArticleVO vo);

    public int updateArticleComment(int no);

    public FileVO selectFile(int fno);

    public int updateDownload(int fno);

    public int selectCountTotal(@Param("cate") String cate);

    public ArticleVO selectArticle(int no);

    public List<ArticleVO> selectComments(int no);

    public List<ArticleVO> selectArticles(@Param("start") int start,@Param("cate") String cate);

    public int updateArticle(ArticleVO vo);

    public int updateArticleHit(int no);

    public int deleteArticle(int no);

    public int deleteFile(int no);
}
