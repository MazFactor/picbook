package com.lyl.web.service.impl;

import com.lyl.web.entity.BL_Comment;
import com.lyl.web.mapper.BL_CommentMapper;
import com.lyl.web.service.BL_CommentService;
import com.lyl.web.vo.BL_CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BL_CommentServiceImpl implements BL_CommentService {

    @Autowired
    private BL_CommentMapper bl_commentMapper;
    //存放迭代找出的所有子代的集合
    private List<BL_CommentVO> tempReplys = new ArrayList<>();

    /**
     * @Description: 查询评论
     * @Param:
     * @Return: 评论消息
     */
    @Override
    public List<BL_CommentVO> queryComments(BL_CommentVO queryCommentVO) {
        //查询出父节点
        List<BL_CommentVO> comments = bl_commentMapper.findCommentByParentIdIsNull(queryCommentVO);
        for(BL_CommentVO comment : comments){
            Integer b2_id = comment.getB2_id();
            String parentNickname1 = comment.getB0_name();
            queryCommentVO.setB2_id(b2_id);
            List<BL_CommentVO> childComments = bl_commentMapper.findCommentByParentIdIsNotNull(queryCommentVO);
            //查询出子评论
            combineChildren(childComments, parentNickname1);
            comment.setSubCommentVOs(tempReplys);
            tempReplys = new ArrayList<>();
        }
        return comments;
    }

    /**
     * @Description: 查询出子评论
     * @Param: childComments：所有子评论
     * @Param: parentNickname1：父评论的姓名
     * @Return:
     */
    private void combineChildren(List<BL_CommentVO> childComments, String parentNickname1) {
        //判断是否有一级子回复
        if(childComments.size() > 0){
            //循环找出子评论的id
            for(BL_CommentVO childComment : childComments){
                String parentNickname = childComment.getB0_name();
                childComment.setB2_pName(parentNickname1);
                tempReplys.add(childComment);
                Integer childId = childComment.getB2_id();
                //查询二级以及所有子集回复
                recursively(childId, parentNickname);
            }
        }
    }

    /**
     * @Description: 循环迭代找出子集回复
     * @Param: childId：子评论的id
     * @Param: parentNickname1：子评论的姓名
     * @Return:
     */
    private void recursively(Integer childId, String parentNickname1) {
        //根据子一级评论的id找到子二级评论
        List<BL_CommentVO> replayComments = bl_commentMapper.findChildrenByParentId(childId);

        if(replayComments.size() > 0){
            for(BL_CommentVO replayComment : replayComments){
                String parentNickname = replayComment.getB0_name();
                replayComment.setB2_pName(parentNickname1);
                Integer replayId = replayComment.getB2_id();
                tempReplys.add(replayComment);
                //循环迭代找出子集回复
                recursively(replayId,parentNickname);
            }
        }
    }


    // 添加评论信息
    @Override
    public int insertNewComment(BL_Comment comment) {
        return bl_commentMapper.insertNewComment(comment);
    }

    @Override
    public BL_CommentVO queryCommentById(Integer commentId) {
        return bl_commentMapper.queryCommentById(commentId);
    }
}
