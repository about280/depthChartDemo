package com.about280.ashleigh.sports.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepthChartDto {
	private String sport;
	private Map<String, List<Long>> positionPlayersMap;
}
