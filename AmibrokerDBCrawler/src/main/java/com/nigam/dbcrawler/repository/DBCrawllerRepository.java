package com.nigam.dbcrawler.repository;

import com.nigam.dbcrawler.entity.DBCrawller_ResistenceSupport;
import com.nigam.dbcrawler.entity.DBCrawllerId;
import com.nigam.dbcrawler.repository.custom.DBCrawllerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBCrawllerRepository extends JpaRepository<DBCrawller_ResistenceSupport, DBCrawllerId>, DBCrawllerRepositoryCustom {
}
