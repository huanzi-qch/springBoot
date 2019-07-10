package cn.huanzi.qch.springbootmail.serive.mail;

import javax.mail.MessagingException;
import java.io.File;
import java.util.List;

public interface SpringBootMailService {
    /**
     * 发送简单邮件
     *
     * @param to      接收方
     * @param subject 邮件主题
     * @param text    邮件内容
     */
    public void sendSimpleMail(String to, String subject, String text);

    /**
     * 发送HTML格式的邮件
     *
     * @param to      接收方
     * @param subject 邮件主题
     * @param content HTML格式的邮件内容
     * @throws MessagingException
     */
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException;

    /**
     * 发送HTML格式的邮件，并可以添加附件
     * @param to      接收方
     * @param subject 邮件主题
     * @param content HTML格式的邮件内容
     * @param files   附件
     * @throws MessagingException
     */
    public void sendAttachmentsMail(String to, String subject, String content, List<File> files) throws MessagingException;
}
