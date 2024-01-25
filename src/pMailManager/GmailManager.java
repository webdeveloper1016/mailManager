package pMailManager;

import java.util.Properties;
import java.util.Scanner;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GmailManager {
    private String userAccount = "";
    private String password = "";
    private Session session;
    
    public GmailManager(String name, String pwd) {
    	userAccount = name;
    	password = pwd;
    	setSession();
    }
    
    private void setSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userAccount, password);
            }
        });	
    }
    
    public void sendEmail(String toEmail, String title, String body)
    {
        try {
        	MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userAccount));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse("toEmail"));
            message.setSubject(toEmail);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
	public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter your mail account: ");
        String mail = scanner.nextLine();
        
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        
        scanner.close();
		
        String toEmail = "roberta397@anderson.aleeas.com";
        String title = "Subject Here";
        String body = "Body of the message";
        
        GmailManager gmailSender = new GmailManager(mail, password);
        gmailSender.sendEmail(toEmail, title, body);
	}
}