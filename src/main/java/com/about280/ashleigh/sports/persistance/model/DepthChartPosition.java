package com.about280.ashleigh.sports.persistance.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "DepthChartPosition")
public class DepthChartPosition {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String sport;
	private String position;
	private Integer depth;
	@ManyToOne(fetch = FetchType.EAGER)
	private Player player;
}
