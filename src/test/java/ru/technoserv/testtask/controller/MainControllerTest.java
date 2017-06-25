package ru.technoserv.testtask.controller;

import org.junit.Assert;
import org.junit.Test;
import ru.technoserv.testtask.model.SearchResult;
import ru.technoserv.testtask.model.Task;

/** Tests for MainController static methods
 * Created by Home on 25.06.2017.
 */
public class MainControllerTest {
    @Test
    public void testGenerateWet(){
        String wetUrl = MainController.generateWet();

        Assert.assertNotNull(wetUrl);
        Assert.assertNotEquals(0, wetUrl.length());
    }
    @Test
    public void testSearch(){
        Task task = new Task();
        task.setWetUrl(MainController.DEFAULT_URL);
        task.setPhrase("Using Technology to Connect Students & the Environment – MandyEngland");

        SearchResult searchResult = MainController.search(task);
        Assert.assertNotNull(searchResult);
        Assert.assertEquals(1, searchResult.getPages().size());
    }
}