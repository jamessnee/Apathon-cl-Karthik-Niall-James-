package uk.ac.cam.jas250.statsgame;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class StatsGameServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Stats Game");
	}
}
