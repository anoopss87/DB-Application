package LibMan;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;

@WebServlet("/Book_Loan")
public class Book_Loan extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    public Book_Loan()
    {
        super();       
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		handleCheckOut(request, response);		
	}	

	private void handleCheckOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");  
        PrintWriter pw = response.getWriter(); 
        Connection conn=null;
        SQLConnection SQLConn = new SQLConnection();         
        
       	String bookid = request.getParameter("bookid");  
    	String branchid = request.getParameter("branchid");
    	String cardnum = request.getParameter("cardnum");
    	String dateout = " ";
    	String duedate = " ";
    	
    	String msg1 = "";
    	String msg2 = "";
    	String query = "";    	
    	
    	//If one of the fields in the form is left blank
    	if(bookid.isEmpty() || branchid.isEmpty() || cardnum.isEmpty())
    	{
    		msg1 = "Check out Failed!!!!!!!!!!!!!";
        	msg2 = "No fields can be left blank. Please fill all the details......";
        	
        	pw.println("<body bgcolor=\"lightblue\">");            
        	pw.println("<font size='6' color=red>" + msg1 + "</font>"); 
        	pw.println("<br>"); 
        	pw.println("<font size='6' color=red>" + msg2 + "</font>");
        	pw.println("<br>");        	
        	pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
        	return;
    	}
    	
    	Statement st;
    	try
        {        	
    		conn = SQLConn.getSQLConnection();
        	ResultSet rs;
        	
        	//Validate borrower card number
        	query = "select count(*) from borrowers where cardnum =" + cardnum;
    		st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.next())
            {
            	if(Integer.parseInt(rs.getString(1)) == 0)
            	{
            		msg1 = "Check out Failed!!!!!!!!!!!!!";
                	msg2 = "Invalid card number";
                	
                	pw.println("<body bgcolor=\"lightblue\">");            
                	pw.println("<font size='6' color=red>" + msg1 + "</font>"); 
                	pw.println("<br>"); 
                	pw.println("<font size='6' color=red>" + msg2 + "</font>");
                	pw.println("<br>");            	
                	
                	pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
                	return;
            	}
            }
            
            //Validate branch Id
            query = "select count(*) from lib_branch where id =" + branchid;
    		st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.next())
            {
            	if(Integer.parseInt(rs.getString(1)) == 0)
            	{
            		msg1 = "Check out Failed!!!!!!!!!!!!!";
                	msg2 = "Invalid branch id";
                	
                	pw.println("<body bgcolor=\"lightblue\">");            
                	pw.println("<font size='6' color=red>" + msg1 + "</font>"); 
                	pw.println("<br>"); 
                	pw.println("<font size='6' color=red>" + msg2 + "</font>");
                	pw.println("<br>");            	
                	
                	pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
                	return;
            	}
            }
        	
        	//Number of books checked out by the borrower
        	query = "Select count(cardnum) from BOOK_LOAN L where L.cardnum = " + cardnum + " AND ISNULL(L.datein) GROUP BY cardnum";  
         	st = conn.createStatement();
            rs = st.executeQuery(query);
            int Borrower_Count = 0;
            if(rs.next())
            {
            	Borrower_Count = Integer.parseInt(rs.getString(1));
            }
            
            //If already 3 books are checked out for the borrower.
            if(Borrower_Count >= 3)
            {
            	msg1 = "Check out Failed!!!!!!!!!!!!!";
            	msg2 = "Borrower has reached the limit of 3 check outs!!!!!";
            	
            	pw.println("<body bgcolor=\"lightblue\">");            
            	pw.println("<font size='6' color=red>" + msg1 + "</font>"); 
            	pw.println("<br>"); 
            	pw.println("<font size='6' color=red>" + msg2 + "</font>");
            	pw.println("<br>");            	
            	
            	pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
            	return;
            }
            
            //Get the number of copies of the book id
            query = "SELECT numcopies from BOOK_COPIES C where C.bkid = '" + bookid + "' AND C.brchid = " + branchid;
            st = conn.createStatement();
            rs = st.executeQuery(query);
            int Book_Copies = 0;
            if(rs.next())
            {
            	Book_Copies = Integer.parseInt(rs.getString(1));
            }
            
            //Get number of checked out copies of the book id 
            query = "Select count(bokid) from BOOK_LOAN L where L.bokid = '" + bookid + "' AND L.brid = " + branchid + " AND ISNULL(L.datein) " + "GROUP BY bokid, brid";  
           	st = conn.createStatement();
            rs = st.executeQuery(query);
            int Book_Loan_Count = 0;
            if(rs.next())
            {
            	Book_Loan_Count = Integer.parseInt(rs.getString(1));
            }
            
            //If all the available copies are checked out or zero book copies in the branch.
            if(Book_Copies == Book_Loan_Count)
            {
            	msg1 = "Check out Failed!!!!!!!!!!!!!";
            	if(Book_Copies == 0)
            		msg2 = "This branch doesn't have this book.......";
            	else
            		msg2 = "All the copies of this book are checked out.Please try some other time!!!!!!!";
            	
            	pw.println("<body bgcolor=\"lightblue\">");            
            	pw.println("<font size='6' color=red>" + msg1 + "</font>"); 
            	pw.println("<br>"); 
            	pw.println("<font size='6' color=red>" + msg2 + "</font>");
            	pw.println("<br>");            	
            	
            	pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
            	return;
            }
            
            //Get date out and due date using system date.
            query = "select current_date()";  
           	st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.next())
            {
            	dateout = rs.getString(1);
            }
            
            query = "select ADDDATE(current_date(), 14)";  
           	st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.next())
            {
            	duedate = rs.getString(1);
            }
            
            //Insert the book loan data.
            query = "INSERT INTO BOOK_LOAN(bokid, brid, cardnum, dateout, duedate, datein) Values(?, ?, ?, ?, ? ,?)";
            PreparedStatement pst = conn.prepareStatement(query);
           	
            pst.setString(1, bookid);
            pst.setString(2, branchid);
            pst.setString(3, cardnum);
            pst.setString(4, dateout);
            pst.setString(5, duedate);
            pst.setString(6, null);
            
            int i = pst.executeUpdate();
            
            if(i!=0)
        	{  
        		msg1 ="Book checked out successfully";
        		pw.println("<font size='6' color=blue>" + msg1 + "</font>"); 
        		
        		response.setContentType("text/html");
        		 
        	    pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
        	}            
            pst.close();            
        }  
        catch (Exception e)
        {  
        	pw.println(e);  
        }
	}
}