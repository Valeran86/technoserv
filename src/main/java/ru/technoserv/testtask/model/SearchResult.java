package ru.technoserv.testtask.model;

import org.springframework.stereotype.Component;

import java.util.List;

/** The result of searching phrase
 * Created by Home on 24.06.2017.
 */
@Component
public class SearchResult {
    private List<Integer> pages;

    public List<Integer> getPages() {
        return pages;
    }
    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }
}
