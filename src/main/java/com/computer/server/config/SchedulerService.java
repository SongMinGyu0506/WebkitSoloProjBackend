package com.computer.server.config;

import com.computer.server.repository.ComputerRepository;
import com.computer.server.service.crawling.Crawling;
import com.computer.server.service.crawling.CrawlingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//@Service
@Slf4j
public class SchedulerService {

    @Autowired
    private CrawlingService crawlingService;

    @Scheduled(fixedDelay = 3600000)
    @Async
    public void autoCrawling() {
        log.info("Crawling start");
        crawlingService.crawling_init();
        crawlingService.danawa_save("SSD","2.5SSD");
        crawlingService.danawa_save("VGA","GTX1650");
        crawlingService.danawa_save("VGA","GTX1660");
        crawlingService.danawa_save("VGA","RTX3060");
        crawlingService.danawa_save("VGA","RTX3070");
        crawlingService.danawa_save("VGA","RTX3080");
        crawlingService.danawa_save("VGA","RTX3090");
        crawlingService.danawa_save("VGA","RTX3080");
        crawlingService.danawa_save("VGA","RX6600");
        crawlingService.danawa_save("VGA","RX6700");
        crawlingService.danawa_save("VGA","RX6800");
        crawlingService.danawa_save("CPU","i3");
        crawlingService.danawa_save("CPU","i5");
        crawlingService.danawa_save("CPU","i7");
        crawlingService.danawa_save("CPU","ryzen3");
        crawlingService.danawa_save("CPU","ryzen5");
        crawlingService.danawa_save("CPU","ryzen7");
        crawlingService.danawa_save("SSD","m.2");


        log.info("Crawling end");
    }
}
