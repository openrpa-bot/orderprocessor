package com.nigam.dbcrawler.repository.custom;

import com.nigam.dbcrawler.entity.DBCrawller_ResistenceSupport;

public interface DBCrawllerRepositoryCustom {
    void upsert(DBCrawller_ResistenceSupport entity);
}
