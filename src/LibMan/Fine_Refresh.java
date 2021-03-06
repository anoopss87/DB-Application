package LibMan;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Fine_Refresh")
public class Fine_Refresh extends HttpServlet {
	private static final long serialVersionUID = 1L;
         
    public Fine_Refresh()
    {
        super();        
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		fineRefresh(response);  
	}

	private void fineRefresh(HttpServletResponse response) throws IOException {
		response.setContentType("text/html");  
        PrintWriter pw = response.getWriter(); 
        Connection conn=null;        
        SQLConnection SQLConn = new SQLConnection();        
        String loanid = "";
        String diff1 = "";
        String diff2 = "";
        String diff = "";        
        String datein = "";
        String msg = "";
        
        try
        {        	
        	conn = SQLConn.getSQLConnection();
        	
        	//get all loan id whose due date has expired and fine has not been paid
        	String query = "select loanid, DATEDIFF(current_date(), duedate), DATEDIFF(datein, duedate), datein FROM book_loan L LEFT JOIN FINES F ON L.loanid = F.loan_id where (datein IS NOT NULL AND duedate < datein) OR (datein IS NULL AND duedate < current_date()) AND F.paid IS NULL"; 
        	
         	Statement st = conn.createStatement();
         	ResultSet rs = st.executeQuery(query);
         	boolean fineTable = false;
         	double fine = 0;
         	double fine_val = 0;
         	String fine_amt = "";
         	PreparedStatement pst;
         	ResultSet rs2;
            while (rs.next())
            {
            	loanid = rs.getString(1);
            	diff1 = rs.getString(2);
            	diff2 = rs.getString(3);
            	datein = rs.getString(4);            	
            	
            	if(datein == null)
            	{
            		//if the book is out then difference from current date
            		diff = diff1;
            	}
            	else
            	{
            		//if the book is checked in then difference from check in date
            		diff = diff2;
            	}            		
            	
            	//Check if there already exists a entry in fine table
            	query = "SELECT COUNT(*), fine_amt FROM FINES WHERE loan_id =" + loanid;
            	st = conn.createStatement();
             	rs2 = st.executeQuery(query);
             	if(rs2.next())
             	{
             		if(Integer.parseInt(rs2.getString(1)) > 0)
             		{
             			fineTable = true;
             			fine_amt = rs2.getString(2);
                 		fine_val = Double.parseDouble(fine_amt);
             		}            		
             	}           	
             	
             	//calculate fine
             	fine = Float.parseFloat(diff) * 0.25;                     	
             	
	            //if fine table already contain loan id then update fine amount
	            if(fineTable)
	            {	             	
	             	if(fine != fine_val)
	             	{
			            pst =(PreparedStatement) conn.prepareStatement("UPDATE FINES SET fine_amt =" + fine + " WHERE loan_id =" + loanid + " AND fine_amt != " + fine + " AND paid IS NULL");
			            int j = pst.executeUpdate();
			        			
			        	if(j != 0)
			            {  
			              	//do nothing
			            }  
			    	    else
			    	    {  
			    	     	msg="Failed to update fine table for loanid " + loanid;
			    	      	pw.println("<font size='6' color=red>" + msg + "</font>");
			    	        		
			    	       	response.setContentType("text/html");
			    	      	pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
			    	    }
	             	}
	           	}
	            else//otherwise add new entry in the fines table
	            {
	            	pst =(PreparedStatement) conn.prepareStatement("INSERT INTO FINES(loan_id, fine_amt) Values(" + loanid + "," + fine + ")");
	        		int j = pst.executeUpdate();
	        			
	        		if(j != 0)
	                {  
	                	//do nothing
	                }  
	    	        else
	    	        {  
	    	        	msg="Failed to insert fines table for loan id " + loanid;
	    	        	pw.println("<font size='6' color=red>" + msg + "</font>");
	    	        		
	    	        	response.setContentType("text/html");
	    	        	pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
	    	        }
	            }
	            fineTable = false;
            }
        	msg="Fine refresh completed";
        	pw.println("<font size='6' color=blue>" + msg + "</font>");
        		
        	response.setContentType("text/html");
        	pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
        }
        catch (Exception e)
        {  
        	pw.println(e);  
        }
	}
}
