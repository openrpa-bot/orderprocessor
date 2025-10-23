package com.nigam.dbcrawler.processors;

import com.nigam.dbcrawler.dto.ResistanceSupport;
import com.nigam.dbcrawler.entity.AmibrokerGrafanaAlgoRecord;
import com.nigam.dbcrawler.entity.DBCrawller_ResistenceSupport;
import com.nigam.dbcrawler.entity.ResistenceSupportRecord;
import com.nigam.dbcrawler.repository.DBCrawllerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AmibrokerGrafanaAlgoRecordProcessor {

    @Autowired
    DBCrawllerRepository dbCrawllerRepository;

    public void ProcessRecord(AmibrokerGrafanaAlgoRecord record,
                              List<ResistanceSupport> combinedList,
                              List<ResistanceSupport> dtoListQuarterly,
                              List<ResistanceSupport> dtoListMonthly) {

        Double closePrice = record.getClose();
        String tickerName = record.getTickerName();
        Double candleDate = record.getCandleDate();

        List<ResistanceSupport> trendlineResistenceSupport = new ArrayList<ResistanceSupport>();

        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Quarterly TL@" + record.getDataQuarterlyTrendline());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Monthly TL@" + record.getDataMonthlyTrendline());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Weekly TL@" + record.getDataWeeklyTrendline());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Daily TL@" + record.getDataDailyTrendline());

        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Quarterly Previous TL@" + record.getDataDailyPreviousTrendline());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Monthly Previous TL@" + record.getDataMonthlyPreviousTrendline());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Weekly Previous TL@" + record.getDataWeeklyPreviousTrendline());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Daily Previous TL@" + record.getDataDailyPreviousTrendline());

        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Quarterly TL OHCL@" + record.getDataQuarterlyTrendlineOhlc());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Monthly TL OHCL@" + record.getDataMonthlyTrendlineOhlc());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Weekly TL OHCL@" + record.getDataWeeklyTrendlineOhlc());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Daily TL OHCL@" + record.getDataDailyTrendlineOhlc());

        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Quarterly Previous TL OHCL@" + record.getDataQuarterlyPreviousTrendlineOhlc());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Monthly Previous TL OHCL@" + record.getDataMonthlyPreviousTrendlineOhlc());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Weekly Previous TL OHCL@" + record.getDataWeeklyPreviousTrendlineOhlc());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Daily Previous TL OHCL@" + record.getDataDailyPreviousTrendlineOhlc());

        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Quarterly TL Middle@" + record.getDataQuarterlyTrendlineMiddle());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Monthly TL  Middle@" + record.getDataMonthlyTrendlineMiddle());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Weekly TL  Middle@" + record.getDataWeeklyTrendlineMiddle());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getDataDailyTrendline() ,"Daily TL  Middle@" + record.getDataDailyTrendlineMiddle());

        trendlineResistenceSupport.addAll(combinedList);

        List<ResistanceSupport> closestSupport = trendlineResistenceSupport.stream()
                .filter(rs -> rs.getEquityName().equals(tickerName)
                        && rs.getResistanceSupport() < closePrice)
                .sorted((rs1, rs2) -> {
                    return rs2.getResistanceSupport().compareTo(rs1.getResistanceSupport());
                })
                .limit(4)
                .toList();

        List<ResistanceSupport> closestResistence = trendlineResistenceSupport.stream()
                .filter(rs -> rs.getEquityName().equals(tickerName)
                        && rs.getResistanceSupport() >= closePrice)
                .sorted((rs1, rs2) -> {
                    return rs1.getResistanceSupport().compareTo(rs2.getResistanceSupport());
                })
                .limit(4)
                .toList();

        DBCrawller_ResistenceSupport dbCrawller_ResistenceSupport = new DBCrawller_ResistenceSupport();

        dbCrawller_ResistenceSupport.setPkField(record.getPkField());
        dbCrawller_ResistenceSupport.setCurrentIndex(record.getCurrentIndex());
        dbCrawller_ResistenceSupport.setCandleDate(record.getCandleDate());
        dbCrawller_ResistenceSupport.setCandleDateTime(record.getCandleDateTime());
        dbCrawller_ResistenceSupport.setTickerName(record.getTickerName());
        dbCrawller_ResistenceSupport.setCandleTime(record.getCandleTime());
        if(closestResistence.size() > 0) {
            dbCrawller_ResistenceSupport.setResistence1(closestResistence.get(0).getResistanceSupport());
            dbCrawller_ResistenceSupport.setResistenceName1(closestResistence.get(0).getResistenceSupport_Comment());
        }
        if(closestResistence.size() > 1) {
            dbCrawller_ResistenceSupport.setResistence2(closestResistence.get(1).getResistanceSupport());
            dbCrawller_ResistenceSupport.setResistenceName2(closestResistence.get(1).getResistenceSupport_Comment());
        }
        if(closestResistence.size() > 2) {
            dbCrawller_ResistenceSupport.setResistence3(closestResistence.get(2).getResistanceSupport());
            dbCrawller_ResistenceSupport.setResistenceName3(closestResistence.get(2).getResistenceSupport_Comment());
        }
        if(closestResistence.size() > 3) {
            dbCrawller_ResistenceSupport.setResistence4(closestResistence.get(3).getResistanceSupport());
            dbCrawller_ResistenceSupport.setResistenceName4(closestResistence.get(3).getResistenceSupport_Comment());
        }
        if(closestSupport.size() > 0) {
            dbCrawller_ResistenceSupport.setSupport1(closestSupport.get(0).getResistanceSupport());
            dbCrawller_ResistenceSupport.setSupportName1(closestSupport.get(0).getResistenceSupport_Comment());
        }
        if(closestSupport.size() > 1) {
            dbCrawller_ResistenceSupport.setSupport2(closestSupport.get(1).getResistanceSupport());
            dbCrawller_ResistenceSupport.setSupportName2(closestSupport.get(1).getResistenceSupport_Comment());
        }
        if(closestSupport.size() > 2) {
            dbCrawller_ResistenceSupport.setSupport3(closestSupport.get(2).getResistanceSupport());
            dbCrawller_ResistenceSupport.setSupportName3(closestSupport.get(2).getResistenceSupport_Comment());
        }
        if(closestSupport.size() > 3) {
            dbCrawller_ResistenceSupport.setSupport4(closestSupport.get(3).getResistanceSupport());
            dbCrawller_ResistenceSupport.setSupportName4(closestSupport.get(3).getResistenceSupport_Comment());
        }

        dbCrawllerRepository.save(dbCrawller_ResistenceSupport);
    }

    private void getResistenceSupportList(AmibrokerGrafanaAlgoRecord record, List<ResistanceSupport> trendlineResistenceSupport, Double resestanceSupport, String comment) {

        trendlineResistenceSupport.add(new ResistanceSupport(
                record.getTickerName(),
                record.getCandleDate(),
                resestanceSupport,
                record.getVolume()/ record.getVsaVolumeMa(),
                record.getHigh()- record.getLow()/ record.getVsaSpreadMa(),
                record.getHigh()- record.getLow() != 0 ? (record.getClose() - record.getLow()) / (record.getHigh() - record.getLow()) : null,
                comment
        ));
    }
}
