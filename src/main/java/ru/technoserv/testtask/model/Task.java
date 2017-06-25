package ru.technoserv.testtask.model;

import org.springframework.stereotype.Component;


@Component
public class Task {

    private String phrase;
    private String wetUrl;

    public String getPhrase() {
        return phrase;
    }
    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getWetUrl() {
        return wetUrl;
    }
    public void setWetUrl(String wetUrl) {
        this.wetUrl = wetUrl;
    }
}
