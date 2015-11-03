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
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/Update_CheckIn")
public class Update_CheckIn extends HttpServlet {
	private static final long serialVersionUID = 1L;      
    
    public Update_CheckIn() 
    {
        super();        
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  
        PrintWriter pw = response.getWriter(); 
        Connection conn=null;       
        SQLConnection SQLConn = new SQLConnection();        
        String bookid= "";
        String branchid="";
        String cardnum = "";
        String duedate = "";
        String checkin = "";
        String loanid ="";
        String diff ="";
        String strcheckin = "(";
        int i = 0;
        String query = "";
        Statement st = null;
     	ResultSet rs = null;
     	String msg="";
    	String msg2="";
    	String datediff ="";
    	String datediff2 ="";
                
    	String selected[] = request.getParameterValues("loan");
    	String datein[] = request.getParameterValues("dateinValue");
    	String dateinVal[] = new String[10];
    	int count = 0;
    	boolean empty = true;    
    	
    	if(selected == null)
    	{
    		msg="Check in failed!!!!!!!!!......Please select some book to check in..........";
    		pw.println("<font size='6' color=red>" + msg + "</font>");
    		
    		response.setContentType("text/html");
    		pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
    		return;
    	}
    	
    	for(int k = 0; k < datein.length; ++k)
    	{    		
    		if(datein[k].length() > 0)
    		{    			
    			dateinVal[count] = datein[k].toString();        			
    			
    			if (!(dateinVal[count].matches("\\d{4}-\\d{2}-\\d{2}")))
    			{
    				msg="Check in failed......Invalid date!!!! Format is YYYY-MM-DD";
	        		pw.println("<font size='6' color=red>" + msg + "</font>");
	        		
	        		response.setContentType("text/html");
	        		pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
	        		return;
    			}
    			count++;
    			empty = false;
    		}    		
    	}
    	
    	if(count> 0 && count < selected.length)
    	{
    		msg="Check in failed......Feed the date in field!!!!";
    		pw.println("<font size='6' color=red>" + msg + "</font>");
    		
    		response.setContentType("text/html");
    		pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
    		return;
    	}
    	
    	if(count> 0 && count > selected.length)
    	{
    		msg="Check in failed......Date in entered for unselected book!!!!";
    		pw.println("<font size='6' color=red>" + msg + "</font>");
    		
    		response.setContentType("text/html");
    		pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
    		return;
    	}
    	
    	//if multiple books are selected to check in store in the form of (a, b, c...)
	    for(i = 0; i < (selected.length - 1); i++)
	    {
	    	strcheckin = strcheckin.concat(selected[i] + ",");
	    }
	    strcheckin = strcheckin.concat(selected[i] + ")");    	    	
	        	
        try
        {        	
        	conn = SQLConn.getSQLConnection();
        	PreparedStatement pst = null;
        	
        	if(!empty)
        	{
	        	for(int k = 0; k < selected.length; ++k)
	        	{
	        		query = "Select DATEDIFF(dateout,'" + dateinVal[k] + "'), DATEDIFF(current_date(),'" + dateinVal[k] + "') from BOOK_LOAN L where loanid = " + selected[k];  
	             	st = conn.createStatement();
	             	rs = st.executeQuery(query);
	             	 if (rs.next())
	                 {	             		
	             		datediff = rs.getString(1);	
	             		datediff2 = rs.getString(2);
	             		int idatediff = Integer.parseInt(datediff);
	             		int idatediff2 = Integer.parseInt(datediff2);
	             		
	             		if(idatediff > 0)
	             		{
	             			msg="Check in failed......Date in should be after date out!!!!";
	                		pw.println("<font size='6' color=red>" + msg + "</font>");
	                		
	                		response.setContentType("text/html");
	                		pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
	                		return;
	             		}
	             		
	             		if(idatediff2 < 0)
	             		{
	             			msg="Check in failed......Date in cannot be a future date!!!!";
	                		pw.println("<font size='6' color=red>" + msg + "</font>");
	                		
	                		response.setContentType("text/html");
	                		pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
	                		return;
	             		}
	                 }
	        	}
        	}
        	
        	if(empty)
        	{
        		pst =(PreparedStatement) conn.prepareStatement("UPDATE BOOK_LOAN SET datein = current_date() where datein IS NULL AND loanid IN " + strcheckin);
        		i = pst.executeUpdate();
        		
        		if(i == 0)
	        	{  
	        		msg="Failed to check in the book(s)!!!!!!!!! Book might have already checked in";
	        		pw.println("<font size='6' color=red>" + msg + "</font>");
	        		
	        		response.setContentType("text/html");
	        		pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
	        		return;
	        	}
        	}
        	else
        	{
	        	for(int m=0;m<selected.length;++m)
	        	{
	        		pst =(PreparedStatement) conn.prepareStatement("UPDATE BOOK_LOAN SET datein = '" + dateinVal[m] + "' where datein IS NULL AND loanid = " + selected[m]);
	        		i = pst.executeUpdate();
	        		
	        		if(i == 0)
		        	{  
		        		msg="Failed to check in the book(s)!!!!!!!!! Book might have already checked in";
		        		pw.println("<font size='6' color=red>" + msg + "</font>");
		        		
		        		response.setContentType("text/html");
		        		pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
		        	}
	        	}
        	}
        	
        	//get date for all the checked in books.
        	query = "Select bokid, brid, cardnum, duedate, datein, loanid, DATEDIFF(datein, duedate) from BOOK_LOAN L where loanid IN " + strcheckin;  
         	st = conn.createStatement();
         	rs = st.executeQuery(query);
         	boolean fineTable = false;
         	double fine_val = 0;
         	String fine_amt = "";
            while (rs.next())
            {
            	bookid = rs.getString(1);
            	branchid = rs.getString(2);
            	cardnum = rs.getString(3);
            	duedate = rs.getString(4);
            	checkin = rs.getString(5);
            	loanid = rs.getString(6);
            	diff = rs.getString(7);
            
            	int iDiff = Integer.parseInt(diff); 
            	
            	//Check if there already exists a entry in fine table
            	query = "SELECT COUNT(*), fine_amt FROM FINES WHERE loan_id =" + loanid;
            	st = conn.createStatement();
             	ResultSet rs2 = st.executeQuery(query);
             	if(rs2.next())
             	{
             		if(Integer.parseInt(rs2.getString(1)) > 0)
             		{
             			fineTable = true;
             			fine_amt = rs2.getString(2);
                 		fine_val = Double.parseDouble(fine_amt);
             		}
             	}
             	
             	//If date in is after the due date
            	if(iDiff > 0)
            	{
            		//calculate fine at 0.25$ per day
            		double fine = iDiff * (float)0.25 ;
            		String fineStr = String.valueOf(fine);
            		msg2 = "Penalty of " + fineStr + "$ has been added for late check in!!!!!!";
            		
            		//If there is fine table entry then update fine amount if new fine is different from already existing table fine
            		if(fineTable)
            		{
            			if((fine != fine_val))
            			{
	            			pst =(PreparedStatement) conn.prepareStatement("UPDATE FINES SET fine_amt =" + fine + " WHERE loan_id =" + loanid + " AND fine_amt != " + fine + " AND paid IS NULL");
	            			int j = pst.executeUpdate();
	            			
	            			if(j != 0)
	                    	{  
	                    		//do nothing
	                    	}  
	        	        	else
	        	        	{  
	        	        		msg="Failed to update fine table";
	        	        		pw.println("<font size='6' color=red>" + msg + "</font>");
	        	        		
	        	        		response.setContentType("text/html");
	        	        		pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
	        	        	}
            			}
            		}
            		else//otherwise create new entry for fines table
            		{
            			pst =(PreparedStatement) conn.prepareStatement("INSERT INTO FINES(loan_id, fine_amt) Values(" + loanid + "," + fine + ")");
            			int j = pst.executeUpdate();
            			
            			if(j != 0)
                    	{  
                    		//do nothing
                    	}  
        	        	else
        	        	{  
        	        		msg="Failed to insert fines table";
        	        		pw.println("<font size='6' color=red>" + msg + "</font>");
        	        		
        	        		response.setContentType("text/html");
        	        		pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
        	        	}
            		}
            	}
   		 	
            	if(i != 0)
            	{  
            		msg="The book with book id " + bookid + " checked in successfully at the branch id " + branchid;
            		pw.println("<font size='6' color=blue>" + msg + "</font>"); 
            		pw.println("<br><font size='6' color=blue>" + msg2 + "</font>");
        		
            		response.setContentType("text/html");
        		 
            		pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
            	}	        	
            	fineTable = false;
            }
        	pst.close();
        }
       
        catch (Exception e)
        {  
        	pw.println(e);  
        }  
	}
}
