package com.rwto.mybatis.scripting.xmltags;

/**
 * @author renmw
 * @since 2025/1/26 19:40
 **/
public interface SqlNode {

    boolean apply(DynamicContext context);

}
