package LibMan;

import java.io.InputStream;
import java.util.Properties;

public class ReadPropertyFile
{
	protected Properties prop = null;
	protected InputStream input = ReadPropertyFile.class.getClassLoader().getResourceAsStream("conf.properties");
	
	public ReadPropertyFile() throws Exception	
	{
		prop = new Properties();
		prop.load(input);
	}
	
	public String getUserName()
	{
		return prop.getProperty("username");
	}
	
	public String getPassword()
	{
		return prop.getProperty("password");
	}
	
	public String getUrl()
	{
		return prop.getProperty("url");
	}
	
	public String getDriver()
	{
		return prop.getProperty("driver");
	}
	
	public String getDbname()
	{
		return prop.getProperty("dbname");
	}
	
	public String getUrl_Dbname()
	{
		return prop.getProperty("driver") + prop.getProperty("dbname");
	}
}
