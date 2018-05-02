package org.mascheraveneziana.zitan.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
public class Meeting extends TimestampEntity {

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

   @Column(name = "description")
    @Size(max = 1000)
    private String description;

    @Column(name = "goal")
    @Size(max = 1000)
    private String goal;

	@Column(name = "date")
	private Date date;

	@Column(name = "start_time")
	private Time startTime;

	@Column(name = "end_time")
	private Time endTime;

	// TODO 結合はどうやるの？
	@OneToOne
	private User mainUser;

	@OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<User> members;

	@Column(name = "provider_event_id")
	@Size(max = 30)
	private String providerEventId;

	public Meeting() {
		super();
	}

	public Meeting(String name, String room, String description, String goal,
	        Date date, Time startTime, Time endTime, User mainUser, List<User> members, String providerEventId) {
		super();
		this.name = name;
		this.room = room;
		this.description = description;
		this.goal = goal;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.mainUser = mainUser;
		this.members = members;
		this.providerEventId = providerEventId;
	}

}


