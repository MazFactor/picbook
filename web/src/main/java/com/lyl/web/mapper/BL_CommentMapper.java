package com.lyl.web.mapper;

import com.lyl.web.entity.BL_Comment;
import com.lyl.web.vo.BL_CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BL_CommentMapper  extends Mapper {

    List<BL_CommentVO> findCommentByParentIdIsNull(BL_CommentVO queryCommentVO);

    List<BL_CommentVO> findCommentByParentIdIsNotNull(BL_CommentVO queryCommentVO);

    List<BL_CommentVO> findChildrenByParentId(Integer childId);

    int insertNewComment(BL_Comment comment);

    List<BL_CommentVO> queryCommentsForSideBar(BL_CommentVO queryCommentVO);

    BL_CommentVO queryCommentById(Integer commentId);
}
