package com.about280.ashleigh.sports.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.about280.ashleigh.sports.config.SportProperties;
import com.about280.ashleigh.sports.dto.DepthChartDto;
import com.about280.ashleigh.sports.exception.PlayerNotFoundException;
import com.about280.ashleigh.sports.exception.PositionNotFoundException;
import com.about280.ashleigh.sports.exception.SportNotFoundException;
import com.about280.ashleigh.sports.persistance.dao.DepthChartPositionRepository;
import com.about280.ashleigh.sports.persistance.dao.PlayerRepository;
import com.about280.ashleigh.sports.persistance.model.DepthChartPosition;
import com.about280.ashleigh.sports.persistance.model.Player;

@Service
public class DepthChartService implements DepthChartServiceInterface {

	private final DepthChartPositionRepository depthChartPositionRepository;
	
	private final PlayerRepository playerRepository;
	
	private final SportProperties sportProperties;

	@Autowired
	public DepthChartService(PlayerRepository playerRepository,
			DepthChartPositionRepository depthChartPositionRepository,
			SportProperties sportProperties) {
		this.playerRepository = playerRepository;
		this.depthChartPositionRepository = depthChartPositionRepository;
		this.sportProperties = sportProperties;
	}
	
    public DepthChartDto getFullDepthChart(String sportId) {   	
    	if (!sportProperties.getSportPositions().containsKey(sportId)) {
    		throw new SportNotFoundException();
    	}
    	
    	List<DepthChartPosition> depthChartPositions = depthChartPositionRepository.findBySport(sportId);
        Map<String, List<DepthChartPosition>> listOfDepthChartsByPositions = depthChartPositions.stream()
                .collect(Collectors.groupingBy(DepthChartPosition::getPosition));
        
        DepthChartDto depthChartDto = new DepthChartDto();
        Map<String, List<Long>> positionPlayersMap = new HashMap<String, List<Long>>();
        for (String position : listOfDepthChartsByPositions.keySet()) {
        	List<DepthChartPosition> depthChartsForPosition = listOfDepthChartsByPositions.get(position);
        	positionPlayersMap.put(position, getOrderListOfPlayers(depthChartsForPosition));
        }
        depthChartDto.setPositionPlayersMap(positionPlayersMap);
        depthChartDto.setSport(sportId);
        return depthChartDto;
	}
    
    private List<Long> getOrderListOfPlayers(List<DepthChartPosition> depthChartsForPosition) {
    	depthChartsForPosition.sort(Comparator.comparing(DepthChartPosition::getDepth));
    	return depthChartsForPosition.stream().map((depth) -> depth.getPlayer().getId())
    			.collect(Collectors.toList());
    }
    
    public DepthChartDto getPlayersUnderPlayerInDepthChart(String sportId, Long playerId, String position) {
    	if (!sportProperties.getSportPositions().containsKey(sportId)) {
    		throw new SportNotFoundException();
    	}
    	if (!sportProperties.getSportPositions().get(sportId).contains(position)) {
    		throw new PositionNotFoundException();
    	}
    	List<DepthChartPosition> depthChartPositions = depthChartPositionRepository.findBySportAndPosition(sportId, position);
    	List<Long> playerIds = getOrderListOfPlayers(depthChartPositions);
    	
    	int playerIndex = playerIds.indexOf(playerId);
    	if (playerIndex > -1) {
    		playerIds = playerIds.subList(playerIndex + 1, playerIds.size());
    	}
    	
    	DepthChartDto depthChartDto = new DepthChartDto();
        Map<String, List<Long>> positionPlayersMap = new HashMap<String, List<Long>>();
        positionPlayersMap.put(position, playerIds);
    	depthChartDto.setPositionPlayersMap(positionPlayersMap);
        depthChartDto.setSport(sportId);
        return depthChartDto;
    }
    
    public void addPlayerToDepthChart(String sportId, Long playerId, String position, Integer depth) {
    	if (!sportProperties.getSportPositions().containsKey(sportId)) {
    		throw new SportNotFoundException();
    	}
    	if (!sportProperties.getSportPositions().get(sportId).contains(position)) {
    		throw new PositionNotFoundException();
    	}
    	
    	Optional<Player> player = playerRepository.findById(playerId);
    	if (player.isEmpty()) {
    		throw new PlayerNotFoundException();
    	}
    	List<DepthChartPosition> depthChartPositions = depthChartPositionRepository.findBySportAndPosition(sportId, position);
    	depthChartPositions.sort(Comparator.comparing(DepthChartPosition::getDepth));
    	//see if there are any conflicts, if so push them all out by 1
    	List<DepthChartPosition> depthChartPositionsMatching = depthChartPositions.stream()
				.filter(chartEntry -> depth >= chartEntry.getDepth()).collect(Collectors.toList());
    	for (DepthChartPosition depthChartPositionToUpdate: depthChartPositionsMatching) {
    		depthChartPositionToUpdate.setDepth(depthChartPositionToUpdate.getDepth() + 1);
    		depthChartPositionRepository.save(depthChartPositionToUpdate);
		}
    	DepthChartPosition addPosition = new DepthChartPosition();
    	addPosition.setDepth(depth);
    	addPosition.setPosition(position);
    	addPosition.setPlayer(player.get());
    	addPosition.setSport(sportId);
    	depthChartPositionRepository.save(addPosition);
    }
    
    public void removePlayerFromDepthChart(String sportId, Long playerId, String position) {
    	if (!sportProperties.getSportPositions().containsKey(sportId)) {
    		throw new SportNotFoundException();
    	}
    	if (!sportProperties.getSportPositions().get(sportId).contains(position)) {
    		throw new PositionNotFoundException();
    	}
    	
    	Optional<Player> player = playerRepository.findById(playerId);
    	if (player.isEmpty()) {
    		throw new PlayerNotFoundException();
    	}
    	depthChartPositionRepository.deleteBySportAndPositionAndPlayer(sportId, position, player.get());
    }
    
    public Player addPlayer(String playerName) {
    	Player player = new Player();
    	player.setName(playerName);
    	return playerRepository.save(player);
    }
    
    public List<Player> getPlayers() {
    	return playerRepository.findAll();
    }

}
