package uk.ac.cam.jas250.statsgame;

import java.io.IOException;
import javax.servlet.http.*;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class StatsGameServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(StatsGameServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		resp.getWriter().println("<h1>Stats Game</h1>");
		
		resp.setContentType("text/plain");
		
		String areaId = NeighbourhoodStatQuery.getRegionFromPostcode("CB12BH", 1);
		log.warning("LOG:"+areaId);
	}
}
