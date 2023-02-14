package Controller;

//import java.util.Collection;
import java.util.List;
//import java.util.Objects;

//import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;


public class SocialMediaController {
    
    
	AccountService accountService;
      MessageService messageService;

    public SocialMediaController(){
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }

    
    public Javalin startAPI(){
        Javalin app = Javalin.create();
        
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
	  app.post("/messages", this::postMessageHandler);
      app.get("/accounts", this::getAllAccountsHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByMessageIdHandler);
         app.delete("/messages/{message_id}", this::deleteMessageGivenMessageIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageGivenMessageIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUserGivenAccountIdHandler);
        return app;
    }

    
    public void getMessageByMessageIdHandler(Context ctx){
		int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message messages = messageService.getMessageById(message_id);
        if(messages != null){
            ctx.status(200);
            ctx.json(messages);
        }
    }
        
   private void postAccountHandler(Context ctx) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    Account account = mapper.readValue(ctx.body(), Account.class);
    Account addedAccount = accountService.addAccount(account);
    if(addedAccount!=null ){
	ctx.status(200);
        ctx.json(mapper.writeValueAsString(addedAccount));
        
    }else{  
        ctx.status(400);
    }
}  

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    Account account = mapper.readValue(ctx.body(), Account.class);
    List <Account> allAccounts= accountService.getAllAccounts();
    boolean Login=accountService.verifyLogin(account);
   
    boolean notUser=accountService.IncorrectUser(account);
   
    Account addedLoginAccount = accountService.addLoginAccount(account);
    
    if((allAccounts==null || allAccounts.isEmpty() &&(notUser==true) ) ||(notUser==false)&&(Login==false)){
        
       
        ctx.status(401);
        ctx.json(mapper.writeValueAsString(addedLoginAccount));
    }
    else if(addedLoginAccount!=null){
        ctx.status(200);
        ctx.json(mapper.writeValueAsString(addedLoginAccount));
           }
          
       
} 
 
    
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            Message message= mapper.readValue(ctx.body(), Message.class);
    	Message addedMessage = messageService.addMessage(message);
           
            if(addedMessage!=null){
    		ctx.status(200);
                ctx.json(mapper.writeValueAsString(addedMessage));
                
            }else{  
                ctx.status(400);
            }
        }
        private void getAllAccountsHandler(Context ctx) {
            List<Account> accounts = accountService.getAllAccounts();
            ctx.json(accounts);
            ctx.status(200);
        }
    
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
		ctx.status(200);
    }
    private void updateMessageGivenMessageIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updated = messageService.updateMessage(message_id,message);
        System.out.println(updated);
        if(updated == null){
            ctx.status(400);
            ctx.json(mapper.writeValueAsString(updated));
            
        }else{
            ctx.json(mapper.writeValueAsString(updated));
            ctx.status(200);
           }   }



private void deleteMessageGivenMessageIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
          System.out.println("Delete") ;    
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        boolean messageExists = messageService.verifyMessageId(message_id);
        System.out.println(messageExists);
        Message messageById= messageService.getMessageById(message_id);
        System.out.println(messageById);
        Message deleted = messageService.deleteMessage(message_id);
        System.out.println(deleted);
        

        if((messageExists==true)&&(messageById==null)) {
            ctx.status(200);
          //  ctx.json(mapper.writeValueAsString(deleted));
            
        }
        else if(messageById!=null){
            ctx.json(mapper.writeValueAsString(messageById));
          //  ctx.json(mapper.writeValueAsString(deleted));
        }
       else

       ctx.json(mapper.writeValueAsString(deleted));

    }


    private void getAllMessagesFromUserGivenAccountIdHandler(Context ctx)throws JsonProcessingException{
        ObjectMapper mapper=new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
	int id = Integer.parseInt(ctx.pathParam("account_id"));
    List<Message> messages=messageService.getAllMessagesPostedBy(id);
    ctx.json(mapper.writeValueAsString(messages));
        
    }

    }

