package com.about280.ashleigh.sports.persistance.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.about280.ashleigh.sports.persistance.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long>  {

}
