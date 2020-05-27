package model;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import secretStuff.MailSecrets;
import secretStuff.PaypalSecrets;

public class Email{

	MailSecrets mailSecrets;
	PaypalSecrets paypalSecrets;
	private String sender;
	private String senderPassword;
	private String link;
	String reciver;
	String subject;
	String mailContent;

	Properties MailServerProperties;
	
	public Email() {
		mailSecrets = new MailSecrets();
		paypalSecrets = new PaypalSecrets();
		sender = mailSecrets.getSenderEmail();
		senderPassword = mailSecrets.getSenderPasword();
		MailServerProperties = new Properties();
	}

	public void setReciver(String reciver) {
		this.reciver = reciver;
	}

	public String getReciver() {
		return reciver;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailServerProperties() {
		MailServerProperties.put("mail.smtp.host", "true");
		MailServerProperties.put("mail.smtp.starttls.enable", "true");
		MailServerProperties.put("mail.smtp.host", "smtp.gmail.com");
		MailServerProperties.put("mail.smtp.port", "587");
		MailServerProperties.put("mail.smtp.auth", "true");
	}

	public Session createSession() {
		Session session = Session.getInstance(MailServerProperties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender, senderPassword);
			}
		});
		return session;
	}

	public void sendMail(String email, int orderId) {
		setReciver(email);
		orderCompleteTemplet(orderId);
		setMailServerProperties();

		try {

			MimeMessage message = new MimeMessage(createSession());

			message.setFrom(new InternetAddress(sender));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(reciver));
			message.setSubject(subject);
			message.setText(mailContent);
			System.out.println("sending");

			Transport.send(message);

			System.out.println("Message sent.");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
		

	public void orderCompleteTemplet(int orderId) {
		// subject = "Order complete!";
		subject = "Order " + orderId + " complete!";
		mailContent = "Your service is now complete.\nLink to invoice: " + link + "\nTo login use:\nEmail: "
				+ paypalSecrets.getUsername() + "\nPassword: " + paypalSecrets.getPassword()
				+ "\nBest regards,\n Company";
	}

	public void createLink(String invoiceID) {
		link = "https://www.sandbox.paypal.com/invoice/payerView/details/" + invoiceID;
	}

	public boolean validateEmail(String email) {
		boolean isValidate = true;
		try {
			InternetAddress emailAddress = new InternetAddress(email);
			emailAddress.validate();

		} catch (AddressException e) {
			isValidate = false;
		}
		return isValidate;
	}
}
