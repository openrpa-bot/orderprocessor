package com.nigam.dbcrawler.processors;

import com.nigam.dbcrawler.dto.ResistanceSupport;
import com.nigam.dbcrawler.entity.DBCrawller_ResistenceSupport;
import com.nigam.dbcrawler.entity.Final_Resistence_Support;
import com.nigam.dbcrawler.entity.readonly.AgDay;
import com.nigam.dbcrawler.repository.DBCrawllerRepository;
import com.nigam.dbcrawler.repository.FinalResistenceSupportDynamicRepository;
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

    @Autowired
    FinalResistenceSupportDynamicRepository finalResistenceSupportDynamicRepository;

    public void ProcessRecord(AgDay record,
                              List<ResistanceSupport> combinedList,
                              List<ResistanceSupport> dtoListQuarterly,
                              List<ResistanceSupport> dtoListMonthly) {

        Double closePrice = record.getClose();
        String tickerName = record.getEquityName();
        Double candleDate = record.getCandleDate();

        List<ResistanceSupport> trendlineResistenceSupport = new ArrayList<ResistanceSupport>();

        getResistenceSupportList(record, trendlineResistenceSupport,record.getQuarterlyTl() ,"Quarterly TL@" + record.getQuarterlyTl());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getMonthlyTl() ,"Monthly TL@" + record.getMonthlyTl());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getWeeklyTl() ,"Weekly TL@" + record.getWeeklyTl());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getDailyTl() ,"Daily TL@" + record.getDailyTl());

        //getResistenceSupportList(record, trendlineResistenceSupport,record.getQuarterlyPTl() ,"Quarterly Previous TL@" + record.getQuarterlyPTl());
        //getResistenceSupportList(record, trendlineResistenceSupport,record.getMonthlyPTl() ,"Monthly Previous TL@" + record.getMonthlyPTl());
        //getResistenceSupportList(record, trendlineResistenceSupport,record.getWeeklyPTl() ,"Weekly Previous TL@" + record.getWeeklyPTl());
        //getResistenceSupportList(record, trendlineResistenceSupport,record.getDailyPTl() ,"Daily Previous TL@" + record.getDailyPTl());

        getResistenceSupportList(record, trendlineResistenceSupport,record.getQuarterlyTlOhlc() ,"Quarterly TL OHCL@" + record.getQuarterlyTlOhlc());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getMonthlyTlOhlc() ,"Monthly TL OHCL@" + record.getMonthlyTlOhlc());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getWeeklyTlOhlc() ,"Weekly TL OHCL@" + record.getWeeklyTlOhlc());
        getResistenceSupportList(record, trendlineResistenceSupport,record.getDailyTlOhlc() ,"Daily TL OHCL@" + record.getDailyTlOhlc());

        //getResistenceSupportList(record, trendlineResistenceSupport,record.getQuarterlyPTlOhlc() ,"Quarterly Previous TL OHCL@" + record.getQuarterlyPTlOhlc());
        //getResistenceSupportList(record, trendlineResistenceSupport,record.getMonthlyPTlOhlc() ,"Monthly Previous TL OHCL@" + record.getMonthlyPTlOhlc());
        //getResistenceSupportList(record, trendlineResistenceSupport,record.getWeeklyPTlOhlc() ,"Weekly Previous TL OHCL@" + record.getWeeklyPTlOhlc());
        //getResistenceSupportList(record, trendlineResistenceSupport,record.getDailyPTlOhlc() ,"Daily Previous TL OHCL@" + record.getDailyPTlOhlc());

        //getResistenceSupportList(record, trendlineResistenceSupport,record.getQuarterlyTlMiddle() ,"Quarterly TL Middle@" + record.getQuarterlyTlMiddle());
        //getResistenceSupportList(record, trendlineResistenceSupport,record.getMonthlyTlMiddle() ,"Monthly TL  Middle@" + record.getMonthlyTlMiddle());
        //getResistenceSupportList(record, trendlineResistenceSupport,record.getWeeklyTlMiddle() ,"Weekly TL  Middle@" + record.getWeeklyTlMiddle());
        //getResistenceSupportList(record, trendlineResistenceSupport,record.getDailyTlMiddle() ,"Daily TL  Middle@" + record.getDailyTlMiddle());

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
        Final_Resistence_Support final_Resistence_Support = new Final_Resistence_Support();

        dbCrawller_ResistenceSupport.setPk_field(record.getPk_field());
        dbCrawller_ResistenceSupport.setCurrentIndex(record.getInnerIndex());
        dbCrawller_ResistenceSupport.setCandleDate(record.getCandleDate());
        dbCrawller_ResistenceSupport.setCandleDateTime(record.getCandleDatetime());
        dbCrawller_ResistenceSupport.setTickerName(record.getEquityName());
        dbCrawller_ResistenceSupport.setCandleTime(record.getCandleTime());
        dbCrawller_ResistenceSupport.setClose(record.getClose());

        final_Resistence_Support.setPk_field(record.getPk_field());
        final_Resistence_Support.setCurrentIndex(record.getInnerIndex());
        final_Resistence_Support.setCandleDate(record.getCandleDate());
        final_Resistence_Support.setCandleDateTime(record.getCandleDatetime());
        final_Resistence_Support.setTickerName(record.getEquityName());
        final_Resistence_Support.setCandleTime(record.getCandleTime());

        if(closestResistence.size() > 0) {
            dbCrawller_ResistenceSupport.setResistence1(closestResistence.get(0).getResistanceSupport());
            dbCrawller_ResistenceSupport.setResistenceName1(closestResistence.get(0).getResistenceSupport_Comment());
            final_Resistence_Support.setSell_1(closestResistence.get(0).getResistanceSupport());
            final_Resistence_Support.setShort_1(closestResistence.get(0).getResistanceSupport());
        }
        if(closestResistence.size() > 1) {
            dbCrawller_ResistenceSupport.setResistence2(closestResistence.get(1).getResistanceSupport());
            dbCrawller_ResistenceSupport.setResistenceName2(closestResistence.get(1).getResistenceSupport_Comment());
            final_Resistence_Support.setSell_2(closestResistence.get(1).getResistanceSupport());
            final_Resistence_Support.setShort_2(closestResistence.get(1).getResistanceSupport());
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
            final_Resistence_Support.setLong_1(closestSupport.get(0).getResistanceSupport());
            final_Resistence_Support.setCover_1(closestSupport.get(0).getResistanceSupport());
        }
        if(closestSupport.size() > 1) {
            dbCrawller_ResistenceSupport.setSupport2(closestSupport.get(1).getResistanceSupport());
            dbCrawller_ResistenceSupport.setSupportName2(closestSupport.get(1).getResistenceSupport_Comment());
            final_Resistence_Support.setLong_2(closestSupport.get(1).getResistanceSupport());
            final_Resistence_Support.setCover_2(closestSupport.get(1).getResistanceSupport());
        }
        if(closestSupport.size() > 2) {
            dbCrawller_ResistenceSupport.setSupport3(closestSupport.get(2).getResistanceSupport());
            dbCrawller_ResistenceSupport.setSupportName3(closestSupport.get(2).getResistenceSupport_Comment());
        }
        if(closestSupport.size() > 3) {
            dbCrawller_ResistenceSupport.setSupport4(closestSupport.get(3).getResistanceSupport());
            dbCrawller_ResistenceSupport.setSupportName4(closestSupport.get(3).getResistenceSupport_Comment());
        }

        finalResistenceSupportDynamicRepository.upsert("op_resistence_support_algo1", final_Resistence_Support);
        dbCrawllerRepository.upsert(dbCrawller_ResistenceSupport);
    }

    private void getResistenceSupportList(AgDay record, List<ResistanceSupport> trendlineResistenceSupport, Double resestanceSupport, String comment) {

        trendlineResistenceSupport.add(new ResistanceSupport(
                record.getEquityName(),
                record.getCandleDate(),
                resestanceSupport,
                record.getVolume()/ record.getMaVsaVolume(),
                record.getHigh()- record.getLow()/ record.getMaVsaSpreed(),
                record.getHigh()- record.getLow() != 0 ? (record.getClose() - record.getLow()) / (record.getHigh() - record.getLow()) : null,
                comment
        ));
    }
}
