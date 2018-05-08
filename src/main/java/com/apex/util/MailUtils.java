/*
 * Copyright @ 2018 com.apexsoft demo 上午10:12:04 All right reserved.
 */
package com.apex.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**  
 * 使用POP3协议接收邮件
 * @desc: demo  
 * @author: yangcheng  
 * @createTime: 2018年5月4日 下午4:33:13    
 * @version: v1.0    
 */    
public class MailUtils {

    /**  
     * @author: yangcheng 
     * @createTime: 2018年5月4日 下午4:33:27    
     * @param args 
     */  
    public static void main(String[] args) {

        try {
            receive();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    /**  
     * 接收邮件
     * @author: yangcheng 
     * @createTime: 2018年5月4日 下午4:33:39    
     * @throws Exception  
     */  
    @SuppressWarnings("restriction")
    public static void receive() throws Exception {
        // 设置SSL连接、邮件环境
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties props = System.getProperties();
        props.setProperty("mail.pop3.host", PropertyUtil.getProperty("mail.pop3.host"));
        props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.pop3.socketFactory.fallback", "false");
        props.setProperty("mail.pop3.port", "995");
        props.setProperty("mail.pop3.socketFactory.port", "995");
        props.setProperty("mail.pop3.auth", "true");
        props.setProperty("mail.pop3.debug", "true");
        // 建立邮件会话
        Session session = Session.getDefaultInstance(props, null);
        // 设置连接邮件仓库的环境
        URLName url = new URLName("pop3",
                PropertyUtil.getProperty("mail.pop3.host"), 995, null,
                PropertyUtil.getProperty("mail.username"),
                PropertyUtil.getProperty("mail.password"));
        Store store = session.getStore(url);
        store.connect();
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        FetchProfile profile = new FetchProfile();
        profile.add(FetchProfile.Item.ENVELOPE);
        Message[] messages = folder.getMessages();
        folder.fetch(messages, profile);

        // 由于POP3协议无法获知邮件的状态,所以getUnreadMessageCount得到的是收件箱的邮件总数
        System.out.println("未读邮件数: " + folder.getUnreadMessageCount());

        // 由于POP3协议无法获知邮件的状态,所以下面得到的结果始终都是为0
        System.out.println("删除邮件数: " + folder.getDeletedMessageCount());
        System.out.println("新邮件: " + folder.getNewMessageCount());

        // 获得收件箱中的邮件总数
        System.out.println("邮件总数: " + folder.getMessageCount());

        // 得到收件箱中的所有邮件,并解析
        parseMessage(messages);

        // 得到收件箱中的所有邮件并且删除邮件
        // deleteMessage(messages);

        // 释放资源
        folder.close(true);
        store.close();
    }

    /**  
     * 解析邮件
     * @author: yangcheng 
     * @createTime: 2018年5月4日 下午4:34:07    
     * @param messages 要解析的邮件列表
     * @throws MessagingException
     * @throws IOException void  
     */  
    public static void parseMessage(Message... messages)
            throws MessagingException, IOException {
        if (messages == null || messages.length < 1)
            throw new MessagingException("未找到要解析的邮件!");

        int count = messages.length;
        // 解析所有邮件
        for (int i = 0; i < count; i++) {
            MimeMessage msg = (MimeMessage) messages[i];
            System.out.println("------------------解析第" + msg.getMessageNumber()
                    + "封邮件-------------------- ");
            System.out.println("主题: " + getSubject(msg));
            System.out.println("发件人: " + getFrom(msg));
            System.out.println("收件人：" + getReceiveAddress(msg, null));
            System.out.println("发送时间：" + getSentDate(msg, null));
            System.out.println("是否已读：" + isSeen(msg));
            System.out.println("邮件优先级：" + getPriority(msg));
            System.out.println("是否需要回执：" + isReplySign(msg));
            System.out.println("邮件大小：" + msg.getSize() * 1024 + "kb");
            boolean isContainerAttachment = isContainAttachment(msg);
            System.out.println("是否包含附件：" + isContainerAttachment);
            if (isContainerAttachment) {
                saveAttachment(msg, "D:\\mailTest\\" + i + "\\"); // 保存附件
            }
            StringBuffer content = new StringBuffer(30);
            getMailTextContent(msg, content);
            System.out.println("邮件正文：" + (content.length() > 100
                    ? content.substring(0, 100) + "..." : content));
            System.out.println("------------------第" + msg.getMessageNumber()
                    + "封邮件解析结束-------------------- ");
            System.out.println();

        }
    }

    /**  
     * 删除邮件
     * @author: yangcheng 
     * @createTime: 2018年5月4日 下午4:34:37    
     * @param messages
     * @throws MessagingException
     * @throws IOException void  
     */  
    public static void deleteMessage(Message... messages)
            throws MessagingException, IOException {
        if (messages == null || messages.length < 1)
            throw new MessagingException("未找到要解析的邮件!");

        // 解析所有邮件
        for (int i = 0, count = messages.length; i < count; i++) {
            Message message = messages[i];
            String subject = message.getSubject();
            // set the DELETE flag to true
            message.setFlag(Flags.Flag.DELETED, true);
            System.out.println("Marked DELETE for message: " + subject);
        }
    }

    /**  
     * 获得邮件主题
     * @author: yangcheng 
     * @createTime: 2018年5月4日 下午4:35:13    
     * @param msg 邮件内容
     * @return 解码后的邮件主题
     * @throws UnsupportedEncodingException
     * @throws MessagingException String  
     */  
    public static String getSubject(MimeMessage msg)
            throws UnsupportedEncodingException, MessagingException {
        return MimeUtility.decodeText(msg.getSubject());
    }

    /**  
     * 获得邮件发件人
     * @author: yangcheng 
     * @createTime: 2018年5月4日 下午4:35:47    
     * @param msg
     * @return  姓名 <Email地址>
     * @throws MessagingException
     * @throws UnsupportedEncodingException String  
     */  
    public static String getFrom(MimeMessage msg)
            throws MessagingException, UnsupportedEncodingException {
        String from = "";
        Address[] froms = msg.getFrom();
        if (froms.length < 1)
            throw new MessagingException("没有发件人!");

        InternetAddress address = (InternetAddress) froms[0];
        String person = address.getPersonal();
        if (person != null) {
            person = MimeUtility.decodeText(person) + " ";
        } else {
            person = "";
        }
        from = person + "<" + address.getAddress() + ">";

        return from;
    }

    /**  
     * 根据收件人类型，获取邮件收件人、抄送和密送地址。如果收件人类型为空，则获得所有的收件人
     * @author: yangcheng 
     * @createTime: 2018年5月4日 下午4:36:15    
     * @param msg
     * @param type 收件人类型(TO 收件人,CC 抄送,BCC 密送)
     * @return 收件人1 <邮件地址1>, 收件人2 <邮件地址2>, ...
     * @throws MessagingException String  
     */  
    public static String getReceiveAddress(MimeMessage msg,
            Message.RecipientType type) throws MessagingException {
        StringBuffer receiveAddress = new StringBuffer();
        Address[] addresss = null;
        if (type == null) {
            addresss = msg.getAllRecipients();
        } else {
            addresss = msg.getRecipients(type);
        }

        if (addresss == null || addresss.length < 1)
            throw new MessagingException("没有收件人!");
        for (Address address : addresss) {
            InternetAddress internetAddress = (InternetAddress) address;
            receiveAddress.append(internetAddress.toUnicodeString())
                    .append(",");
        }
        receiveAddress.deleteCharAt(receiveAddress.length() - 1); // 删除最后一个逗号
        return receiveAddress.toString();
    }

    /**  
     * 获得邮件发送时间
     * @author: yangcheng 
     * @createTime: 2018年5月4日 下午4:37:41    
     * @param msg
     * @param pattern
     * @return yyyy年mm月dd日 星期X HH:mm
     * @throws MessagingException String  
     */  
    public static String getSentDate(MimeMessage msg, String pattern)
            throws MessagingException {
        Date receivedDate = msg.getSentDate();
        if (receivedDate == null)
            return "";

        if (pattern == null || "".equals(pattern))
            pattern = "yyyy年MM月dd日 E HH:mm ";

        return new SimpleDateFormat(pattern).format(receivedDate);
    }

    /**  
     * 判断邮件中是否包含附件
     * @author: yangcheng 
     * @createTime: 2018年5月4日 下午4:38:13    
     * @param part
     * @return 邮件中存在附件返回true，不存在返回false
     * @throws MessagingException
     * @throws IOException boolean  
     */  
    public static boolean isContainAttachment(Part part)
            throws MessagingException, IOException {
        boolean flag = false;
        if (part.isMimeType("multipart/*")) {
            MimeMultipart multipart = (MimeMultipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String disp = bodyPart.getDisposition();
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT)
                        || disp.equalsIgnoreCase(Part.INLINE))) {
                    flag = true;
                } else if (bodyPart.isMimeType("multipart/*")) {
                    flag = isContainAttachment(bodyPart);
                } else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.indexOf("application") != -1) {
                        flag = true;
                    }

                    if (contentType.indexOf("name") != -1) {
                        flag = true;
                    }
                }

                if (flag){
                    break;
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            flag = isContainAttachment((Part) part.getContent());
        }
        return flag;
    }

    /**  
     * 判断邮件是否已读
     * @author: yangcheng 
     * @createTime: 2018年5月4日 下午4:38:59    
     * @param msg
     * @return 如果邮件已读返回true,否则返回false
     * @throws MessagingException boolean  
     */  
    public static boolean isSeen(MimeMessage msg) throws MessagingException {
        return msg.getFlags().contains(Flags.Flag.SEEN);
    }

    /** 
     * 判断邮件是否需要阅读回执 
     * @author: yangcheng 
     * @createTime: 2018年5月4日 下午4:39:32    
     * @param msg
     * @return 需要回执返回true,否则返回false
     * @throws MessagingException boolean  
     */  
    public static boolean isReplySign(MimeMessage msg)
            throws MessagingException {
        boolean replySign = false;
        String[] headers = msg.getHeader("Disposition-Notification-To");
        if (headers != null)
            replySign = true;
        return replySign;
    }

    /**  
     * 获得邮件的优先级
     * @author: yangcheng 
     * @createTime: 2018年5月4日 下午4:39:58    
     * @param msg
     * @return 1(High):紧急 3:普通(Normal) 5:低(Low)
     * @throws MessagingException String  
     */  
    public static String getPriority(MimeMessage msg)
            throws MessagingException {
        String priority = "普通";
        String[] headers = msg.getHeader("X-Priority");
        if (headers != null) {
            String headerPriority = headers[0];
            if (headerPriority.indexOf("1") != -1
                    || headerPriority.indexOf("High") != -1)
                priority = "紧急";
            else if (headerPriority.indexOf("5") != -1
                    || headerPriority.indexOf("Low") != -1)
                priority = "低";
            else
                priority = "普通";
        }
        return priority;
    }

    /**  
     * 获得邮件文本内容
     * @author: yangcheng 
     * @createTime: 2018年5月4日 下午4:41:03    
     * @param part
     * @param content 存储邮件文本内容的字符串
     * @throws MessagingException
     * @throws IOException void  
     */  
    public static void getMailTextContent(Part part, StringBuffer content)
            throws MessagingException, IOException {
        // 如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断
        boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
        if (part.isMimeType("text/*") && !isContainTextAttach) {
            content.append(part.getContent().toString());
        } else if (part.isMimeType("message/rfc822")) {
            getMailTextContent((Part) part.getContent(), content);
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                getMailTextContent(bodyPart, content);
            }
        }
    }

    /**  
     * 保存附件
     * @author: yangcheng 
     * @createTime: 2018年5月4日 下午4:41:44    
     * @param part
     * @param destDir 附件保存目录
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     * @throws FileNotFoundException
     * @throws IOException void  
     */  
    public static void saveAttachment(Part part, String destDir)
            throws UnsupportedEncodingException, MessagingException,
            FileNotFoundException, IOException {
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent(); // 复杂体邮件
            // 复杂体邮件包含多个邮件体
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                // 获得复杂体邮件中其中一个邮件体
                BodyPart bodyPart = multipart.getBodyPart(i);
                // 某一个邮件体也有可能是由多个邮件体组成的复杂体
                String disp = bodyPart.getDisposition();
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT)
                        || disp.equalsIgnoreCase(Part.INLINE))) {
                    InputStream is = bodyPart.getInputStream();
                    saveFile(is, destDir, decodeText(bodyPart.getFileName()));
                } else if (bodyPart.isMimeType("multipart/*")) {
                    saveAttachment(bodyPart, destDir);
                } else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.indexOf("name") != -1
                            || contentType.indexOf("application") != -1) {
                        saveFile(bodyPart.getInputStream(), destDir,
                                decodeText(bodyPart.getFileName()));
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            saveAttachment((Part) part.getContent(), destDir);
        }
    }

   
    /**  
     * 读取输入流中的数据保存至指定目录
     * @author: yangcheng 
     * @createTime: 2018年5月4日 下午4:42:21    
     * @param is
     * @param destDir
     * @param fileName
     * @throws FileNotFoundException
     * @throws IOException void  
     */  
    private static void saveFile(InputStream is, String destDir,
            String fileName) throws FileNotFoundException, IOException {
        if(fileName == null || fileName.trim().equals(""))
        {
            return;
        }
        BufferedInputStream bis = new BufferedInputStream(is);
        File dir = new File(destDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(new File(destDir + fileName)));
        int len = -1;
        while ((len = bis.read()) != -1) {
            bos.write(len);
            bos.flush();
        }
        bos.close();
        bis.close();
    }

    /**  
     * 文本解码
     * @author: yangcheng 
     * @createTime: 2018年5月4日 下午4:42:43    
     * @param encodeText
     * @return
     * @throws UnsupportedEncodingException String  
     */  
    public static String decodeText(String encodeText)
            throws UnsupportedEncodingException {
        if (encodeText == null || "".equals(encodeText)) {
            return "";
        } else {
            return MimeUtility.decodeText(encodeText);
        }
    }


}


