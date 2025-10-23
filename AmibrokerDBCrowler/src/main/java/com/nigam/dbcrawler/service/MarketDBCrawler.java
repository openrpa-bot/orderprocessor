package com.nigam.dbcrawler.service;

import com.nigam.dbcrawler.dto.ResistanceSupport;
import com.nigam.dbcrawler.entity.AmibrokerGrafanaAlgoRecord;
import com.nigam.dbcrawler.entity.ResistenceSupportRecord;
import com.nigam.dbcrawler.processors.AmibrokerGrafanaAlgoRecordProcessor;
import com.nigam.dbcrawler.repository.DynamicAmibrokerReadOnlyRepository;
import com.nigam.dbcrawler.repository.DynamicResistenceSupportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarketDBCrawler {

    private static final Logger log = LoggerFactory.getLogger(MarketDBCrawler.class);

    private final DynamicAmibrokerReadOnlyRepository dynamicAmibrokerReadOnlyRepository;

    private final DynamicResistenceSupportRepository dynamicResistanceSupportRepository;

    @Autowired
    public MarketDBCrawler(DynamicAmibrokerReadOnlyRepository dynamicAmibrokerReadOnlyRepository, DynamicResistenceSupportRepository dynamicResistanceSupportRepository) {
        this.dynamicAmibrokerReadOnlyRepository = dynamicAmibrokerReadOnlyRepository;
        this.dynamicResistanceSupportRepository = dynamicResistanceSupportRepository;
    }

    @Autowired
    AmibrokerGrafanaAlgoRecordProcessor recordProcessor;
    /**
     * Fetch all records from the table.
     *
     * @param tableName Table to query (must match Amibroker_Grafana_Algo*)
     */
    public List<AmibrokerGrafanaAlgoRecord> processAllRecords(String tableName) {

        // Fetch all required data tables
        List<AmibrokerGrafanaAlgoRecord> allRecords = dynamicAmibrokerReadOnlyRepository.findAll(tableName);
        List<ResistenceSupportRecord> allQuarterly_ResistanceSupportRecord = dynamicResistanceSupportRepository.findAll("ResistenceSupport_Quarterly");
        List<ResistenceSupportRecord> allMonthly_ResistanceSupportRecord = dynamicResistanceSupportRepository.findAll("ResistenceSupport_Monthly");
        //List<ResistenceSupportRecord> allWeekly_ResistanceSupportRecord = dynamicResistanceSupportRepository.findAll("ResistenceSupport_Weekly");
        //List<ResistenceSupportRecord> allDaily_ResistanceSupportRecord = dynamicResistanceSupportRepository.findAll("ResistenceSupport_Daily");

        log.info("Fetched {} records from table {}", allRecords.size(), tableName);
        log.info("Fetched {} records from table {}", allQuarterly_ResistanceSupportRecord.size(), "ResistanceSupport_Quarterly");
        log.info("Fetched {} records from table {}", allMonthly_ResistanceSupportRecord.size(), "ResistanceSupport_Monthly");
        //log.info("Fetched {} records from table {}", allWeekly_ResistanceSupportRecord.size(), "ResistanceSupport_Weekly");
        //log.info("Fetched {} records from table {}", allDaily_ResistanceSupportRecord.size(), "ResistanceSupport_Daily");

        // Prepare final list of DTOs
        List<ResistanceSupport> dtoList_Quarterly = new ArrayList<>();

        // Create 3 DTO entries (Close, Low, High) for each quarterly record
        for (ResistenceSupportRecord record : allQuarterly_ResistanceSupportRecord) {
            extracted(record, dtoList_Quarterly, "Q");
        }

        // Prepare final list of DTOs
        List<ResistanceSupport> dtoList_Monthly = new ArrayList<>();

        // Create 3 DTO entries (Close, Low, High) for each quarterly record
        for (ResistenceSupportRecord record : allMonthly_ResistanceSupportRecord) {
            extracted(record, dtoList_Monthly, "M");
        }


        log.info("Total DTO entries created for Quarterly data: {}", dtoList_Quarterly.size());
        log.info("Total DTO entries created for Monthly data: {}", dtoList_Monthly.size());

        // Optional: process Amibroker records
        for (AmibrokerGrafanaAlgoRecord record : allRecords) {
            recordProcessor.ProcessRecord(record,
                    dtoList_Quarterly,
                    dtoList_Monthly);
        }

        return allRecords; // Or return dtoList if you want to return the DTOs instead
    }

    private static void extracted(ResistenceSupportRecord record, List<ResistanceSupport> dtoList , String period) {
        // Common reusable calculations
        Double volumeRatio = (record.getVolume() != null && record.getVsaVolumeMa() != null)
                ? record.getVolume() / record.getVsaVolumeMa()
                : null;

        Double spreadRatio = (record.getVsaSpreadMa() != null && record.getHigh() != null && record.getLow() != null)
                ? ((record.getHigh() - record.getLow()) * 100 / record.getVsaSpreadMa())
                : null;

        Double spreadPosition = (record.getHigh() != null && record.getLow() != null && record.getClose() != null)
                ? (record.getClose() - record.getLow())*100 / (record.getHigh() - record.getLow())
                : null;

        // 1️⃣ Close Entry
        ResistanceSupport dtoClose = new ResistanceSupport();
        dtoClose.setEquityName(record.getTickerName());
        dtoClose.setCandleDate(record.getCandleDate());
        dtoClose.setResistanceSupport(record.getClose());
        dtoClose.setVolume_Ratio(volumeRatio);
        dtoClose.setSpread_Ratio(spreadRatio);
        dtoClose.setSpread_Position(spreadPosition);
        dtoClose.setResistenceSupport_Comment(period + "Close@" + spreadPosition);
        dtoList.add(dtoClose);

        // 2️⃣ Low Entry
        ResistanceSupport dtoLow = new ResistanceSupport();
        dtoLow.setEquityName(record.getTickerName());
        dtoLow.setCandleDate(record.getCandleDate());
        dtoLow.setResistanceSupport(record.getLow());
        dtoLow.setVolume_Ratio(volumeRatio);
        dtoLow.setSpread_Ratio(spreadRatio);
        dtoLow.setSpread_Position(spreadPosition);
        dtoLow.setResistenceSupport_Comment(period + "Low" + spreadPosition);
        dtoList.add(dtoLow);

        // 3️⃣ High Entry
        ResistanceSupport dtoHigh = new ResistanceSupport();
        dtoHigh.setEquityName(record.getTickerName());
        dtoHigh.setCandleDate(record.getCandleDate());
        dtoHigh.setResistanceSupport(record.getHigh());
        dtoHigh.setVolume_Ratio(volumeRatio);
        dtoHigh.setSpread_Ratio(spreadRatio);
        dtoHigh.setSpread_Position(spreadPosition);
        dtoHigh.setResistenceSupport_Comment(period + "High" + spreadPosition);
        dtoList.add(dtoHigh);
    }
}
