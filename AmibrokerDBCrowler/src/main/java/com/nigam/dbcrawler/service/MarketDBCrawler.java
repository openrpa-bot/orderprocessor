package com.nigam.dbcrawler.service;

import com.nigam.dbcrawler.entity.AmibrokerGrafanaAlgoRecord;
import com.nigam.dbcrawler.processors.AmibrokerGrafanaAlgoRecordProcessor;
import com.nigam.dbcrawler.repository.DynamicAmibrokerReadOnlyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketDBCrawler {

    private static final Logger log = LoggerFactory.getLogger(MarketDBCrawler.class);

    @Autowired
    DynamicAmibrokerReadOnlyRepository repository;


    @Autowired
    AmibrokerGrafanaAlgoRecordProcessor recordProcessor;
    /**
     * Fetch all records from the table.
     *
     * @param tableName Table to query (must match Amibroker_Grafana_Algo*)
     */
    public List<AmibrokerGrafanaAlgoRecord> processAllRecords(String tableName) {


        List<AmibrokerGrafanaAlgoRecord> allRecords = repository.findAll(tableName);

        log.info("Fetched {} records from table {}", allRecords.size(), tableName);

        // Optional: iterate over each record
        for (AmibrokerGrafanaAlgoRecord record : allRecords) {
           // log.debug("Record: {}", record);
            recordProcessor.ProcessRecord(record);
        }

        return allRecords;
    }
}
