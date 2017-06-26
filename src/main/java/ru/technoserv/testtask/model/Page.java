package ru.technoserv.testtask.model;


import org.springframework.stereotype.Component;

/** Page for processing search
 * Created by Home on 24.06.2017.
 */
@Component
public class Page {
    private String url;
    private String content;

    public Page() {
        setUrl(url);
        setContent(content);
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
