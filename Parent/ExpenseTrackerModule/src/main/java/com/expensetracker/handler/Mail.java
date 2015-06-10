package com.expensetracker.handler;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.expensetracker.constants.Constants;
import com.expensetracker.logger.Logger;

public class Mail {

	private static Properties props = null;

	private static Session authenticateSession() {

		if (props == null) {
			props = new Properties();
			props.put("mail.smtp.auth", Constants.MAIL_SMTP_AUTH);
			props.put("mail.smtp.starttls.enable",
					Constants.MAIL_SMTP_STARTTLS_ENABLE);
			props.put("mail.smtp.host", Constants.MAIL_SMTP_HOST);
			props.put("mail.smtp.port", Constants.MAIL_SMTP_PORT);
		}
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								Constants.REPORT_ADMIN_ID,
								Constants.REPORT_ADMIN_PWD);
					}
				});

		return session;

	}

	public static void sendMail(String to, String msg) {
		Session session = authenticateSession();
		if (session == null) {
			System.out.println("Seesion not created error");
		}

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(Constants.REPORT_ADMIN_ID));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			// Set Subject: header field
			message.setSubject("Expense Tracker Report for month");

			// Now set the actual message
			message.setText(msg);

			// Send message
			Transport.send(message);
			Logger.logMessage("Mail.sendmail(..) : Mail sent successfully.");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	public static void sendMail(List<String> toList, String msg) {

		Session session = authenticateSession();
		if (session == null) {
			Logger.logMessage("Mail.sendmail(..) : Seesion not created error");
		}

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(Constants.REPORT_ADMIN_ID));

			// Set To: header field of the header.
			for (String to : toList) {
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(to));
			}

			// Set Subject: header field
			message.setSubject("Expense Tracker Report for month");

			// Now set the actual message
			message.setText(msg);

			// Send message
			Transport.send(message);
			Logger.logMessage("Mail.sendmail(..) : Mail sent successfully.");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	public static void sendMail(List<String> toList, File file) {

		Session session = authenticateSession();
		if (session == null) {
			Logger.logMessage("Mail.sendmail(..) : Seesion not created error");
		}

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(Constants.REPORT_ADMIN_ID));

			// Set To: header field of the header.
			for (String to : toList) {
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(to));
			}

			// Set Subject: header field
			message.setSubject("Expense Tracker Report for month");

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message
			messageBodyPart
					.setText("Attached is the report for the this month");

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(file);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(file.getName());
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);
			Logger.logMessage("Mail.sendmail(..) : Mail sent successfully.");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
