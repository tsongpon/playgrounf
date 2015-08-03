package com.abctechthailand.playground.controller;

import com.abctechthailand.playground.PlaygroundProperties;
import com.abctechthailand.playground.component.CalendarDownloadServlet;
import com.abctechthailand.playground.component.FrontierComponent;
import com.abctechthailand.playground.transport.frontier.AdObjectFrontierTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;

/**
 *
 */

@Controller //tell spring, this is our controller
public class HelloController{

    @Autowired
    private FrontierComponent frontierComponent;

    @Autowired
    private PlaygroundProperties properties;

    //tell spring to call this method if someone make request to this path
    @RequestMapping("/sayhello.html")
    public String sayHello(Model model) {
        return "index";
    }

    @RequestMapping(value = "/calendar.html")
    public String calendar(Model model, HttpServletRequest request) throws IOException, URISyntaxException {
        return "calendar";
    }

    @RequestMapping(value = "/download.ics", method = RequestMethod.POST)
    public void downloadCalendar(@RequestParam(value = "adId", required = false) Integer adId, HttpServletResponse response) throws IOException, URISyntaxException {
        AdObjectFrontierTransport transport = frontierComponent.fetchAdById(adId);
        if(transport != null){
            CalendarDownloadServlet fileServlet = new CalendarDownloadServlet();
            fileServlet.download(transport, properties.getZettServerHost(), response);
        }
    }

}
