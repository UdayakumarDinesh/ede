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
		    IMAPFolder folder = null;
		    Store store = null;
		    String subject = null;
		    Flag flag = null;
		    try 
		    {
		      Properties props = System.getProperties();
		      props.setProperty("mail.store.protocol", mail_protocol );
		      props.setProperty("mail."+mail_protocol+".usesocketchannels", "true");
		      props.setProperty("mail."+mail_protocol+".port",mail_port);
		      props.setProperty("mail."+mail_protocol+".ssl.trust",mail_host);
		      props.setProperty("mail."+mail_protocol+".timeout", "10000");
		      Session session = Session.getDefaultInstance(props, null);
		      ExecutorService es = Executors.newCachedThreadPool();
		      final IdleManager idleManager = new IdleManager(session, es);
		      try 
			    {
		      store = session.getStore(mail_protocol);
		      store.connect(mail_host ,mail_username,mail_password);
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


		      for (int i=messages.length-1; i >0;i--) 
		      {

		        Message msg =  messages[i];

		        subject = msg.getSubject();
 
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
