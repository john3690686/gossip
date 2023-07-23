package cc.openhome.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class Login extends HttpServlet {
	private final String USERS = "D:\\\\John\\\\JavaWorkspace\\\\EclipseProject\\\\gossip\\\\src\\\\main\\\\webapp\\\\WEB-INF\\\\users";
	private final String SUCCESS_PATH = "member.html";
	private final String ERROR_PATH = "index.html";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		response.sendRedirect(login(username, password) ? SUCCESS_PATH : ERROR_PATH);

	}

	private boolean isCorrectPassword(String password, Path userhome) throws IOException {
		Path profile = userhome.resolve("profile");
		try (BufferedReader reader = Files.newBufferedReader(profile)) {
			String[] data = reader.readLine().split("\t");
			int encrypt = Integer.parseInt(data[1]);
			int salt = Integer.parseInt(data[2]);

			return password.hashCode() + salt == encrypt;
		}
	}

	private boolean login(String username, String password) throws IOException {
		Path userhome = Paths.get(USERS, username);

		return Files.exists(userhome) && isCorrectPassword(password, userhome);

	}

}
