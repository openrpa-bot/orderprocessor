package com.nigam.dbcrawler.repository;

import com.nigam.dbcrawler.entity.ResistenceSupportRecord;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.annotations.Immutable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Immutable  // Marks repository read-only
public class DynamicResistenceSupportRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Fetch all records dynamically from a ResistenceSupport_* table.
     * Table must start with 'ResistenceSupport_' for safety.
     *
     * @param tableName the target table name (e.g. ResistenceSupport_Daily)
     * @return list of records mapped to ResistenceSupportRecord entity
     */
    public List<ResistenceSupportRecord> findAll(String tableName) {
        validateTableName(tableName);

        String sql = "SELECT * FROM \"" + tableName + "\" ORDER BY \"candle_Date\" DESC";
        Query query = entityManager.createNativeQuery(sql, ResistenceSupportRecord.class);
        return query.getResultList();
    }

    /**
     * Fetch all records for a specific ticker dynamically.
     */
    public List<ResistenceSupportRecord> findByTickerName(String tableName, String tickerName) {
        validateTableName(tableName);

        String sql = "SELECT * FROM \"" + tableName + "\" WHERE \"tickerName\" = :tickerName ORDER BY \"candle_Date\" DESC";
        Query query = entityManager.createNativeQuery(sql, ResistenceSupportRecord.class);
        query.setParameter("tickerName", tickerName);
        return query.getResultList();
    }

    /**
     * Validate table name to prevent SQL injection.
     */
    private void validateTableName(String tableName) {
        if (tableName == null || !tableName.matches("ResistenceSupport_.*")) {
            throw new IllegalArgumentException("Invalid table name: " + tableName);
        }
    }
}
