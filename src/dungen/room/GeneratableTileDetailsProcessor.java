package dungen.room;

import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;
import dungen.Direction;
import dungen.Position;
import dungen.tile.Entity;
import dungen.lotto.Lotto;

/**
 * Processes and creates cell entities.
 */
public class GeneratableTileDetailsProcessor {
	
	/**
	 * Generate actual entities based on generatable entity information. 
	 * @param generatable The information regarding the generatable entities.
	 * @param random The RNG to use in resolving entity participants.
	 * @return Entities based on generatable entity information. 
	 */
	public static PositionedTileDetails process(JSONArray generatable, Random random) {
		// Create a map of tile positions to tile details.
		PositionedTileDetails positionedTileDetails = new PositionedTileDetails();
		
		for (int generatableIndex = 0; generatableIndex < generatable.length(); generatableIndex++) {
			processTileDetailsForNode(generatable.getJSONObject(generatableIndex), positionedTileDetails, random);
		}
		
		return positionedTileDetails;
	}
	
	/**
	 * Recursively process the tile detail for a node.
	 * @param node The node to process.
	 * @param positionedTileDetails Holder of tile details.
	 * @param random The RNG to use in picking winning participants.
	 */
	private static void processTileDetailsForNode(JSONObject node, PositionedTileDetails positionedTileDetails, Random random) {
		// If the node has an 'x' and 'y' property then we have reached an actual tile node.
		if (node.has("x") && node.has("y")) {
			// Get the absolute tile position.
			Position position = new Position(node.getInt("x"), node.getInt("y"));	
			
			// Get the tile details for the position.
			TileDetails tileDetails = positionedTileDetails.getDetails(position);
			
			// The node may have a name.
			if (node.has("name")) {
				tileDetails.setName(node.getString("name"));
			}
			
			// The node may have a direction.
			if (node.has("direction")) {
				tileDetails.setDirection(getDirection(node.getString("direction")));
			}
			
			// The node may have attributes.
			if (node.has("attributes")) {
				tileDetails.setAttributes(node.getJSONObject("attributes"));
			}
			
			// There may be entities attached to the tile.
			if (node.has("entities")) {
				// If we have a 'entities' array then we should process each one.
				JSONArray entitiesJSONArray = node.getJSONArray("entities");
				
				// Process each entity individually.
				for (int entitiesIndex = 0; entitiesIndex < entitiesJSONArray.length(); entitiesIndex++) {
					// Get the entity JSON.
					JSONObject entityJSON = entitiesJSONArray.getJSONObject(entitiesIndex);
					
					// Get the entity id.
					String entityId = entityJSON.getString("id");
					
					// Get the facing direction of the entity. This is optional.
					Direction entityDirection = entityJSON.has("direction") ? getDirection(entityJSON.getString("direction")) : Direction.UNKNOWN;
					
					// Add the entity to the list of entities associated with the tile position.
					tileDetails.getEntities().add(new Entity(entityId, entityDirection));
				}
			}
		} else if (node.has("tiles")) {
			// If we have a 'tiles' array then we should process each one.
			JSONArray tilesJSONArray = node.getJSONArray("tiles");
			
			// Process each one individually.
			for (int tileIndex = 0; tileIndex < tilesJSONArray.length(); tileIndex++) {
				processTileDetailsForNode(tilesJSONArray.getJSONObject(tileIndex), positionedTileDetails, random);
			}
		} else if (node.has("participants")) {
			// If we have a 'participants' array then we will be randomly selecting a winning entity.
			JSONArray participantsJSONArray = node.getJSONArray("participants");
			
			// There is nothing to do if there are no participants.
			if (participantsJSONArray.length() == 0) {
				return;
			}
			
			// Create a lotto with which to find a winning participating node.
			Lotto<JSONObject> lotto = new Lotto<JSONObject>(random);
			
			// Process each participant individually.
			for (int participantIndex = 0; participantIndex < participantsJSONArray.length(); participantIndex++) {
				// Get the participant.
				JSONObject participant = participantsJSONArray.getJSONObject(participantIndex);
				
				// Get the participants ticket count.
				int tickets = participant.has("tickets") ? participant.getInt("tickets") : 0;
				
				// Add the participant to the lotto.
				lotto.add(participant, tickets);
			}
			
			// Process the winning node.
			processTileDetailsForNode(lotto.draw(), positionedTileDetails, random);
		}
	}
	
	/**
	 * Get the Direction enum value based on its string value.
	 * @param direction The string value.
	 * @return The Direction enum value based on its string value.
	 */
	private static Direction getDirection(String direction) {
		return Direction.valueOf(direction.toUpperCase());
	}
}
