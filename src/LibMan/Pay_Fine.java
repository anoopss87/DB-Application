package LibMan;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Pay_Fine")
public class Pay_Fine extends HttpServlet {
	private static final long serialVersionUID = 1L;       
   
    public Pay_Fine()
    {
        super();        
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");  
        PrintWriter pw = response.getWriter(); 
        Connection conn=null;       
        SQLConnection SQLConn = new SQLConnection();               
        String loanid ="";  
        
        int i = 0;
        String msg ="";
                
    	String selected[] = request.getParameterValues("fine");
    	
    	if(selected == null)
    	{
    		msg="Fine Payment Failed!!!!!!! Please select some entry.....";
    		pw.println("<font size='6' color=red>" + msg + "</font>");
    		return;
    	}
    	
    	if(selected.length > 1)
    	{
    		msg="Fine Payment Failed!!!!!!! Please pay fine for one card at a time";
    		pw.println("<font size='6' color=red>" + msg + "</font>");
    		return;
    	}
    	
    	try
        {        	
    		conn = SQLConn.getSQLConnection();
        	
        	//get all loan id for the particular card number
        	PreparedStatement pst =(PreparedStatement) conn.prepareStatement("SELECT loanid from BOOK_LOAN WHERE cardnum = " + selected[0] + " AND datein IS NOT NULL AND datein > duedate");
        	        	
        	ResultSet rs = pst.executeQuery();   
        	
        	if(!rs.next())
        	{
        		msg="Fine Payment Failed!!!!!!! Book has not checked in yet.....";
        		pw.println("<font size='6' color=red>" + msg + "</font>");             		
    		
        		response.setContentType("text/html");
    		 
        		pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
        		return;
        	}
            do
            {            	
            	loanid = rs.getString(1);
            	
            	//update paid status for each loan id whose paid status is NULL(not paid)
            	pst =(PreparedStatement) conn.prepareStatement("UPDATE FINES SET paid = '1' where loan_id = " + loanid + " AND paid IS NULL");
	        	
            	i = pst.executeUpdate();
            	
            	if(i != 0)
            	{  
            		msg="Fine has been paid and updated for the loan id " + loanid;
            		pw.println("<font size='6' color=blue>" + msg + "</font>");             		
        		
            		response.setContentType("text/html");
        		 
            		pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");            		
            	}  
	        	else
	        	{  
	        		msg="Fine payment was not successful for the loan id " + loanid + "..........Fine is already paid ";
	        		pw.println("<font size='6' color=red>" + msg + "</font>");
	        		
	        		response.setContentType("text/html");
	        		pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");	        		
	        	}            	
            }while (rs.next());
        	pst.close();
        }
       
        catch (Exception e)
        {  
        	pw.println(e);  
        }  		
	}
}
