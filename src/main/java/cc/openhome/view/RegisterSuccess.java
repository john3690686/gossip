package cc.openhome.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register_success.view")
public class RegisterSuccess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		out.printf("""
				<!DOCTYPE html>
				<html>
					<head>
						<meta charset='UTF-8'>
						<title>成功送出表單</title>
					</head>
				<body>
					<h1>%s 成功送出表單</h1>
					<a href='index.html'>回首頁</a>
				</body>
				</html>
						""", request.getParameter("username"));
	}

}
