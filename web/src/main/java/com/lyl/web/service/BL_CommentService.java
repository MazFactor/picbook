package com.lyl.web.service;

import com.lyl.web.entity.BL_Comment;
import com.lyl.web.vo.BL_CommentVO;

import java.util.List;

public interface BL_CommentService {
    public List<BL_CommentVO> queryComments(BL_CommentVO queryCommentVO);
    public int insertNewComment(BL_Comment comment);

    BL_CommentVO queryCommentById(Integer commentId);
}
