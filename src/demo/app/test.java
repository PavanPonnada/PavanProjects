package demo.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class test extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out=resp.getWriter();
		resp.setContentType("text/html");
		int select=Integer.parseInt(req.getParameter("s1"));
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "123456");
			
			switch(select) {
			case 1:	//For Insertion
				String ins1=req.getParameter("name").toString();
				String ins2=req.getParameter("mobile").toString();
				String ins3=req.getParameter("email").toString();
				String ins4=req.getParameter("password").toString();
				PreparedStatement stmt=con.prepareStatement("insert into Loan values(?,?,?,?)");  
				stmt.setString(1,ins1);
				stmt.setString(2,ins2);  
				stmt.setString(3,ins3);
				stmt.setString(4,ins4);
				stmt.executeUpdate();  
				/*out.println("Record inserted...........");
				out.println("<br><br><a href='index.html'>Back To Home</a>"); */
				out.print(ins1+" Insert Sucessfully..........");  
			    RequestDispatcher rdi=req.getRequestDispatcher("index.html");  
			    rdi.include(req, resp);
				
				
				break;
			case 2:		// For Updation
				String upd1=req.getParameter("name").toString();
				String upd2=req.getParameter("mobile").toString();
				String upd3=req.getParameter("email").toString();
				PreparedStatement upst=con.prepareStatement("Update Loan set mobile=?,email=? where name=?");  
				upst.setString(3,upd1);
				upst.setString(1,upd2);  
				upst.setString(2,upd3);
				upst.executeUpdate();  
				out.println("Record Updated...........");
				//out.println("<br><br><a href='index.html'>Back To Home</a>");
				RequestDispatcher rdu=req.getRequestDispatcher("index.html");
				rdu.include(req, resp);
				break;
			case 3:	//For Deletion
				String del1=req.getParameter("name").toString();
				PreparedStatement dest=con.prepareStatement("delete from Loan where name=?");  
				dest.setString(1,del1);
				dest.executeUpdate();  
				out.println("Record Deleted...........");
				//out.println("<br><br><a href='index.html'>Back To Home</a>");
				RequestDispatcher rdd=req.getRequestDispatcher("index.html");
						rdd.include(req, resp);
				break;
			case 4:	//For Selection/ Retrieve
				Statement sel=con.createStatement();
				ResultSet rs=sel.executeQuery("select * from Loan");
				out.println("<table border='1' align='center'><tr><th>Name</th><th>Mobile</th><th>Email</th><th>Password</th></tr>");
				while(rs.next()) {
					out.println("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td></tr>");
				}
				out.println("</table><br><br><a href='index.html' align='center'>Back To Home</a>");
				break;
			default:
				out.println("Select Valid Option........");
				out.println("<br><br><a href='index.html'>Back To Home</a>");
			}
			 
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
