package com.about280.ashleigh.sports.persistance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.about280.ashleigh.sports.persistance.model.DepthChartPosition;
import com.about280.ashleigh.sports.persistance.model.Player;

public interface DepthChartPositionRepository extends JpaRepository<DepthChartPosition, Long>  {

	List<DepthChartPosition> findBySport(String sport);
	
	List<DepthChartPosition> findBySportAndPosition(String sport, String position);

	void deleteBySportAndPositionAndPlayer(String sport, String position, Player player);
}
