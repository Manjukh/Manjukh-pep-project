package Service;

import java.util.List;



import Model.Message;
import DAO.MessageDAO;

public class MessageService {
    public MessageDAO messageDAO;

   
    public MessageService(){
        messageDAO = new MessageDAO();
    }
    
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
    


    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
    

    public Message addMessage(Message message) {
	if((messageDAO.getMessageById(message.message_id))!=null ||(message.message_text==null||message.message_text.length()==0) ||(message.message_text.length() >255)||(message.posted_by==0))
        return null;
	else
      return messageDAO.insertMessage(message);
    }


    //checks for no message
    public boolean verifyMessageId(int message_id){
        
        if(messageDAO.CheckMessageIdExists(message_id)== false)
        return true;
        else 
              return false;

    }


    public Message updateMessage(int message_id, Message message){
     int mlength =   message.message_text.length();
     String messageString= message.getMessage_text();
	if((messageDAO.getMessageById(message_id)==null)|| ((messageString==null)||(messageString.length()==0)) || ((mlength==0) || (mlength>255)))
	return null;
    
    else
    messageDAO.updatePatchMessage(message_id, message);
	 return messageDAO.getMessageById(message_id);
    }
 
	
  //  public Message deleteMessage(int message_id){
        
  //      messageDAO.deleteMessage(message_id);
 //       return messageDAO.getMessageById(message_id);
        
    
 //   }

    public Message deleteMessage(int message_id){
        if(messageDAO.getMessageById( message_id)== null)
            return null;
        else
        { messageDAO.deleteMessage( message_id);
        return messageDAO.getMessageById( message_id);
        }
        }

public Message getMessageById(int message_id){
    if(messageDAO.getMessageById(message_id) != null){
	return messageDAO.getMessageById( message_id);}
    else
    return null;
    
}

    public List<Message> getAllMessagesWithAccountId(int id) {
        return messageDAO.getAllMessagesWithAccountId( id);
    }

    public List<Message> getAllMessagesPostedBy(int id) {
        return messageDAO.getAllMessagesPostedBy( id);
    }


}