package com.es.test;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class EsTest {

    private Logger logger = LoggerFactory.getLogger(EsTest.class);
    public final static String HOST = "10.161.16.21";
    public final static Integer PORT = 9300;
    private TransportClient client = null;

    @Before
    public void getConnect() throws UnknownHostException {

        //设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "test").build();
        //创建客户端
        client = new PreBuiltTransportClient(settings).addTransportAddress(
                new TransportAddress(InetAddress.getByName(HOST), PORT));

        logger.info("连接信息:" + client.toString());

        }

    @After
    public void closeConnect(){
        if (null != client){
            logger.info("关闭连接...");
            client.close();
        }
    }

    //增加索引
    @Test
    public void addIndex() throws IOException {
        IndexResponse response = client.prepareIndex("test", "action", "1")
                .setSource(XContentFactory.jsonBuilder()
                .startObject()
                .field("username", "张三")
                .field("sendDate", new Date())
                .field("msg", "你好李四")
                .endObject()).get();

        logger.info("索引名称:" + response.getIndex() + "\n        类型:" + response.getType()
                    + "\n        文档ID:" + response.getId() + "\n        实例状态:" + response.status());
    }

    //查看数据
    @Test
    public void getDate(){
        GetResponse getResponse = client.prepareGet("people", "man", "1").get();
        logger.info("取出的数据:" + getResponse.getSourceAsString());
    }

}
