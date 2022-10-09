package com.computer.server.service.crawling;

import com.computer.server.entity.domain.Computer;
import com.computer.server.repository.ComputerRepository;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CrawlingService {
    @Autowired
    private Crawling danawaCrawling;
    @Autowired
    private Crawling happyCrawling;
    @Autowired
    private Crawling compuzoneCrawling;

    private Crawling crawling;
    @Autowired
    private ComputerRepository computerRepository;

    public void danawa_save(String type, String keyword) {
        String URL = danawaCrawling.makeURI(keyword);
        Elements connection = danawaCrawling.connection(URL);
        ArrayList<Computer> computers = (ArrayList<Computer>) danawaCrawling.dataParser(connection,type,keyword);
        for (Computer computer : computers) {
            computerRepository.save(computer);
        }
    }

    public void crawling_init() {
        computerRepository.deleteAllInBatch();
    }
}
