package com.rwto.mybatis.scripting.xmltags;

import java.util.List;

/**
 * @author renmw
 * @create 2024/9/19 9:32
 * 存储混合sqlNode，包括静态和动态，
 **/
public class MixedSqlNode implements SqlNode{
    //组合模式，拥有一个SqlNode的List
    private List<SqlNode> contents;

    public MixedSqlNode(List<SqlNode> contents) {
        this.contents = contents;
    }

    @Override
    public boolean apply(DynamicContext context) {
        // 依次调用list里每个元素的apply
        contents.forEach(node -> node.apply(context));
        return true;
    }

}
