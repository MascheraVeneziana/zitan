package org.mascheraveneziana.zitan.domain;



import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "meeting")
@Getter
@Setter
@ToString
public class Meeting extends TimestampEntity{
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	//TODO:数字に特に意味はないので検討必要
	@Size(max = 50)
	private String name;
	
	@Column(name = "room")
	//実際の会議室名の最大31なので少し余裕を持って60
	@Size(max = 60)
	private String room;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "start_time")
	private Time startTime;
	
	@Column(name = "end_time")
	private Time endTime;
	
	@OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<User> member;
	
	public Meeting() {
		super();
	}
	
	public Meeting(String name, String room, Date date, Time startTime, Time endTime) {
		super();
		this.name = name;
		this.room = room;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		
	}
	
}


