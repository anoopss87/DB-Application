package LibMan;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLConnection 
{
	ReadPropertyFile data;
	public SQLConnection()
	{
		
	}
	
	public Connection getSQLConnection() throws Exception
	{
		data = new ReadPropertyFile();
    	Class.forName(data.getDriver()).newInstance();  
    	return DriverManager.getConnection(data.getUrl() + data.getDbname() ,data.getUserName(), data.getPassword()); 
	}
}
