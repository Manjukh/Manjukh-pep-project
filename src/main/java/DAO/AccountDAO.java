package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Util.ConnectionUtil;


public class AccountDAO {

    
    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            
            String sql = "Select * from account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    
    public Account getAccountById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            String sql = "select * from account where account_id = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
               Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
         
            String sql = "Insert into account (username,password) values(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
           
            preparedStatement.setString(1,account.getUsername());
		preparedStatement.setString(2,account.getPassword());
            
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                int generated_account_id =  rs.getInt(1);
                return new Account(generated_account_id, account.getUsername(),account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


	public  boolean CheckLoginExists(String username,String password){
		boolean loginExists = false;
		Connection connection = ConnectionUtil.getConnection();
		try{
		String sql = "Select * from account where username=? and password=?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);
			ResultSet rs=preparedStatement.executeQuery();
		if(rs.next()) {
 		loginExists = true;

		}
	}catch(SQLException e){
            System.out.println(e.getMessage());
        }

	return loginExists;

}
public  Account getAccountWithLogin(String username,String password){
    
    Connection connection = ConnectionUtil.getConnection();
    try{
    String sql = "Select * from account where username=? and password=?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setString(1, username);
    preparedStatement.setString(2, password);
    ResultSet rs = preparedStatement.executeQuery();
    while(rs.next()){
       Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                rs.getString("password"));
        return account;
    }
}catch(SQLException e){
    System.out.println(e.getMessage());
}
return null;

}



	public  boolean CheckUsernameExists(String username){
		boolean UsernameExists = false;
		Connection connection = ConnectionUtil.getConnection();
		try{
		String sql = "Select * from account where username=? ";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, username);
		
			ResultSet rs=preparedStatement.executeQuery();
		if(rs.next()) {
 		UsernameExists = true;

		}
	}catch(SQLException e){
            System.out.println(e.getMessage());
        }

	return UsernameExists;

}
public  Account getAccountByUsername(String username){
    
    Connection connection = ConnectionUtil.getConnection();
    try{
    String sql = "Select * from account where username=? ";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setString(1, username);
    
    ResultSet rs = preparedStatement.executeQuery();
    while(rs.next()){
       Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                rs.getString("password"));
        return account;
    }
}catch(SQLException e){
    System.out.println(e.getMessage());
}
return null;

}
	
}	