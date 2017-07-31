package ru.technoserv.testtask.bgp;

import ru.technoserv.testtask.model.Page;
import ru.technoserv.testtask.model.SearchResult;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** Filter strategy for test task
 * Created by Home on 28.06.2017.
 */
public class Filter {

    /** Getting search for param task */
    public static SearchResult process(String phrase, List<Page> pages) {
        Pattern pattern = Pattern.compile(phrase, Pattern.CASE_INSENSITIVE);

        /* function style */
        // now for searching can use parallel
        List<String> pagesIndex = pages.parallelStream()
                .filter(p -> pattern.matcher(p.getContent()).find())
                .map(Page::getUrl)
                .collect(Collectors.toList());

        /* procedure style
        List<String> pagesIndex = new LinkedList<>();
        for(Page page : getPages(task)){
            String content = page.getContent();
            Matcher matcher = pattern.matcher(content);
            if (matcher.find())
                pages.add(page.getUrl());
        }*/

        SearchResult searchResult = new SearchResult();
        searchResult.setPages(pagesIndex);

        return searchResult;
    }
}
