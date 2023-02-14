package Service;

import java.util.List;

import DAO.AccountDAO;
import Model.Account;


public class AccountService {
    private AccountDAO accountDAO;
    
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    

    public List<Account> getAllAccounts () {
        return accountDAO.getAllAccounts();
    }
    

    public Account addAccount(Account account) {
        String uname=account.getUsername();
        int length=account.getPassword().length();
         if((uname==null|| uname.length()==0) ||(length<4)||(accountDAO.getAccountByUsername(uname)!=null))
         return null;
         else 
		return accountDAO.insertAccount(account);
        
       
    }
    public Account addLoginAccount(Account account) {
        
        if((accountDAO.getAccountWithLogin(account.username, account.password))!=null)
		return accountDAO.getAccountWithLogin(account.username,account.password);
        else
        return null;
       
    }

    //check for non-existent user
    public boolean IncorrectUser(Account account) {
        
        if((accountDAO.CheckUsernameExists(account.username))==false)

        return true;
        else
        return false;
       
    }
	public boolean verifyLogin(Account account){
        
	if(accountDAO.CheckLoginExists(account.username,account.password)== false)
	return false;
	else 
          return true;

}
}