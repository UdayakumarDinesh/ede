package com.vts.ems.utils;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Flags.Flag;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.search.FlagTerm;

import org.springframework.beans.factory.annotation.Value;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IdleManager;
import com.sun.mail.imap.SortTerm;

public class MailReciever {
	
	public  void mail(String  mail_protocol,String  mail_host,String  mail_port,String  mail_username,String  mail_password) throws MessagingException, IOException {
		 System.out.println("insid5");
		    IMAPFolder folder = null;
		    Store store = null;
		    String subject = null;
		    Flag flag = null;
		    System.out.println("insidi");
		    try 
		    {
		      Properties props = System.getProperties();
		      props.setProperty("mail.store.protocol", mail_protocol );
		      props.setProperty("mail."+mail_protocol+".usesocketchannels", "true");
		      props.setProperty("mail."+mail_protocol+".port",mail_port);
		      props.setProperty("mail."+mail_protocol+".ssl.trust",mail_host);
		      props.setProperty("mail."+mail_protocol+".timeout", "10000");
		      Session session = Session.getDefaultInstance(props, null);
              System.out.println("insidi2");
		      ExecutorService es = Executors.newCachedThreadPool();
		      final IdleManager idleManager = new IdleManager(session, es);
		      try 
			    {
		      store = session.getStore(mail_protocol);
		      store.connect(mail_host ,mail_username,mail_password);
		      System.out.println("insid3");
			    }catch (Exception e) {
					e.printStackTrace();
				}
		      SortTerm[] termos = new SortTerm[1];
	            termos[0] = SortTerm.ARRIVAL;
		      folder = (IMAPFolder) store.getFolder("INBOX"); 


		      if(!folder.isOpen())
		      folder.open(Folder.READ_WRITE);

		      folder.addMessageCountListener(new MessageCountAdapter() {
		          public void messagesAdded(MessageCountEvent ev) {
		              Folder folder = (Folder)ev.getSource();
		              //Message[] msgs = ev.getMessages();
		              Message[] msgs=null;
					try {
						msgs = folder.search(
							        new FlagTerm(new Flags(Flags.Flag.SEEN), false));
						
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		              System.out.println("Folder: " + folder +
		                  " got " + msgs.length + " new messages");
		              try {
		                  // process new messages
		                  idleManager.watch(folder); // keep watching for new messages
		              } catch (MessagingException mex) {
		                  // handle exception related to the Folder
		              }
		          }
		      });
		      idleManager.watch(folder);


		      Message[] messages = folder.getMessages();
		      System.out.println("No of Messages : " + folder.getMessageCount());
		      System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());
		      System.out.println(messages.length);


		      for (int i=messages.length-1; i >0;i--) 
		      {

		        System.out.println("*****************************************************************************");
		        System.out.println("MESSAGE " + (i + 1) + ":");
		        Message msg =  messages[i];

		        subject = msg.getSubject();
 
		        System.out.println("Subject: " + subject);
		        System.out.println("At: " + msg.ATTACHMENT);
		        //javax.mail.internet.MimeMultipart msger=(javax.mail.internet.MimeMultipart)msg.getContent();
		        System.out.println(msg.getContent());
		        System.out.println(msg.getReceivedDate());
		        System.out.println("From: " + msg.getFrom()[0]);
		        System.out.println("To: "+msg.getAllRecipients()[0]);
		        System.out.println("CC: " + msg.getAllRecipients());
		        msg.setFlag(Flag.SEEN, true);
		        break;

		      }
		    }
		    catch (Exception e) {
				e.printStackTrace();
			}
		    finally 
		    {
		      if (folder != null && folder.isOpen()) { folder.close(true); }
		      if (store != null) { store.close(); }
		    }
		
	}
}
