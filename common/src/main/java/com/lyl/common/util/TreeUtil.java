package com.jinghuan.common.util;

import com.jinghuan.common.enume.FactoryLevelEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dcc
 * @date 2019-11-08 15:51
 */
public class TreeUtil {

    /**
     * 根据层级获取对应的树 所有工厂的树、所有车间的树、所有线体的树
     * @param date 数据源
     * @param tree
     * @param level 层级（0工厂，1车间，2线体)
     * @return
     */
    public static Tree findChildren(List<Tree> date, Tree tree, Integer level){
        if(tree != null) {
            Long id = tree.getId();
            List<Tree> children = tree.getChildren();
            for(Tree treeDB:date){
                Long parentid = treeDB.getParentId();
                if(parentid.equals(id)){
                    children.add(treeDB);
                    findChildren(date, treeDB, level);
                }
            }
            tree.setChildren(children);
        }else{
            tree = new Tree();
            tree.setId(level.longValue());
            tree.setChildren(new ArrayList<Tree>());
            findChildren(date, tree, level);
        }
        return tree;
    }

    /**
     * 根据层级获取对应的树 根据code获取指定的工厂树、车间树、线体树
     * @param date 数据源
     * @param tree
     * @param level 层级（0工厂，1车间，2线体)
     * @return
     */
    public static Tree findGivenTree(List<Tree> date, Tree tree, Integer level){
        if(tree != null) {
            Long id = tree.getId();
            List<Tree> children = tree.getChildren();
            for(Tree treeDB:date){
                if(level.equals(treeDB.getLevel()) && !level.equals(FactoryLevelEnum.FACTORY.getId())){
                    tree = treeDB;
                }
                Long parentid = treeDB.getParentId();
                if(parentid.equals(id)){
                    children.add(treeDB);
                    findGivenTree(date, treeDB, level);
                }
            }
            tree.setChildren(children);
        }else{
            tree = new Tree();
            tree.setId(level.longValue());
            tree.setChildren(new ArrayList<Tree>());
            findGivenTree(date, tree, level);
        }
        return tree;
    }
}
