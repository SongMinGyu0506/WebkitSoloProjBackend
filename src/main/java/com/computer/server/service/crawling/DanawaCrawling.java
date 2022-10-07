package com.computer.server.service.crawling;

import com.computer.server.entity.domain.Computer;
import com.computer.server.entity.dto.ComputerDTO;
import com.google.common.base.Charsets;
import lombok.NoArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class DanawaCrawling implements Crawling {
    @Override
    public String makeURI(String keyword) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("search.danawa.com")
                .path("dsearch.php")
                .encode(Charsets.UTF_8)
                .queryParam("query",keyword)
                .queryParam("originalQuery",keyword)
                .queryParam("volumeType","all")
                .queryParam("page",1)
                .queryParam("limit",120)
                .queryParam("sort","saveDESC")
                .queryParam("boost","true")
                .build(true);
        return uriComponents.toUriString();
    }

    @Override
    public Elements connection(String url) {
        Connection conn = Jsoup.connect(url);
        final Elements dataDocument;
        try {
            Document document = conn.get();
            dataDocument = document.select(".product_list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dataDocument;
    }
    @Override
    public ArrayList<Computer> dataParser(Elements elements, String Type, String keyword) {
        ArrayList<Computer> list = new ArrayList<>();
        Elements mainElements = elements.select(".prod_main_info");
        for (Element element : mainElements) {
            String name = element.select(".prod_name").text();
            String imgUrl = "https:"+element.select(".thumb_image > a > img").attr("data-src");
            String originalUrl = element.select(".prod_name > a").attr("href");
            String spec = element.select(".spec_list").text();
            String price = element.select(".click_log_product_standard_price_ > strong").text();
            Computer computer = Computer.builder()
                    .comType(Type)
                    .imgUrl(imgUrl)
                    .originalUrl(originalUrl)
                    .name(name)
                    .spec(spec)
                    .price(price)
                    .searchTag(keyword)
                    .build();

            list.add(computer);
        }
        return list;
    }
}
