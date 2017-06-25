package ru.technoserv.testtask.controller;

import org.archive.io.ArchiveReader;
import org.archive.io.ArchiveRecord;
import org.archive.io.warc.WARCReaderFactory;
import org.jets3t.service.S3Service;
import org.jets3t.service.ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Object;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.technoserv.testtask.model.Page;
import ru.technoserv.testtask.model.SearchResult;
import ru.technoserv.testtask.model.Task;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

//@SpringBootApplication
//@RestController
@Controller
public class MainController {
    static final String DEFAULT_URL = "crawl-data/CC-MAIN-2017-17/segments/1492917118310.2/wet/CC-MAIN-20170423031158-00013-ip-10-145-167-34.ec2.internal.warc.wet.gz";
    private static final String WET_DATA_BASE = "wet.paths.gz";
    private static final String ENCODING = "utf8";


    /**First method on start application*/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main() {
        Task task = new Task();
        task.setWetUrl(generateWet());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("task", task);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    /**Action - search phrase into text pages */
    @RequestMapping(value = "/find-phrase")
    public ModelAndView findPhrase(@ModelAttribute("task") Task task) {
        SearchResult searchResult = search(task);

        ModelAndView modelAndView = new ModelAndView();

        //имя представления, куда нужно будет перейти
        modelAndView.setViewName("parsePage");

        modelAndView.addObject("task", task);
        modelAndView.addObject("searchResult", searchResult);

        return modelAndView;
    }

    /*public static void main(String[] args) {
        SpringApplication.run(MainController.class, args);
    }*/

    /** Getting search for param task */
    static SearchResult search(Task task) {
        Pattern pattern = Pattern.compile(task.getPhrase(), Pattern.CASE_INSENSITIVE);

        /* function style */
        final int[] i = {0};
        // for generating index use one threaded stream()
        List<Page> pages = getPages(task).stream()
                .map(s -> new Page(i[0]++, s))
                .collect(Collectors.toList());

        // now for searching can use parallel
        List<Integer> pagesIndex = pages.parallelStream()
                .filter(p -> pattern.matcher(p.getContent()).find())
                .map(Page::getIndex)
                .collect(Collectors.toList());

        /* procedure style
        int i = 0;
        List<Integer> pagesIndex = new LinkedList<>();
        for(String page : getPages(task)){
            Matcher matcher = pattern.matcher(page);
            if (matcher.matches())
                pages.add(Integer.valueOf(i));
            i++;
        }*/

        SearchResult searchResult = new SearchResult();
        searchResult.setPages(pagesIndex);

        return searchResult;
    }

    /** One thread parse and load pages */
    private static List<String> getPages(Task task) {
        List<String> pages = new LinkedList<>();
        ArchiveReader ar = getArchiveReader(task.getWetUrl());

        int maxLength = 10000;
        byte[] rawData = new byte[maxLength];
        for(ArchiveRecord record : ar)
            try {
                int available = record.available();
                if (available > maxLength){
                    maxLength = available;
                    rawData = new byte[maxLength];
                }
                int actualRead = record.read(rawData, 0, available);

                String page = new String(rawData, 0, actualRead);
                pages.add(page);
            } catch (IOException e) {
                e.printStackTrace();
            }

        return pages;
    }

    /** Get ArchiveReader object for parsing wet file */
    private static ArchiveReader getArchiveReader(String wetUrl) {
        // We're accessing a publicly available bucket so don't need to fill in our credentials
        S3Service s3s = new RestS3Service(null);

        try {
            // Let's grab a file out of the CommonCrawl S3 bucket
            S3Object f = s3s.getObject("commoncrawl", wetUrl, null, null, null, null, null, null);

            // The file name identifies the ArchiveReader and indicates if it should be decompressed
            return WARCReaderFactory.get(wetUrl, f.getDataInputStream(), true);
        } catch (ServiceException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Generate wet sample url for task */
    static String generateWet() {
        String wetUrl;
        try {
            List<String> wetUrls = loadWetDataBase();
            int seed = wetUrls.size();
            Random rnd = new Random(seed);

            wetUrl = wetUrls.get(rnd.nextInt() % wetUrls.size());
        }
        catch (IOException e) {
            wetUrl = DEFAULT_URL;
        }

        return wetUrl;
    }

    /** Load wet url's base from resources */
    private static List<String> loadWetDataBase() throws IOException {
        Resource resource = new ClassPathResource(WET_DATA_BASE);
        try(
                InputStream resourceStream = resource.getInputStream();
                GZIPInputStream gzipStream = new GZIPInputStream(resourceStream);
                Reader decoder = new InputStreamReader(gzipStream, ENCODING);
                BufferedReader buffered = new BufferedReader(decoder)
        ) {
            List<String> wetUrls = new LinkedList<>();
            String content;

            while ((content = buffered.readLine()) != null)
                wetUrls.add(content);
            return wetUrls;
        }
    }
}