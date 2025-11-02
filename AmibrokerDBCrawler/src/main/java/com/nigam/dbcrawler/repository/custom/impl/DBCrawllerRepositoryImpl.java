package com.nigam.dbcrawler.repository.custom.impl;

import com.nigam.dbcrawler.entity.DBCrawller_ResistenceSupport;
import com.nigam.dbcrawler.entity.DBCrawllerId;
import com.nigam.dbcrawler.repository.custom.DBCrawllerRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class DBCrawllerRepositoryImpl implements DBCrawllerRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void upsert(DBCrawller_ResistenceSupport entity) {
        DBCrawllerId id = new DBCrawllerId(entity.getPkField(), entity.getCurrentIndex());

        // Check if record exists
        DBCrawller_ResistenceSupport existing = entityManager.find(DBCrawller_ResistenceSupport.class, id);

        if (existing != null) {
            // Merge fields you want to update (optional fine-tuning)
            entityManager.merge(entity);
        } else {
            // Persist new entity
            entityManager.persist(entity);
        }
    }
}
