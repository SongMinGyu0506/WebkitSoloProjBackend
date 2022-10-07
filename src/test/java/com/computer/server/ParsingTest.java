package com.computer.server;

import com.computer.server.entity.domain.Computer;
import com.computer.server.entity.dto.ComputerDTO;
import com.computer.server.repository.ComputerRepository;
import com.computer.server.service.crawling.Crawling;
import com.computer.server.service.crawling.CrawlingService;
import com.computer.server.service.crawling.DanawaCrawling;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class ParsingTest {
    @Autowired
    Crawling danawaCrawling;
    @Autowired
    ComputerRepository computerRepository;

    @Autowired
    CrawlingService crawlingService;

    @Test
    public void crawlingTest() {
        String url = danawaCrawling.makeURI("rtx3060");
        Elements elements = danawaCrawling.connection(url).select(".prod_main_info");
        List<Computer> list = new ArrayList<>();
        for (Element element : elements) {
//            System.out.println(element.select(".prod_name").text());
            String name = element.select(".prod_name").text();
            String imgUrl = "https:"+element.select(".thumb_image > a > img").attr("data-src");
            String originalUrl = element.select(".prod_name > a").attr("href");
            String spec = element.select(".spec_list").text();
            String price = element.select(".click_log_product_standard_price_ > strong").text();
//            System.out.println("https:"+element.select(".thumb_image > a > img").attr("data-src"));
//            System.out.println(element.select(".prod_name > a").attr("href"));
//            System.out.println(element.select(".spec_list").text());
//            System.out.println(element.select(".click_log_product_standard_price_ > strong").text());
//            System.out.println();
            Computer computer = Computer.builder()
                    .comType("VGA")
                    .imgUrl(imgUrl)
                    .originalUrl(originalUrl)
                    .name(name)
                    .spec(spec)
                    .price(price)
                    .build();
            list.add(computer);
        }
        for (Computer computer : list) {
            System.out.println(computer.getName());
        }
        //System.out.println(elements.select(".prod_name").text());
    }

    @Test
    public void ctest() {
        crawlingService.danawa_save("CPU","INTEL");
        List<Computer> lists = computerRepository.findAll();
        for (Computer computer : lists) {
            System.out.println(computer.getId());
            System.out.println(computer.getName());
            System.out.println(computer.getComType());
            System.out.println(computer.getImgUrl());
            System.out.println(computer.getSpec());
            System.out.println(computer.getOriginalUrl());
        }
    }
}
