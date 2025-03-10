package DAO;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;


public class MessageDAO {
    
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            
            String sql = "select * from message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message= new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
				rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }


	public List<Message> getAllMessagesWithAccountId(int id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            
            String sql = "select message.message_id,message.posted_by,message.message_text,message.time_posted_epoch from account  inner join message on account.account_id = message.posted_by";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message= new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
				rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    
    public Message getMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            String sql = "Select * from message where message_id=?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
              Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }



    
    public  boolean CheckMessageIdExists(int id){
		boolean Exists = false;
		Connection connection = ConnectionUtil.getConnection();
		try{
		String sql = "Select * from message where message_id=? ";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1,id);
		
			ResultSet rs=preparedStatement.executeQuery();
		if(rs.next()) {
 		Exists = true;

		}
	}catch(SQLException e){
            System.out.println(e.getMessage());
        }

	return Exists;

}
     public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            String sql = "Insert into message (posted_by,message_text,time_posted_epoch) values (?,?,?)" ;
           PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
         

            preparedStatement.setInt(1,message.getPosted_by());
		preparedStatement.setString(2,message.getMessage_text());
		preparedStatement.setLong(3,message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
           ResultSet rs = preparedStatement.getGeneratedKeys();
          
            if(rs.next()){
                int generated_message_id = rs.getInt(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(),message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }



	 
    public void updateMessage(int id,Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            String sql = "Update message set posted_by=?, message_text=?,time_posted_epoch=? where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,message.getPosted_by());
		preparedStatement.setString(2,message.getMessage_text());
		preparedStatement.setLong(3,message.getTime_posted_epoch());
		preparedStatement.setInt(4,id);
            preparedStatement.executeUpdate();
           
                      
              
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }





    public void updatePatchMessage(int id,Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            String sql = "Update message set  message_text=? where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            
		preparedStatement.setString(1,message.getMessage_text());
		
		preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
           
                      
              
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }

	 public void deleteMessage(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            String sql = "Delete from message  where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,message_id);
           int value= preparedStatement.executeUpdate();
           System.out.println(value);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }



        public Message getMessageByPostedby(int id){
            Connection connection = ConnectionUtil.getConnection();
            try {
                
                String sql = "Select * from message where posted_by=?";
                
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                  Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),rs.getString("message_text"),
                            rs.getLong("time_posted_epoch"));
                    return message;
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return null;
        }






        public List<Message> getAllMessagesPostedBy(int id){
            Connection connection = ConnectionUtil.getConnection();
            List<Message> messages = new ArrayList<>();
            try {
                
                String sql = "select * from message where posted_by =?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,id);
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    Message message= new Message(
                            rs.getInt("message_id"),
                            rs.getInt("posted_by"),
                    rs.getString("message_text"),
                            rs.getLong("time_posted_epoch"));
                    messages.add(message);
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return messages;
        }
    
}




    
