package telran.games;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import telran.games.dto.Move;
import telran.games.dto.MoveResult;
import telran.net.Protocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;
//boolean isGameOver(long gameId);
public class BullsCowsProtocol implements Protocol {
    BullsCowsService bullsCows;
    
    
	public BullsCowsProtocol(BullsCowsService bullsCows) {
		this.bullsCows = bullsCows;
	}

	@Override
	public Response getResponse(Request request) {
		String requestType = request.requestType();
		String requestData = request.requestData();
		Response response = null;
		try {
			response = switch(requestType) {
			case "createNewGame" -> createNewGame(requestData);
			case "getResults" -> getResults(requestData);
			case "isGameOver" -> isGameOver(requestData);
			default -> new Response(ResponseCode.WRONG_REQUEST_DATA, requestType);
			};
		} catch (Exception e) {
			response = new Response(ResponseCode.WRONG_REQUEST_DATA, e.getMessage());
		}
		return response;
	}

	private Response isGameOver(String requestData) {
		long id = Long.parseLong(requestData);
		boolean isGameOver = bullsCows.isGameOver(id); 
		return new Response(ResponseCode.OK, Boolean.toString(isGameOver));
	}

	private Response getResults(String requestData) {
		Move move = new Move(new JSONObject(requestData));
		List<MoveResult> res = bullsCows.getResults(move);
		return new Response(ResponseCode.OK, resultsToJSON(res));
	}

	private String resultsToJSON(List<MoveResult> res) {
		
		return res.stream().map(MoveResult::toString)
				.collect(Collectors.joining(";"));
	}

	private Response createNewGame(String requestData) {
		long gameId = bullsCows.createNewGame();
		return new Response(ResponseCode.OK, "" + gameId);
	}

}
