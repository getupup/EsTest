package com.hbase.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HbaseTest {

    private HBaseAdmin admin = null;
    private static Configuration configuration;

    public static void main(String[] args){

        System.setProperty("hadoop.home.dir", "D:/soft/hadoop-common-2.2.0-bin-master");
        HbaseTest hbaseTest = new HbaseTest();
//        hbaseTest.getAllTable();
//        hbaseTest.creatTable("table2", "life");
//        hbaseTest.addData("table2");
        hbaseTest.getData("table2", "row2");
    }

    public HbaseTest(){

        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "127.0.0.1");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");

        try {
            admin = new HBaseAdmin(configuration);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //查看已有表
    public List getAllTable(){
        List<String> tables = null;
        if (admin != null){
            try {
                HTableDescriptor[] allTable = admin.listTables();
                if (allTable.length > 0){
                    tables = new ArrayList<String>();
                    for (HTableDescriptor hTableDescriptor : allTable){
                        tables.add(hTableDescriptor.getNameAsString());
                        System.out.println(hTableDescriptor.getNameAsString());
                    }
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        return tables;
    }

    //创建新表
    public void creatTable(String tableName, String cfName){
        try {

            if (admin.tableExists(tableName)){
                admin.disableTable(tableName);
                admin.deleteTable(tableName);
                System.out.println("开始建表...");
            }

            HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
            tableDescriptor.addFamily(new HColumnDescriptor(cfName));
            admin.createTable(tableDescriptor);

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //插入数据
    public void addData(String tableName){
        try {
            HTable table = new HTable(configuration, tableName);

            Put put1 = new Put("row1".getBytes());
            put1.addColumn("life".getBytes(), "school".getBytes(), "太原理工大学".getBytes());
            put1.addColumn("life".getBytes(), "work".getBytes(), "同程艺龙".getBytes());

            Put put2 = new Put("row2".getBytes());
            put2.addColumn("life".getBytes(), "girl".getBytes(), "ymj".getBytes());

            List<Put> list = new ArrayList<>();
            list.add(put1);
            list.add(put2);

//            table.put(put1);
//            table.put(put2);
            table.put(list);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    //查看数据
    public void getData(String tableName, String rowKey){
        try {
            Table table = new HTable(configuration, tableName);
            Get get = new Get(rowKey.getBytes());
            Result result = table.get(get);
            for (KeyValue keyValue : result.raw()){
                System.out.println("-----  CF:" + new String(keyValue.getFamily()) + "." +
                        new String(keyValue.getQualifier()) + " = " + new String(keyValue.getValue()));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
