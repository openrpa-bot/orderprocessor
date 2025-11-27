package com.nigam.dbcrawler.service;

import com.nigam.dbcrawler.dto.ResistanceSupport;
import com.nigam.dbcrawler.entity.ResistenceSupportRecord;
import com.nigam.dbcrawler.entity.readonly.AgDay;
import com.nigam.dbcrawler.processors.AmibrokerGrafanaAlgoRecordProcessor;
import com.nigam.dbcrawler.repository.DynamicResistenceSupportRepository;
import com.nigam.dbcrawler.repository.readonly.AgDayReadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarketDBCrawler {

    private static final Logger log = LoggerFactory.getLogger(MarketDBCrawler.class);

    private final AgDayReadRepository agDayReadRepository;

    private final DynamicResistenceSupportRepository dynamicResistanceSupportRepository;

    @Autowired
    public MarketDBCrawler(AgDayReadRepository agDayReadRepository, DynamicResistenceSupportRepository dynamicResistanceSupportRepository) {
        this.agDayReadRepository = agDayReadRepository;
        this.dynamicResistanceSupportRepository = dynamicResistanceSupportRepository;
    }

    @Autowired
    AmibrokerGrafanaAlgoRecordProcessor recordProcessor;
    /**
     * Fetch all records from the table.
     *
     * @param tableName Table to query (must match ag_day*)
     */
    public List<AgDay> processAllRecords(String tableName) {

        // Fetch all required data tables
        List<AgDay> allRecords = agDayReadRepository.findAll(tableName);
        List<ResistenceSupportRecord> allQuarterly_ResistanceSupportRecord = dynamicResistanceSupportRepository.findAll("ResistenceSupport_Quarterly");
        List<ResistenceSupportRecord> allMonthly_ResistanceSupportRecord = dynamicResistanceSupportRepository.findAll("ResistenceSupport_Monthly");

        log.info("Fetched {} records from table {}", allRecords.size(), tableName);
        log.info("Fetched {} records from table {}", allQuarterly_ResistanceSupportRecord.size(), "ResistanceSupport_Quarterly");
        log.info("Fetched {} records from table {}", allMonthly_ResistanceSupportRecord.size(), "ResistanceSupport_Monthly");

        // Prepare final list of DTOs
        List<ResistanceSupport> dtoList_Quarterly = new ArrayList<>();
        for (ResistenceSupportRecord record : allQuarterly_ResistanceSupportRecord) {
            extracted(record, dtoList_Quarterly, "Q");
        }

        List<ResistanceSupport> dtoList_Monthly = new ArrayList<>();
        for (ResistenceSupportRecord record : allMonthly_ResistanceSupportRecord) {
            extracted(record, dtoList_Monthly, "M");
        }

        // Combine both lists
        List<ResistanceSupport> combinedList = new ArrayList<>();
        combinedList.addAll(dtoList_Quarterly);
        combinedList.addAll(dtoList_Monthly);

        // ✅ Sort by ResistanceSupport ascending (use reversed() for descending)
        dtoList_Quarterly = dtoList_Quarterly.stream()
                .sorted(Comparator.comparing(ResistanceSupport::getResistanceSupport, Comparator.nullsLast(Double::compareTo)))
                .collect(Collectors.toList());

        dtoList_Monthly = dtoList_Monthly.stream()
                .sorted(Comparator.comparing(ResistanceSupport::getResistanceSupport, Comparator.nullsLast(Double::compareTo)))
                .collect(Collectors.toList());

        // Sort the combined list by ResistanceSupport ascending (or descending with reversed())
        combinedList = combinedList.stream()
                .sorted(Comparator.comparing(ResistanceSupport::getResistanceSupport, Comparator.nullsLast(Double::compareTo)))
                .collect(Collectors.toList());

       // log.info("Sorted Quarterly DTO list by ResistanceSupport value");
        //log.info("Sorted Monthly DTO list by ResistanceSupport value");

        // Optional: process Amibroker records
        for (AgDay record : allRecords) {
            recordProcessor.ProcessRecord(record, combinedList, dtoList_Quarterly, dtoList_Monthly);
        }

        return allRecords; // or return the DTOs instead if you prefer
    }


    private static void extracted(ResistenceSupportRecord record, List<ResistanceSupport> dtoList , String period) {
        // Common reusable calculations
        Double volumeRatio = (record.getVolume() != null && record.getVsaVolumeMa() != null)
                ? record.getVolume() / record.getVsaVolumeMa()
                : null;

        Double spreadRatio = (record.getVsaSpreadMa() != null && record.getHigh() != null && record.getLow() != null)
                ? ((record.getHigh() - record.getLow())/ record.getVsaSpreadMa())
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
        dtoClose.setResistenceSupport_Comment(period + "C-Date(" + String.format("%.0f", record.getCandleDate()) + "),Vol("+String.format("%.2f", volumeRatio)+ "), Spread(" + String.format("%.2f", spreadRatio) + "),C-Pos(" + String.format("%.0f", spreadPosition) + ")");
        dtoList.add(dtoClose);

        // 2️⃣ Low Entry
        ResistanceSupport dtoLow = new ResistanceSupport();
        dtoLow.setEquityName(record.getTickerName());
        dtoLow.setCandleDate(record.getCandleDate());
        dtoLow.setResistanceSupport(record.getLow());
        dtoLow.setVolume_Ratio(volumeRatio);
        dtoLow.setSpread_Ratio(spreadRatio);
        dtoLow.setSpread_Position(spreadPosition);
        dtoLow.setResistenceSupport_Comment(period + "L-Date(" + String.format("%.0f", record.getCandleDate()) + "),Vol("+String.format("%.2f", volumeRatio)+ "), Spread(" + String.format("%.2f", spreadRatio) + "),C-Pos(" + String.format("%.0f", spreadPosition) + ")");
        dtoList.add(dtoLow);

        // 3️⃣ High Entry
        ResistanceSupport dtoHigh = new ResistanceSupport();
        dtoHigh.setEquityName(record.getTickerName());
        dtoHigh.setCandleDate(record.getCandleDate());
        dtoHigh.setResistanceSupport(record.getHigh());
        dtoHigh.setVolume_Ratio(volumeRatio);
        dtoHigh.setSpread_Ratio(spreadRatio);
        dtoHigh.setSpread_Position(spreadPosition);
        dtoHigh.setResistenceSupport_Comment(period + "H-Date(" + String.format("%.0f", record.getCandleDate()) + "),Vol("+String.format("%.2f", volumeRatio)+ "), Spread(" + String.format("%.2f", spreadRatio) + "),C-Pos(" + String.format("%.0f", spreadPosition) + ")");
        dtoList.add(dtoHigh);
    }
}
