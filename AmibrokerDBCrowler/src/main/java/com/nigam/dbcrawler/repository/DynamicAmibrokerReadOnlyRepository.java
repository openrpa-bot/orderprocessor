package com.nigam.dbcrawler.repository;


import com.nigam.dbcrawler.entity.AmibrokerGrafanaAlgoRecord;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DynamicAmibrokerReadOnlyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Query any Amibroker_Grafana_Algo* table dynamically
     *
     * @param tableName must match pattern Amibroker_Grafana_Algo*
     */
    public Page<AmibrokerGrafanaAlgoRecord> findAll(
            String tableName,
            String tickerName,
            Double minDate,
            Double maxDate,
            Pageable pageable) {

        // Validate table pattern
        if (!tableName.matches("Amibroker_Grafana_Algo.*")) {
            throw new IllegalArgumentException("Invalid table name: " + tableName);
        }

        StringBuilder sql = new StringBuilder("SELECT * FROM \"" + tableName + "\" WHERE 1=1 ");
        if (tickerName != null) sql.append(" AND \"tickerName\" = :tickerName ");
        if (minDate != null) sql.append(" AND \"candle_Date\" >= :minDate ");
        if (maxDate != null) sql.append(" AND \"candle_Date\" <= :maxDate ");
        sql.append(" ORDER BY \"candle_Date\" DESC ");

        Query query = entityManager.createNativeQuery(sql.toString(), AmibrokerGrafanaAlgoRecord.class);
        if (tickerName != null) query.setParameter("tickerName", tickerName);
        if (minDate != null) query.setParameter("minDate", minDate);
        if (maxDate != null) query.setParameter("maxDate", maxDate);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<AmibrokerGrafanaAlgoRecord> content = query.getResultList();

        // Count total
        String countSql = sql.toString().replaceFirst("SELECT \\*", "SELECT COUNT(*)");
        Query countQuery = entityManager.createNativeQuery(countSql);
        if (tickerName != null) countQuery.setParameter("tickerName", tickerName);
        if (minDate != null) countQuery.setParameter("minDate", minDate);
        if (maxDate != null) countQuery.setParameter("maxDate", maxDate);
        Number total = ((Number) countQuery.getSingleResult());

        return new PageImpl<>(content, pageable, total.longValue());
    }
}
