package ru.technoserv.testtask.model;

import org.springframework.stereotype.Component;

import java.util.List;

/** The result of searching phrase
 * Created by Home on 24.06.2017.
 */
@Component
public class SearchResult {
    private List<String> pages;

    public List<String> getPages() {
        return pages;
    }
    public void setPages(List<String> pages) {
        this.pages = pages;
    }
}
