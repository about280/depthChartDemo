package com.about280.ashleigh.sports.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.about280.ashleigh.sports.dto.DepthChartDto;
import com.about280.ashleigh.sports.persistance.model.Player;
import com.about280.ashleigh.sports.service.DepthChartService;

@RestController
public class DepthChartController {

	@Autowired
	private DepthChartService depthChartService;

	@GetMapping(value = "/sports/{sportId}")
	public ResponseEntity<DepthChartDto> getFullDepthChart(@PathVariable String sportId) {
		DepthChartDto dto = depthChartService.getFullDepthChart(sportId);
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value = "/sports/{sportId}/player/{playerId}/position/{positionId}")
	public ResponseEntity<DepthChartDto> getPlayersUnderPlayerInDepthChart(@PathVariable String sportId,
			@PathVariable Long playerId,
			@PathVariable String positionId) {
		DepthChartDto dto = depthChartService.getPlayersUnderPlayerInDepthChart(sportId, playerId, positionId);
		return ResponseEntity.ok().body(dto);
	}
	
	@PutMapping(value = "/sports/{sportId}/player/{playerId}/position/{positionId}/{depth}")
	public ResponseEntity<Void> addPlayerToDepthChart(@PathVariable String sportId,
			@PathVariable Long playerId,
			@PathVariable String positionId,
			@PathVariable Integer depth) {
		depthChartService.addPlayerToDepthChart(sportId, playerId, positionId, depth);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(value = "/sports/{sportId}/player/{playerId}/position/{positionId}")
	public ResponseEntity<Void> removePlayerFromDepthChart(@PathVariable String sportId,
			@PathVariable Long playerId,
			@PathVariable String positionId) {
		depthChartService.removePlayerFromDepthChart(sportId, playerId, positionId);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(value = "/players/{playerName}")
	public ResponseEntity<Player> addPlayer(@PathVariable String playerName) {
		Player player = depthChartService.addPlayer(playerName);
		return ResponseEntity.created(URI.create("/players/" + player.getId())).body(player);
	}
	
	@GetMapping(value = "/players")
	public ResponseEntity<List<Player>> getPlayers() {
        return ResponseEntity.ok().body(depthChartService.getPlayers());
	}

}
