package com.nigam.dbcrawler.repository.impl;

import com.nigam.dbcrawler.entity.Final_Resistence_Support;
import com.nigam.dbcrawler.repository.FinalResistenceSupportDynamicRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FinalResistenceSupportDynamicRepositoryImpl
        implements FinalResistenceSupportDynamicRepository {

    private final JdbcTemplate jdbc;

    private final Map<String, Boolean> tableCreated = new ConcurrentHashMap<>();

    @PersistenceContext
    private EntityManager entityManager;

    private static final String CREATE_TABLE_TEMPLATE = """
        CREATE TABLE IF NOT EXISTS %s (
            pk_field VARCHAR NOT NULL,
            current_index DOUBLE PRECISION,
            candle_date DOUBLE PRECISION  NOT NULL,
            candle_datetime TIMESTAMP,
            ticker_name VARCHAR,
            candle_time DOUBLE PRECISION,
            long_1 DOUBLE PRECISION,
            long_2 DOUBLE PRECISION,
            sell_1 DOUBLE PRECISION,
            sell_2 DOUBLE PRECISION,
            long_sl_1 DOUBLE PRECISION,
            long_sl_2 DOUBLE PRECISION,
            short_1 DOUBLE PRECISION,
            short_2 DOUBLE PRECISION,
            cover_1 DOUBLE PRECISION,
            cover_2 DOUBLE PRECISION,
            short_sl_1 DOUBLE PRECISION,
            short_sl_2 DOUBLE PRECISION,
            PRIMARY KEY (pk_field,candle_date)
        )
        """;

    private String q(String table) {
        return "\"" + table.replace("\"", "") + "\"";
    }

    private synchronized void ensureTableExists(String tableName) {
        if (!tableCreated.containsKey(tableName)) {
            jdbc.execute(String.format(CREATE_TABLE_TEMPLATE, q(tableName)));
            tableCreated.put(tableName, true);
            log.info("Created table if not exists: {}", tableName);
        }
    }

    @Override
    public Final_Resistence_Support findById(String tableName, String pk_field, Double candleDate) {

        String sql = """
                SELECT * FROM %s
                WHERE pk_field = :pk_field
                  AND candle_Date = :candle_Date
                """.formatted(q(tableName));

        Query q = entityManager.createNativeQuery(sql, Final_Resistence_Support.class);
        q.setParameter("pk_field", pk_field);
        q.setParameter("candle_Date", candleDate);

        List<?> result = q.getResultList();
        return result.isEmpty() ? null : (Final_Resistence_Support) result.get(0);
    }

    @Override
    public List<Final_Resistence_Support> findBypk_field(String tableName, String pk_field) {

        String sql = """
                SELECT * FROM %s
                WHERE pk_field = :pk_field
                """.formatted(q(tableName));

        Query q = entityManager.createNativeQuery(sql, Final_Resistence_Support.class);
        q.setParameter("pk_field", pk_field);

        return q.getResultList();
    }

    @Override
    @Transactional
    public void upsert(String tableName, Final_Resistence_Support r) {

        ensureTableExists(tableName);

        String sql = """
                INSERT INTO %s
                (pk_field, current_index, candle_date, candle_datetime, ticker_name,
                 candle_time, long_1, long_2, sell_1, sell_2,
                 long_sl_1, long_sl_2, short_1, short_2,
                 cover_1, cover_2, short_sl_1, short_sl_2)
                VALUES
                (:pk_field, :currentIndex, :candleDate, :candleDateTime, :tickerName,
                 :candleTime, :long1, :long2, :sell1, :sell2,
                 :longSl1, :longSl2, :short1, :short2,
                 :cover1, :cover2, :shortSl1, :shortSl2)
                ON CONFLICT (pk_field, candle_date)
                DO UPDATE SET
                    candle_date = EXCLUDED.candle_date,
                    candle_datetime = EXCLUDED.candle_datetime,
                    ticker_name = EXCLUDED.ticker_name,
                    candle_time = EXCLUDED.candle_time,
                    long_1 = EXCLUDED.long_1,
                    long_2 = EXCLUDED.long_2,
                    sell_1 = EXCLUDED.sell_1,
                    sell_2 = EXCLUDED.sell_2,
                    long_sl_1 = EXCLUDED.long_sl_1,
                    long_sl_2 = EXCLUDED.long_sl_2,
                    short_1 = EXCLUDED.short_1,
                    short_2 = EXCLUDED.short_2,
                    cover_1 = EXCLUDED.cover_1,
                    cover_2 = EXCLUDED.cover_2,
                    short_sl_1 = EXCLUDED.short_sl_1,
                    short_sl_2 = EXCLUDED.short_sl_2
                """.formatted(q(tableName));

        Query q = entityManager.createNativeQuery(sql);

        q.setParameter("pk_field", r.getPk_field());
        q.setParameter("currentIndex", r.getCurrentIndex());
        q.setParameter("candleDate", r.getCandleDate());
        q.setParameter("candleDateTime", r.getCandleDateTime());
        q.setParameter("tickerName", r.getTickerName());
        q.setParameter("candleTime", r.getCandleTime());
        q.setParameter("long1", r.getLong_1());
        q.setParameter("long2", r.getLong_2());
        q.setParameter("sell1", r.getSell_1());
        q.setParameter("sell2", r.getSell_2());
        q.setParameter("longSl1", r.getLong_sl_1());
        q.setParameter("longSl2", r.getLong_sl_2());
        q.setParameter("short1", r.getShort_1());
        q.setParameter("short2", r.getShort_2());
        q.setParameter("cover1", r.getCover_1());
        q.setParameter("cover2", r.getCover_2());
        q.setParameter("shortSl1", r.getShort_sl_1());
        q.setParameter("shortSl2", r.getShort_sl_2());

        q.executeUpdate();
    }

    @Override
    public void delete(String tableName, String pk_field, Double candleDate) {

        String sql = """
                DELETE FROM %s
                WHERE pk_field = :pk_field
                  AND candle_date = :candleDate
                """.formatted(q(tableName));

        Query q = entityManager.createNativeQuery(sql);
        q.setParameter("pk_field", pk_field);
        q.setParameter("candleDate", candleDate);

        q.executeUpdate();
    }
}
