package com.apex.demo.util;

import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Properties;
import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

/**
 * 查看收件箱邮件
 * 
 * @desc: demo
 * @author: yangcheng
 * @createTime: 2018年5月4日 下午4:32:29
 * @version: v1.0
 */
public class Inbox {
    @SuppressWarnings("restriction")
    public static void main(String[] args) {
        // 设置SSL连接、邮件环境
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties props = System.getProperties();
        props.setProperty("mail.pop3.host", "pop.qq.com");
        props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.pop3.socketFactory.fallback", "false");
        props.setProperty("mail.pop3.port", "995");
        props.setProperty("mail.pop3.socketFactory.port", "995");
        props.setProperty("mail.pop3.auth", "true");
        // 建立邮件会话
        Session session = Session.getDefaultInstance(props, null);
        // 设置连接邮件仓库的环境
        URLName url = new URLName("pop3", "pop.qq.com", 995, null,
                "1430478198@qq.com", "*******");
        Store store = null;
        Folder inbox = null;
        try {
            // 得到邮件仓库并连接
            store = session.getStore(url);
            store.connect();
            // 得到收件箱并抓取邮件
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            FetchProfile profile = new FetchProfile();
            profile.add(FetchProfile.Item.ENVELOPE);
            Message[] messages = inbox.getMessages();
            inbox.fetch(messages, profile);
            // 打印收件箱邮件部分信息
            int length = messages.length;
            System.out.println("收件箱的邮件数：" + length);
            System.out.println("-------------------------------------------\n");
            for (int i = 0; i < length; i++) {
                String from = MimeUtility
                        .decodeText(messages[i].getFrom()[0].toString());
                InternetAddress ia = new InternetAddress(from);
                System.out.println("发件人：" + ia.getPersonal() + "("
                        + ia.getAddress() + ")");
                System.out.println("主题：" + messages[i].getSubject());
                System.out.println("邮件大小：" + messages[i].getSize());
                System.out.println(
                        "邮件发送时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .format(messages[i].getSentDate()));
                System.out.println(
                        "-------------------------------------------\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inbox.close(false);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            try {
                store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
