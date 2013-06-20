package com.jiangchao.core.exception;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

public class ExceptionUtilsDemo {

    private final static Logger log = Logger.getLogger(ExceptionUtilsDemo.class);

    public static void main(String[] args) {
        try {
            int x = 1 / 0;
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        } finally {
            // log
        }
    }

}
