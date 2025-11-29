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

    public List<AgDay> findBypk_field(String tableName, String pk_field) {
        String sql = "SELECT * FROM " + tableName + " WHERE pk_field = :pk";

        return em.createNativeQuery(sql, AgDay.class)
                .setParameter("pk", pk_field)
                .getResultList();
    }

    public List<AgDay> findByFilters(String tableName, String pk_field, Double candleDate) {
        String sql = "SELECT * FROM " + tableName +
                " WHERE pk_field = :pk AND candle_date = :cd";

        return em.createNativeQuery(sql, AgDay.class)
                .setParameter("pk", pk_field)
                .setParameter("cd", candleDate)
                .getResultList();
    }
}
