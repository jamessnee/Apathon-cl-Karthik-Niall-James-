package uk.ac.cam.jas250.statsgame;

import java.io.IOException;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.servlet.http.*;
import java.util.logging.Logger;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class StatsGameServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(StatsGameServlet.class
			.getName());

	public final String HEADER_RQTYPE = "rqtype";
	public final String HEADER_NEWPLAYER = "newplayer";
	public final String HEADER_POSTCODE = "postcode";
	public final String HEADER_PLAYERID = "playerid";
	public final String HEADER_GAMEID = "gameid";
	public final String HEADER_METRICID = "metricid";
	public final String HEADER_DIRECTION = "direction";

	public enum REQUEST_TYPE {
		CREATE_PLAYER, UPDATE_PLAYER, JOIN_LOBBY, JOIN_GAME, GET_CARD, CHOOSE, LEAVE_GAME, QUERY_POSTCODE, QUERY_STATE
	};

	
	/*
	 * NOTE TO KARTHIK
	 * I was expecting to send a url to the server from an app like:
	 * http://www.trumpus-appathon.appspot.com/statsgame?playerId=12345,gameId=67789,metricChoice=<somethingToDoWithTheMetric>
	 * I would then expect JSON data to be returned. Is this what you were thinking?
	 */

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		resp.setContentType("application/json");

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Lobby l;
			try {
			l = pm.getObjectById(Lobby.class, Lobby.SINGLETON_KEY);
			} catch (JDOObjectNotFoundException e) {
				l = new Lobby();
				pm.makePersistent(l);
			
			}
			
			
			REQUEST_TYPE rq = REQUEST_TYPE.class.getEnumConstants()[Integer.parseInt(req.getParameter(HEADER_RQTYPE))];
			Gson gson = new Gson();
			Player p;

			if (rq == null) {
				resp.getWriter().print(gson.toJson("No request"));
				return;
			}
	
			System.out.print(gson.toJson(l));
			System.out.print(rq);
			switch (rq) {
			case CREATE_PLAYER:
				String name = req.getParameter(HEADER_NEWPLAYER);
				String postcode = req.getParameter(HEADER_POSTCODE);
				p = new Player(name, postcode);
				pm.makePersistent(p);
				resp.getWriter().print(gson.toJson(p.getKey().getId()));
				break;
			case JOIN_LOBBY:
				
				p = pm.getObjectById(Player.class, Long.parseLong(req
						.getParameter(HEADER_PLAYERID)));
				l.join(p);

				System.out.print(gson.toJson(l));
				resp.getWriter().print(gson.toJson(l));
				break;
			case JOIN_GAME:
				p = pm.getObjectById(Player.class, Long.parseLong(req
						.getParameter(HEADER_PLAYERID)));
				resp.getWriter().print(gson.toJson(l.joinGame(p.getKey())));
				break;

			case GET_CARD:
				p = pm.getObjectById(Player.class, Long.parseLong(req
						.getParameter(HEADER_PLAYERID)));
				Game g = pm.getObjectById(Game.class, Long.parseLong(req.getParameter(HEADER_GAMEID)));
				Card c = g.getCard(p.getKey());
				resp.getWriter().print(gson.toJson(c));
				break;
			case CHOOSE:
				p = pm.getObjectById(Player.class, Long.parseLong(req
						.getParameter(HEADER_PLAYERID)));
				g = pm.getObjectById(Game.class, Long.parseLong(req.getParameter(HEADER_GAMEID)));
				Metric m = pm.getObjectById(Metric.class, Long.parseLong(req.getParameter(HEADER_METRICID)));
				
				int dir = Integer.parseInt(req.getParameter(HEADER_DIRECTION));
				g.choose(m, dir);
				resp.getWriter().print(gson.toJson(null));
				break;
			case QUERY_POSTCODE:
				postcode = req.getParameter(HEADER_POSTCODE);
				resp.getWriter().print(gson.toJson(NeighbourhoodStatQuery.getAreasFromPostcode(postcode)));
				break;
				
			case QUERY_STATE:
				p = pm.getObjectById(Player.class, Long.parseLong(req
						.getParameter(HEADER_PLAYERID)));
				g = pm.getObjectById(Game.class, Long.parseLong(req.getParameter(HEADER_GAMEID)));
				resp.getWriter().print(gson.toJson(g.getState()));
				break;
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
	}
}
