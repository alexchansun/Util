package com.fanli.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: cyx
 * Date: 2010-12-8
 * Time: 21:49:59
 */
public class Setting {
       private static final Log log = LogFactory.getLog(Setting.class);
       public static Properties SETTINGS;

       static {
           init();
       }

       private static void init() {

           SETTINGS = new Properties();
           InputStream stream = Setting.class.getClassLoader().getResourceAsStream("setting.properties");
           try {
               SETTINGS.load(stream);
           } catch (IOException e) {
               log.error("初始化配置失败！",e);
           } finally {
               IOUtils.closeQuietly(stream);
           }

       }

       public static String getSetting(String key) {
           return SETTINGS.getProperty(key);
       }

}
