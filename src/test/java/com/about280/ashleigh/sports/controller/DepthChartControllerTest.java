package com.about280.ashleigh.sports.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.about280.ashleigh.sports.dto.DepthChartDto;
import com.about280.ashleigh.sports.service.DepthChartService;

@ExtendWith(SpringExtension.class)
public class DepthChartControllerTest {
	
	@Mock
	private DepthChartService depthChartService;
	
	@InjectMocks
	DepthChartController unitUnderTest = new DepthChartController();
	
	@Test
	void testGetFullDepthChartFromService() throws Exception {
		when(depthChartService.getFullDepthChart(eq("Test"))).thenReturn(new DepthChartDto());
		ResponseEntity<DepthChartDto> entity = unitUnderTest.getFullDepthChart("Test");
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}

	@Test
	void testGetPlayersUnderPlayerInDepthChartFromService() throws Exception {
		when(depthChartService.getPlayersUnderPlayerInDepthChart(eq("Test"), eq(1l), eq("WR"))).thenReturn(new DepthChartDto());
		ResponseEntity<DepthChartDto> entity = unitUnderTest.getPlayersUnderPlayerInDepthChart("Test", 1l, "WR");
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}
	
}
