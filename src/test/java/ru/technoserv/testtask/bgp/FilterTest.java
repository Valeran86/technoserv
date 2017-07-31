package ru.technoserv.testtask.bgp;

import org.junit.Assert;
import org.junit.Test;
import ru.technoserv.testtask.model.Page;
import ru.technoserv.testtask.model.SearchResult;
import ru.technoserv.testtask.wet.WetLoader;

import java.util.List;

/** Test for Filter strategy
 * Created by Home on 28.06.2017.
 */
public class FilterTest {
    @Test
    public void testSearch(){
        String phrase = "Using Technology to Connect Students & the Environment – MandyEngland";
        List<Page> pages = WetLoader.getPages(WetLoader.DEFAULT_URL);

        SearchResult searchResult = Filter.process(phrase, pages);
        Assert.assertNotNull(searchResult);
        Assert.assertEquals(1, searchResult.getPages().size());
    }
}
