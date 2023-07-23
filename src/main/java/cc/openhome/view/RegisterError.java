package cc.openhome.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register_error.view")
public class RegisterError extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();

		List<String> errors = (List<String>) request.getAttribute("errors");

		StringBuilder errorStringList = new StringBuilder();

		for (String error : errors) {
			errorStringList.append("<li>").append(error).append("</li>");
		}

		out.printf("""
				<!DOCTYPE html>
				<html>
					<head>
						<meta charset='UTF-8'>
						<title>表單填寫錯誤</title>
					</head>
				<body>
					<h1>表單填寫錯誤</h1>
					<ul style='color: rgb(255, 0, 0);'>
					%s
					</ul>
					<a href='register.html>返回註冊表單</a>
				</body>
				</html>
						""", errorStringList);

	}

}
