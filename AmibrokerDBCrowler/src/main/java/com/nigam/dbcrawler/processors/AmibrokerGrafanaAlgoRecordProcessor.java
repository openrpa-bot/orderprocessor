package com.nigam.dbcrawler.processors;

import com.nigam.dbcrawler.entity.AmibrokerGrafanaAlgoRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AmibrokerGrafanaAlgoRecordProcessor {
    public void ProcessRecord(AmibrokerGrafanaAlgoRecord record) {
        // Implement your processing logic here
        log.info("Processing record with ID: {}", record.getPkField());
        // Add more processing as needed
    }
}
