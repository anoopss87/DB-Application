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


@WebServlet("/Fine_Process")
public class Fine_Process extends HttpServlet {
	private static final long serialVersionUID = 1L;
           
    public Fine_Process() 
    {
        super();        
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		handleFine(request, response);
	}
	
	private void handleFine(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");  
        PrintWriter pw = response.getWriter(); 
        Connection conn=null;        
        SQLConnection SQLConn = new SQLConnection();
        String msg1 = "";
        String msg2 = "";        
       	 
    	String fname = request.getParameter("fname");
    	String lname = request.getParameter("lname");
    	String cardno = request.getParameter("cardnum");
    	String Action = request.getParameter("fine");
    	    	
    	String query = "";      	
    	
    	Statement st;
    	try
        {        	
    		conn = SQLConn.getSQLConnection();
        	ResultSet rs;
        	ArrayList<String> fine_tuple = null;
            ArrayList<ArrayList<String>> fine_list = new ArrayList<ArrayList<String>>();
            
          //Validate borrower card number. Use copy of card no without %
            if(!cardno.isEmpty())
            {
	        	query = "select count(*) from borrowers where cardnum =" + cardno;
	    		st = conn.createStatement();
	            rs = st.executeQuery(query);
	            if(rs.next())
	            {
	            	if(Integer.parseInt(rs.getString(1)) == 0)
	            	{
	            		msg1 = "Fine process failed!!!!!!!!!!!!!";
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
        	
        	query = "Select B.fname, B.lname, B.cardnum, SUM(F.fine_amt), F.paid from BOOK_LOAN L, BORROWERS B, FINES F where L.cardnum = B.cardnum AND F.loan_id = L.loanid";
        	        	
        	if(!(fname.isEmpty()))
        	{    		
        		fname = "%" + fname + "%";
        		query = query.concat(" AND B.fname LIKE '" + fname + "'");
        	}
        	
        	if(!(lname.isEmpty()))
        	{    		
        		lname = "%" + lname + "%";
        		query = query.concat(" AND B.lname LIKE '" + lname + "'");
        	}
        	
        	if(!(cardno.isEmpty()))
        	{        		
        		query = query.concat(" AND L.cardnum =" + cardno);
        	}
        	
        	//get all the paid fines records
        	if(Action.equals("Paid"))
        	{
        		query = query.concat(" AND F.paid IS NOT NULL");
        	}
        	//get all the unpaid fines records
        	else if(Action.equals("UnPaid"))
        	{
        		query = query.concat(" AND F.paid IS NULL");
        	}
        	//get both paid and unpaid fine records
        	else if(Action.equals("All"))
        	{
        		//do nothing
        	}
        	
        	//group the result by card number to calculate total fine per person
        	query = query.concat(" GROUP BY L.cardnum");
         	st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next())
            {
            	fine_tuple = new ArrayList<String>();            	 

            	fine_tuple.add(rs.getString(1));
            	fine_tuple.add(rs.getString(2));
            	fine_tuple.add(rs.getString(3));   
            	fine_tuple.add(rs.getString(4));
            	fine_tuple.add(rs.getString(5));
            	fine_list.add(fine_tuple);
            }
            request.setAttribute("fine_list", fine_list);
            RequestDispatcher view = request.getRequestDispatcher("/FineResult.jsp");
            view.forward(request, response);
            conn.close();        
        }  
    	catch (Exception e)
    	{  
    		pw.println(e);  
    	}
	}

}
