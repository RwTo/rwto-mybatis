package com.rwto.mybatis.scripting.xmltags;

/**
 * @author renmw
 * @create 2024/9/19 9:34
 **/
public class StaticTextSqlNode implements SqlNode{
    private String text;

    public StaticTextSqlNode(String text) {
        this.text = text;
    }

    @Override
    public boolean apply(DynamicContext context) {
        //将文本加入context
        context.appendSql(text);
        return true;
    }
}
