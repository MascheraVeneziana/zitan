package org.mascheraveneziana.zitan.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="meeting")
public class Meeting extends TimestampEntity{
	
	@Id
	@GeneratedValue
	@Getter @Setter
	private Long id;
	@Getter @Setter
	private String name;
	@Getter @Setter
	private String room;
	
}
