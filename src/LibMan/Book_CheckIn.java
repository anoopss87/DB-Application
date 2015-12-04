package LibMan;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Book_CheckIn")
public class Book_CheckIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
           
    public Book_CheckIn()
    {
        super();        
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		handleCheckIn(request, response);		
	}

	private void handleCheckIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");  
        PrintWriter pw = response.getWriter(); 
        Connection conn=null;       
        
        SQLConnection SQLConn = new SQLConnection();
        String msg1 = "";
        String msg2 = "";
        
       	String bookid = request.getParameter("bookid");  
    	String fname = request.getParameter("fname");
    	String lname = request.getParameter("lname");
    	String cardno = request.getParameter("cardnum"); 
    	String cardnum = cardno;
    	
    	if(bookid.isEmpty() && fname.isEmpty() && lname.isEmpty() && cardno.isEmpty())
    	{
        	msg1 = "Retrieve Failed!!!!!!!!!!!!!";
        	msg2 = "Please fill in some fields to retrieve the checked out books......";
        	
        	pw.println("<body bgcolor=\"lightblue\">");            
        	pw.println("<font size='6' color=red>" + msg1 + "</font>"); 
        	pw.println("<br>"); 
        	pw.println("<font size='6' color=red>" + msg2 + "</font>");
        	pw.println("<br>");        	
        	pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
        	return;
    	}
    	    	
    	String query = "";    	
    	
    	if(bookid.isEmpty())
    	{
    		bookid = "%";
    	}
    	else
    	{
    		bookid = "%" + bookid + "%";
    	}
    	
    	if(fname.isEmpty())
    	{
    		fname = "%";
    	}
    	else
    	{
    		fname = "%" + fname + "%";
    	}
    	
    	if(lname.isEmpty())
    	{
    		lname = "%";
    	}
    	else
    	{
    		lname = "%" + lname + "%";
    	}
    	if(cardno.isEmpty())
    	{
    		cardno = "%";
    	}
    	else
    	{
    		cardno = "%" + cardno + "%";
    	}
    	
    	Statement st;
    	try
        {        	 
    		conn = SQLConn.getSQLConnection();
        	ResultSet rs;
        	ArrayList<String> loan_tuple = null;
            ArrayList<ArrayList<String>> loan_list = new ArrayList<ArrayList<String>>();
        	
            //Validate borrower card number. Use copy of card no without %
            if(!cardnum.isEmpty())
            {
	        	query = "select count(*) from borrowers where cardnum =" + cardnum;
	    		st = conn.createStatement();
	            rs = st.executeQuery(query);
	            if(rs.next())
	            {
	            	if(Integer.parseInt(rs.getString(1)) == 0)
	            	{
	            		msg1 = "Check in Failed!!!!!!!!!!!!!";
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
            }
        	
        	query = "Select loanid, bokid, brid, B.cardnum, dateout, duedate, datein from BOOK_LOAN L, BORROWERS B where L.cardnum = B.cardnum AND ISNULL(L.datein)";
        	
        	//If the fields are non empty then only add it to the query
        	if(!bookid.isEmpty())
        	{
        		query = query.concat(" AND L.bokid LIKE '" + bookid + "'");
        	}
        	if(!cardno.isEmpty())
        	{
        		query = query.concat(" AND L.cardnum LIKE '" + cardno + "'");
        	}
        	if(!fname.isEmpty())
        	{
        		query = query.concat(" AND B.fname LIKE '" + fname + "'");
        	}
        	if(!lname.isEmpty())
        	{
        		query = query.concat(" AND B.lname LIKE '" + lname + "'");
        	}
        	
         	st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next())
            {
            	loan_tuple = new ArrayList<String>();            	 

            	loan_tuple.add(rs.getString(1));
            	loan_tuple.add(rs.getString(2));
            	loan_tuple.add(rs.getString(3));   
            	loan_tuple.add(rs.getString(4));
            	loan_tuple.add(rs.getString(5));
            	loan_tuple.add(rs.getString(6));
            	loan_tuple.add(rs.getString(7));
            	loan_list.add(loan_tuple);
            }
            request.setAttribute("loan_list", loan_list);
            RequestDispatcher view = request.getRequestDispatcher("/LoanResult.jsp");
            view.forward(request, response);
            conn.close();        
        }  
    	catch (Exception e)
    	{  
    		pw.println(e);  
    	}
	}
}
