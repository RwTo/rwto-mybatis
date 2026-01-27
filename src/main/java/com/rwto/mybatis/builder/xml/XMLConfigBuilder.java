package com.rwto.mybatis.builder.xml;

import com.rwto.mybatis.builder.BaseBuilder;
import com.rwto.mybatis.datasource.DataSourceFactory;
import com.rwto.mybatis.io.Resources;
import com.rwto.mybatis.mapping.BoundSql;
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
import com.rwto.mybatis.mapping.Environment;

import java.io.InputStream;
import java.io.Reader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author renmw
 * @since 2025/8/13 0:07
 **/
public class XMLConfigBuilder extends BaseBuilder {

    private final Element root;

    public XMLConfigBuilder(Reader reader) {
        super(new Configuration());

        SAXReader saxReader = new SAXReader();

        try {
            Document document = saxReader.read(new InputSource(reader));
            this.root = document.getRootElement();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 解析xml配置
     * @return
     */
    public Configuration parse(){
        try {
            // 环境
            environmentsElement(root.element("environments"));
            // 解析映射器
            mapperElement(root.element("mappers"));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
        }
        return configuration;
    }

    /**
     * 解析config文件下的environment信息
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
     * @param context
     */
    private void environmentsElement(Element context) throws Exception {
        // <environments default="development">
        String environment = context.attributeValue("default");

        // <environment id="development"> </environment>
        List<Element> environmentList = context.elements("environment");

        for (Element e : environmentList) {
            String id = e.attributeValue("id");
            // 加载启动的数据源
            if (environment.equals(id)) {
                // 事务管理器  <transactionManager type="JDBC">
                TransactionFactory txFactory = (TransactionFactory) typeAliasRegistry.resolveAlias(e.element("transactionManager").attributeValue("type")).newInstance();

                // 数据源 <dataSource type="DRUID">
                Element dataSourceElement = e.element("dataSource");
                DataSourceFactory dataSourceFactory = (DataSourceFactory) typeAliasRegistry.resolveAlias(dataSourceElement.attributeValue("type")).newInstance();
                // 填充连接数据库的属性
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


    /**
     * 1. 解析config 文件中的Mapper路径
     * 2. 根据路径解析对应的Mapper
     * @param mappers
     * @throws Exception
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
}
