package com.nigam.dbcrawler.repository;

import com.nigam.dbcrawler.entity.Final_Resistence_Support;

import java.util.List;

public interface FinalResistenceSupportDynamicRepository {

    Final_Resistence_Support findById(String tableName, String pkField, Double candleDate);

    List<Final_Resistence_Support> findByPkField(String tableName, String pkField);

    void upsert(String tableName, Final_Resistence_Support row);

    void delete(String tableName, String pkField, Double candleDate);

}

