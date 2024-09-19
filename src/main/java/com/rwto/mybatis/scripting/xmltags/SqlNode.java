package com.rwto.mybatis.scripting.xmltags;

/**
 * @author renmw
 * @create 2024/9/19 9:32
 **/
public interface SqlNode {

    boolean apply(DynamicContext context);
}
