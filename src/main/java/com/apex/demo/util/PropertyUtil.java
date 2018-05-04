/*
 * Copyright @ 2018 com.apexsoft document 下午3:14:13 All right reserved.
 */
package com.apex.demo.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @desc: document
 * @author: yangcheng
 * @createTime: 2018年4月19日 下午3:14:13
 * @version: v1.0
 */
public final class PropertyUtil {
    
    private static String defaultFile = "aliyun.properties";

    /**
     * constructor of PropertyUtil.java
     */
    private PropertyUtil() {}

    private static Properties props;
    static {
        loadProps(defaultFile);
    }

    synchronized static private void loadProps(String fileName) {
        System.out.println("开始加载properties文件内容.......");
        if(props == null)
        {
            props = new Properties();
        }
        InputStream in = null;
        try {
            in = PropertyUtil.class.getClassLoader()
                    .getResourceAsStream(fileName);
            props.load(in);
        } catch (FileNotFoundException e) {
            System.out.println(fileName+"文件未找到");
        } catch (IOException e) {
            System.out.println("出现IOException");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                System.out.println(fileName+"文件流关闭出现异常");
            }
        }
        System.out.println("加载properties文件内容完成...........");
        System.out.println(props);
    }
    
    public static void appendProps(String fileName) {
            loadProps(fileName);
    }

    public static String getProperty(String key) {
        if (null == props) {
            loadProps(defaultFile);
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if (null == props) {
            loadProps(defaultFile);
        }
        return props.getProperty(key, defaultValue);
    }
    
    public static void clear() {
        if (null == props) {
            props.clear();
        }
    }
    
    public static void main(String[] args) {
        appendProps("shortmsg.properties");
        
    }

}

