package com.computer.server.service.crawling;

import com.computer.server.entity.domain.Computer;
import com.computer.server.entity.dto.ComputerDTO;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.List;

public interface Crawling {
    String makeURI(String keyword);
    Elements connection(String url);
    List<Computer> dataParser(Elements elements, String Type, String keyword);
}
