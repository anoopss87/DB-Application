package LibMan;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.exceptions.jdbc4.*;

@WebServlet(description = "Add a new borrower", urlPatterns = { "/Add_Borrower" })
public class Add_Borrower extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Add_Borrower() 
    {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		processBorrower(request, response);  
	}

	private void processBorrower(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");  
        PrintWriter pw = response.getWriter(); 
        Connection conn=null;
        SQLConnection SQLConn = new SQLConnection();
        
    	String fname = request.getParameter("fname");  
    	String lname = request.getParameter("lname");
    	String email = request.getParameter("email"); 
    	String address = request.getParameter("address");
    	String city = request.getParameter("city");
    	String state = request.getParameter("state");
    	String phone = request.getParameter("phone");    

        try
        {	
        	conn = SQLConn.getSQLConnection();
        	PreparedStatement pst =(PreparedStatement) conn.prepareStatement("insert into Borrowers(fname,lname,email, address, city, state, phone) values(?,?,?,?,?,?,?)");

        	if(fname.isEmpty())
        	{
        		pst.setString(1, null);
        	}
        	else
        	{
        		pst.setString(1,fname);
        	}
        	
        	if(lname.isEmpty())
        	{
        		pst.setString(2, null);
        	}
        	else
        	{
        		pst.setString(2,lname);
        	}
        	
        	if(email.isEmpty())
        	{
        		pst.setString(3, null);
        	}
        	else
        	{
        		if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)"))
        		{        			
            		pw.println("<font size='6' color=red> Invalid email!!!!!!!!!</font>"); 
            		return;
        		}
        		else
        		{
        			pst.setString(3,email);
        		}
        	}
        	
        	if(address.isEmpty())
        	{
        		pst.setString(4, null);
        	}
        	else
        	{
        		pst.setString(4,address);
        	}
        	
        	if(city.isEmpty())
        	{
        		pst.setString(5, null);
        	}
        	else
        	{
        		pst.setString(5,city);
        	}
        	
        	if(state.isEmpty())
        	{
        		pst.setString(6, null);
        	}
        	else
        	{
        		pst.setString(6,state);
        	}
        	if(phone.isEmpty())
        	{
        		pst.setString(7, null);
        	}
        	else
        	{       		
        		pst.setString(7,phone);        		
        	}
        	
        	int i = pst.executeUpdate();
        	
        	String msg=" ";        	
   		 	
        	if(i!=0)
        	{  
        		msg="Borrower has been added successfully";
        		pw.println("<font size='6' color=blue>" + msg + "</font>"); 
        		
        		response.setContentType("text/html");
        		 
        		pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
        	}  
        	else
        	{  
        		msg="Failed to insert the borrower";
        		pw.println("<font size='6' color=red>" + msg + "</font>");
        		
        		response.setContentType("text/html");
        		pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
        	}  
        	pst.close();
        }
        catch(MySQLIntegrityConstraintViolationException e)
        {     
        	String msg1 = "Add Operation Failed!!!!!!";
        	String msg2 = "Borrower cannot have same first name, last name and address";
            String msg4 = "Borrower's first name, last name, address cannot be empty";
        	
            if(fname.isEmpty() || lname.isEmpty() || address.isEmpty())
            {
            	pw.println("<body bgcolor=\"lightblue\">");            
            	pw.println("<font size='6' color=red>" + msg1 + "</font>"); 
            	pw.println("<br>"); 
            	pw.println("<font size='6' color=red>" + msg4 + "</font>");
            	pw.println("<br>");
            }
            else
            {        	
            	pw.println("<body bgcolor=\"lightblue\">");            
            	pw.println("<font size='6' color=red>" + msg1 + "</font>"); 
            	pw.println("<br>"); 
            	pw.println("<font size='6' color=red>" + msg2 + "</font>");
            	pw.println("<br>");
            }
        	
        	response.setContentType("text/html");
    		pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
        }
        catch (Exception e)
        {  
        	pw.println(e);  
        }
	}
}
