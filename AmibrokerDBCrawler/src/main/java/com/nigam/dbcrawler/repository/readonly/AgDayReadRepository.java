package com.nigam.dbcrawler.repository.readonly;

import com.nigam.dbcrawler.entity.readonly.AgDay;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AgDayReadRepository {

    @PersistenceContext
    private EntityManager em;

    public List<AgDay> findAll(String tableName) {
        String sql = "SELECT * FROM " + tableName;

        return em.createNativeQuery(sql, AgDay.class)
                .getResultList();
    }

    public List<AgDay> findByPkField(String tableName, String pkField) {
        String sql = "SELECT * FROM " + tableName + " WHERE pk_field = :pk";

        return em.createNativeQuery(sql, AgDay.class)
                .setParameter("pk", pkField)
                .getResultList();
    }

    public List<AgDay> findByFilters(String tableName, String pkField, Double candleDate) {
        String sql = "SELECT * FROM " + tableName +
                " WHERE pk_field = :pk AND candle_date = :cd";

        return em.createNativeQuery(sql, AgDay.class)
                .setParameter("pk", pkField)
                .setParameter("cd", candleDate)
                .getResultList();
    }
}
