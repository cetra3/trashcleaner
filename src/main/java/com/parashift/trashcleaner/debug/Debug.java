package com.parashift.trashcleaner.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Debug {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    void init() {

        logger.debug("\n**************************************************\n" +
                "* Trash Cleaner has been activated on {}\n" +
                "**************************************************\n", new Date());

    }

}
