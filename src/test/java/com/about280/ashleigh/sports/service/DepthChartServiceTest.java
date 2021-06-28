package com.about280.ashleigh.sports.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.about280.ashleigh.sports.config.SportProperties;
import com.about280.ashleigh.sports.dto.DepthChartDto;
import com.about280.ashleigh.sports.persistance.dao.DepthChartPositionRepository;
import com.about280.ashleigh.sports.persistance.dao.PlayerRepository;
import com.about280.ashleigh.sports.persistance.model.DepthChartPosition;
import com.about280.ashleigh.sports.persistance.model.Player;

@ExtendWith(SpringExtension.class)
public class DepthChartServiceTest {

	@Mock
	private DepthChartPositionRepository depthChartPositionRepository;
	
	@Mock
	private PlayerRepository playerRepository;
	
	private SportProperties sportProperties;
	
	private DepthChartService unitUnderTest;
	
	@BeforeEach
	public void setup()
	{
		sportProperties = new SportProperties();
		Map<String, List<String>> sportPositions = new HashMap<String, List<String>>(); 
		sportPositions.put("NFL", Collections.singletonList("WR"));
		sportProperties.setSportPositions(sportPositions);
		unitUnderTest = new DepthChartService(playerRepository,
				depthChartPositionRepository, sportProperties);
	}
	
	private void setupDepthChartData() {
		Player player = new Player();
		player.setId(1l);
		player.setName("Bob");
		Player player2 = new Player();
		player2.setId(2l);
		player2.setName("Alice");
		Player player3 = new Player();
		player3.setId(3l);
		player3.setName("Tom");
		
		List<DepthChartPosition> depthChartPositions = new ArrayList<DepthChartPosition>();
		DepthChartPosition depthChartPosition = new DepthChartPosition();
		depthChartPosition.setDepth(2);
		depthChartPosition.setPlayer(player);
		depthChartPosition.setPosition("WR");
		depthChartPosition.setSport("NFL");
		depthChartPositions.add(depthChartPosition);
		
		DepthChartPosition depthChartPosition2 = new DepthChartPosition();
		depthChartPosition2.setDepth(0);
		depthChartPosition2.setPlayer(player2);
		depthChartPosition2.setPosition("WR");
		depthChartPosition2.setSport("NFL");
		depthChartPositions.add(depthChartPosition2);
		
		DepthChartPosition depthChartPosition3 = new DepthChartPosition();
		depthChartPosition3.setDepth(1);
		depthChartPosition3.setPlayer(player3);
		depthChartPosition3.setPosition("WR");
		depthChartPosition3.setSport("NFL");
		depthChartPositions.add(depthChartPosition3);
		
		when(depthChartPositionRepository.findBySport(eq("NFL"))).thenReturn(depthChartPositions);
		when(depthChartPositionRepository.findBySportAndPosition(eq("NFL"), eq("WR"))).thenReturn(depthChartPositions);
	}
	@Test
	void testGetFullDepthChart() {
		setupDepthChartData();
		DepthChartDto dto = unitUnderTest.getFullDepthChart("NFL");
		assertEquals(1, dto.getPositionPlayersMap().size());
		assertEquals(2, dto.getPositionPlayersMap().get("WR").get(0));
		assertEquals(3, dto.getPositionPlayersMap().get("WR").get(1));
		assertEquals(1, dto.getPositionPlayersMap().get("WR").get(2));
		
	}
	
	@Test
	void testGetPlayersUnderPlayerInDepthChart() {
		setupDepthChartData();
		DepthChartDto dto = unitUnderTest.getPlayersUnderPlayerInDepthChart("NFL", 2l, "WR");
		assertEquals(1, dto.getPositionPlayersMap().size());
		assertEquals(3, dto.getPositionPlayersMap().get("WR").get(0));
		assertEquals(1, dto.getPositionPlayersMap().get("WR").get(1));
	}
	
	@Test
	void testAddPlayerToDepthChart() {
		setupDepthChartData();
		when(playerRepository.findById(any())).thenReturn(Optional.of(new Player()));
		unitUnderTest.addPlayerToDepthChart("NFL", 2l, "WR", 0);
		verify(depthChartPositionRepository, atLeast(2)).save(any());
	}

}
