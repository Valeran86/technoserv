package ru.technoserv.testtask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.technoserv.testtask.bgp.Filter;
import ru.technoserv.testtask.model.Page;
import ru.technoserv.testtask.model.SearchResult;
import ru.technoserv.testtask.model.Task;
import ru.technoserv.testtask.wet.WetLoader;

import java.util.List;

@Controller
public class MainController {

    /**First method on start application*/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main() {
        Task task = new Task();
        task.setWetUrl(WetLoader.generateWet());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("task", task);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    /**Action - search phrase into text pages */
    @RequestMapping(value = "/find-phrase")
    public ModelAndView findPhrase(@ModelAttribute("task") Task task) {
        // one threaded loading
        List<Page> pages = WetLoader.getPages(task.getWetUrl());

        SearchResult searchResult = Filter.process(task.getPhrase(), pages);

        ModelAndView modelAndView = new ModelAndView();

        //имя представления, куда нужно будет перейти
        modelAndView.setViewName("parsePage");

        modelAndView.addObject("task", task);
        modelAndView.addObject("searchResult", searchResult);

        return modelAndView;
    }
}