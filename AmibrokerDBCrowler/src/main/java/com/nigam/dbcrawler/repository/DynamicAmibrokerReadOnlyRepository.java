package com.nigam.dbcrawler.repository;

import com.nigam.dbcrawler.entity.AmibrokerGrafanaAlgoRecord;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DynamicAmibrokerReadOnlyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Fetch all records from any Amibroker_Grafana_Algo* table
     *
     * @param tableName must match pattern Amibroker_Grafana_Algo*
     */
    public List<AmibrokerGrafanaAlgoRecord> findAll(String tableName) {

        // Validate table pattern
        if (!tableName.matches("Amibroker_Grafana_Algo.*")) {
            throw new IllegalArgumentException("Invalid table name: " + tableName);
        }

        String sql = "SELECT * FROM \"" + tableName + "\" ORDER BY \"candle_Date\" DESC";

        Query query = entityManager.createNativeQuery(sql, AmibrokerGrafanaAlgoRecord.class);
        return query.getResultList();
    }
}

