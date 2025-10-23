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


    public void ProcessRecord(AmibrokerGrafanaAlgoRecord record, List<ResistanceSupport> dtoListQuarterly, List<ResistanceSupport> dtoListMonthly) {

    }
}
