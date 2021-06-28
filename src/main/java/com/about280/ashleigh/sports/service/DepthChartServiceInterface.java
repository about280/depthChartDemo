package com.about280.ashleigh.sports.service;

import java.util.List;

import com.about280.ashleigh.sports.dto.DepthChartDto;
import com.about280.ashleigh.sports.persistance.model.Player;

public interface DepthChartServiceInterface {
	
    public DepthChartDto getFullDepthChart(String sportId);
    
    public DepthChartDto getPlayersUnderPlayerInDepthChart(String sportId, Long playerId, String position);
    
    public void addPlayerToDepthChart(String sportId, Long playerId, String position, Integer depth);
    
    public void removePlayerFromDepthChart(String sportId, Long playerId, String position);
    
    public Player addPlayer(String playerName);
    
    public List<Player> getPlayers();
}
