package com.nigam.dbcrawler.processors;

import com.nigam.dbcrawler.dto.ResistanceSupport;
import com.nigam.dbcrawler.entity.AmibrokerGrafanaAlgoRecord;
import com.nigam.dbcrawler.entity.ResistenceSupportRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AmibrokerGrafanaAlgoRecordProcessor {

    public void ProcessRecord(AmibrokerGrafanaAlgoRecord record,
                              List<ResistanceSupport> combinedList,
                              List<ResistanceSupport> dtoListQuarterly,
                              List<ResistanceSupport> dtoListMonthly) {

        Double closePrice = record.getClose();
        String tickerName = record.getTickerName();
        Double candleDate = record.getCandleDate();

        List<ResistanceSupport> closestSupport = combinedList.stream()
                .filter(rs -> rs.getEquityName().equals(tickerName)
                        && rs.getResistanceSupport() < closePrice)
                .sorted((rs1, rs2) -> {
                    return rs2.getResistanceSupport().compareTo(rs1.getResistanceSupport());
                })
                .limit(4)
                .toList();

        List<ResistanceSupport> closestResistence = combinedList.stream()
                .filter(rs -> rs.getEquityName().equals(tickerName)
                        && rs.getResistanceSupport() >= closePrice)
                .sorted((rs1, rs2) -> {
                    return rs1.getResistanceSupport().compareTo(rs2.getResistanceSupport());
                })
                .limit(4)
                .toList();

        for (ResistanceSupport rs : closestResistence) {
            if (rs.getResistenceSupport_Comment().endsWith("Q")) {
                log.info("Quarterly Resistance/Support found for {} on date {}: {}, Close Price: {}",
                        tickerName, candleDate, rs.getResistanceSupport(), closePrice);
                dtoListQuarterly.add(rs);
            } else if (rs.getResistenceSupport_Comment().endsWith("M")) {
                log.info("Monthly Resistance/Support found for {} on date {}: {}, Close Price: {}",
                        tickerName, candleDate, rs.getResistanceSupport(), closePrice);
                dtoListMonthly.add(rs);
            }
        }
    }
}
