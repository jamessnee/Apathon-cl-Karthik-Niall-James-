package uk.ac.cam.jas250.statsgame;

import java.io.IOException;
import javax.servlet.http.*;
import java.util.logging.Logger;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class StatsGameServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(StatsGameServlet.class.getName());
	
	Service s;
	
	public final String HEADER_RQTYPE = "rqtype";
	public final String HEADER_JSON = "json";
	public final String HEADER_PLAYERID = "playerid";
	public final String HEADER_METRICID = "metricid";
	public final String HEADER_DIRECTION = "direction";
	
	
	public enum REQUEST_TYPE { CREATE_PLAYER, UPDATE_PLAYER, JOIN_LOBBY, JOIN_GAME, GET_CARD, CHOOSE, LEAVE_GAME };
	
	public StatsGameServlet() {
		s = new Service();
		
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		REQUEST_TYPE rq = (REQUEST_TYPE) req.getAttribute(HEADER_RQTYPE);
		Gson gson = new Gson();
		Player p;
		
		switch (rq) {
		case CREATE_PLAYER:
			p = gson.fromJson(req.getParameter(HEADER_JSON), Player.class);
			s.createPlayer(p);
			break;
		case JOIN_LOBBY:
			p = s.getPlayer(req.getParameter(HEADER_PLAYERID));
			s.getLobby().join(p);
			break;
		case JOIN_GAME:
			p = s.getPlayer(req.getParameter(HEADER_PLAYERID));
			s.getLobby().getGame(p).getId();
			break;
		case GET_CARD:
			p = s.getPlayer(req.getParameter(HEADER_PLAYERID));
			Game g = s.getLobby().getPlayerGame(p);
			Card c = g.getCard(p);
			resp.getWriter().println(gson.toJson(c));
			break;
		case CHOOSE:
			p = s.getPlayer(req.getParameter(HEADER_PLAYERID));
			Game g = s.getPlayerGame(p);
			Metric m = s.getMetric(req.getParameter(HEADER_METRICID));
			int dir = Integer.parseInt(req.getParameter(HEADER_DIRECTION));
			Player winner = g.choose(m, dir);
			resp.getWriter().println(gson.toJson(winner));
			break;
		}
		
		resp.setContentType("text/html");
		resp.getWriter().println("<h1>Stats Game</h1>");
		
		resp.setContentType("text/plain");
		
		String areaId = NeighbourhoodStatQuery.getRegionFromPostcode("CB12BH", 1);
		log.warning("LOG:"+areaId);
	}
}
