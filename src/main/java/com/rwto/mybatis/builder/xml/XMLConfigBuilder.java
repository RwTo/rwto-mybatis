package com.rwto.mybatis.builder.xml;

import com.rwto.mybatis.builder.BaseBuilder;
import com.rwto.mybatis.datasource.DataSourceFactory;
import com.rwto.mybatis.io.Resources;
import com.rwto.mybatis.mapping.BoundSql;
import com.rwto.mybatis.mapping.Environment;
import com.rwto.mybatis.mapping.MappedStatement;
import com.rwto.mybatis.mapping.SqlCommandType;
import com.rwto.mybatis.session.Configuration;
import com.rwto.mybatis.transaction.TransactionFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author renmw
 * @create 2024/9/17 16:52
 **/
public class XMLConfigBuilder extends BaseBuilder {

    private Element root;

    public XMLConfigBuilder(Reader reader) {
        super(new Configuration());
        /*解析xml*/
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(new InputSource(reader));
            root = document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public Configuration parse() {
        try {
            // 解析 environment
            environmentsElement(root.element("environments"));
            // 解析mappers映射器
            mapperElement(root.element("mappers"));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
        }
        return configuration;
    }

    /**
     * <environments default="development">
     * <environment id="development">
     * <transactionManager type="JDBC">
     * <property name="..." value="..."/>
     * </transactionManager>
     * <dataSource type="POOLED">
     * <property name="driver" value="${driver}"/>
     * <property name="url" value="${url}"/>
     * <property name="username" value="${username}"/>
     * <property name="password" value="${password}"/>
     * </dataSource>
     * </environment>
     * </environments>
     */
    private void environmentsElement(Element context) throws Exception{
        String environment = context.attributeValue("default");

        List<Element> environmentList = context.elements("environment");
        for (Element e : environmentList) {
            String id = e.attributeValue("id");
            if (environment.equals(id)) {
                // 事务管理器
                TransactionFactory txFactory = (TransactionFactory) typeAliasRegistry.resolveAlias(e.element("transactionManager").attributeValue("type")).newInstance();

                // 数据源
                Element dataSourceElement = e.element("dataSource");
                DataSourceFactory dataSourceFactory = (DataSourceFactory) typeAliasRegistry.resolveAlias(dataSourceElement.attributeValue("type")).newInstance();
                List<Element> propertyList = dataSourceElement.elements("property");
                Properties props = new Properties();
                for (Element property : propertyList) {
                    props.setProperty(property.attributeValue("name"), property.attributeValue("value"));
                }
                dataSourceFactory.setProperties(props);
                DataSource dataSource = dataSourceFactory.getDataSource();

                // 构建环境
                Environment.Builder environmentBuilder = new Environment.Builder(id)
                        .transactionFactory(txFactory)
                        .dataSource(dataSource);

                configuration.setEnvironment(environmentBuilder.build());
            }
        }
    }

    /*
     * 这里解析的是 mybatis-config.xml
     * <mappers>
     *	 <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
     *	 <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
     *	 <mapper resource="org/mybatis/builder/PostMapper.xml"/>
     * </mappers>
     */
    private void mapperElement(Element mappers) throws Exception {
        List<Element> mapperList = mappers.elements("mapper");
        for (Element e : mapperList) {
            String resource = e.attributeValue("resource");
            InputStream inputStream = Resources.getResourceAsStream(resource);

            // 在for循环里每个mapper都重新new一个XMLMapperBuilder，来解析
            XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, configuration, resource);
            mapperParser.parse();
        }
    }


    //    private void mapperElement(Element mappers) throws Exception {
//        List<Element> mapperList = mappers.elements("mapper");
//        for (Element e : mapperList) {
//            String resource = e.attributeValue("resource");
//            Reader reader = Resources.getResourceAsReader(resource);
//            SAXReader saxReader = new SAXReader();
//            Document document = saxReader.read(new InputSource(reader));
//            Element root = document.getRootElement();
//            //命名空间
//            String namespace = root.attributeValue("namespace");
//
//            // SELECT
//            List<Element> selectNodes = root.elements("select");
//            for (Element node : selectNodes) {
//                String id = node.attributeValue("id");
//                String parameterType = node.attributeValue("parameterType");
//                String resultType = node.attributeValue("resultType");
//                String sql = node.getText();
//
//                // ? 匹配
//                Map<Integer, String> parameter = new HashMap<>();
//                Pattern pattern = Pattern.compile("(#\\{(.*?)})");
//                Matcher matcher = pattern.matcher(sql);
//                for (int i = 1; matcher.find(); i++) {
//                    String g1 = matcher.group(1);
//                    String g2 = matcher.group(2);
//                    parameter.put(i, g2);
//                    /*把#{id} 替换为 ?*/
//                    sql = sql.replace(g1, "?");
//                }
//
//                String msId = namespace + "." + id;
//                String nodeName = node.getName();
//                SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
//
//                BoundSql boundSql = new BoundSql(sql, parameter, parameterType, resultType);
//
//                MappedStatement mappedStatement = new MappedStatement.Builder(configuration, msId, sqlCommandType, boundSql).build();
//                // 添加解析 SQL
//                configuration.addMappedStatement(mappedStatement);
//            }
//
//            // 注册Mapper映射器
//            configuration.addMapper(Resources.classForName(namespace));
//        }
//    }

}
