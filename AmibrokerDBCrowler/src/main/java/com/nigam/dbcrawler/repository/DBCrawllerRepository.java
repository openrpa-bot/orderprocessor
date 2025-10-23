package com.nigam.dbcrawler.repository;

import com.nigam.dbcrawler.entity.DBCrawller_ResistenceSupport;
import com.nigam.dbcrawler.entity.DBCrawller_ResistenceSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBCrawllerRepository extends JpaRepository<DBCrawller_ResistenceSupport, String> {

    // You can add custom queries later if needed, for example:
    // List<DBCrawller> findByTickerName(String tickerName);
    // List<DBCrawller> findByCandleDateBetween(Double start, Double end);

}
