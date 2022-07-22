package com.vts.ems;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.search.FlagTerm;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IdleManager;
import com.sun.mail.imap.SortTerm;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		try {
//			mail();
//		} catch (MessagingException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return application.sources(EmsApplication.class);
		
	}

	
	public static void mail() throws MessagingException, IOException {
		 System.out.println("insid5");
		    IMAPFolder folder = null;
		    Store store = null;
		    String subject = null;
		    Flag flag = null;
		    System.out.println("insidi");
		    try 
		    {
		      Properties props = System.getProperties();
		      props.setProperty("mail.store.protocol", "imaps");
		      props.setProperty("mail.imaps.usesocketchannels", "true");

		      Session session = Session.getDefaultInstance(props, null);
              System.out.println("insidi2");
		      ExecutorService es = Executors.newCachedThreadPool();
		      final IdleManager idleManager = new IdleManager(session, es);
		      try 
			    {
		      store = session.getStore("imaps");
		      store.connect("imap.gmail.com","dinesh.vedts@gmail.com", "xfzbakiailrhpzvy");
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
		        //javax.mail.internet.MimeMultipart msger=(javax.mail.internet.MimeMultipart)msg.getContent();
		        System.out.println(msg.getContent());
		        System.out.println(msg.getReceivedDate());
		        System.out.println("From: " + msg.getFrom()[0]);
		        System.out.println("To: "+msg.getAllRecipients()[0]);
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
