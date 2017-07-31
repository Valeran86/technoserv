package ru.technoserv.testtask.wet;

import org.junit.Assert;
import org.junit.Test;
import ru.technoserv.testtask.controller.MainController;
import ru.technoserv.testtask.model.SearchResult;
import ru.technoserv.testtask.model.Task;

/** Tests for WetLoader (facade static methods)
 * Created by Home on 25.06.2017.
 */
public class WetLoaderTest {
    @Test
    public void testGenerateWet(){
        String wetUrl = WetLoader.generateWet();

        Assert.assertNotNull(wetUrl);
        Assert.assertNotEquals(0, wetUrl.length());
    }
}