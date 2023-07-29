package cc.openhome.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member.view")
public class memberView extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String LOGIN_PATH = "index.html";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	private String getUsername(HttpServletRequest request) {
		return (String) request.getSession().getAttribute("login");
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (request.getSession().getAttribute("login") == null) {
			response.sendRedirect(LOGIN_PATH);
			return;
		}

		String username = getUsername(request);

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String preBlabla = request.getParameter("blabla");

		String body_1 = "";
		String body_2 = "";

		body_1 = preBlabla == null ? "" : "訊息要140字以內";

		Map<Long, String> messages = (Map<Long, String>) request.getAttribute("messages");

		if (messages.isEmpty()) {
			body_2 = "<p>寫點什麼吧!</p><br>";
		} else {
			StringBuilder table = new StringBuilder();
			table.append("""
					<table border = '0' cellpadding='2' cellspacing='2'>
						<thead>
							<tr><th></th></tr>
						</thead>
						<tody>
					""");
			for (Map.Entry<Long, String> message : messages.entrySet()) {
				Long millis = message.getKey();
				String blabla = message.getValue();

				LocalDateTime dateTime = Instant.ofEpochMilli(millis).atZone(ZoneId.of("Asia/Taipei"))
						.toLocalDateTime();

				table.append("""
						<tr><td style='vertical-align: top;'>
						%s<br>
						%s<br>
						%s<br>
						<form method='post' action='del_message'>
							<input type='hidden' name='millis' value='%s'>
							<button type='submit'>刪除</button>
						</form>
						<hr></td></tr>
						""".formatted(username, blabla, dateTime, millis));
			}

			table.append("""
					</tbody>
					</table>
					""");

			body_2 = table.toString();

		}

		out.println("""
				<!DOCTYPE html>
				<html>
					<head>
						<meta charset='UTF-8'>
						<title>Gossip 微網誌</title>

					</head>
					<body>
						<div class='leftPanel'>
							<img src='images/caterpillar.jpg' alt='Goosip 微網誌'/><br>
							<a href='logout'>登出</a>
						</div>
						<form method='post' action='new_message'>
							分享新鮮事...<br>
							%s<br>
							<textarea cols = '60' rows='4' name='blabla'>%s</textarea><br>
							<button type='submit'>送出</button>
						</form>
						%s
					</body>
				</html>
				""".formatted(body_1, preBlabla, body_2));
	}

}
