package ru.technoserv.testtask.model;


import org.springframework.stereotype.Component;

/** Page for processing search
 * Created by Home on 24.06.2017.
 */
@Component
public class Page {
    private int index;
    private String content;

    public Page(int index, String content) {
        setIndex(index);
        setContent(content);
    }

    public int getIndex() {
        return index;
    }
    private void setIndex(int index) {
        this.index = index;
    }

    public String getContent() {
        return content;
    }
    private void setContent(String content) {
        this.content = content;
    }
}
