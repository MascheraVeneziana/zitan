package org.mascheraveneziana.zitan.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name="user")
@Data
public class User {
	@Id
	@GeneratedValue
	@Column(name = "id")
    private Long id;
	
	@Column(name = "name")
	//Googleの名前サイズに合わせて60
	@Size(max = 60)
    private String name;
	
	@Column(name = "email")
	//Googleのアドレスサイズに合わせて64
	@Size(max = 64)
    private String email;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "meeting_id")
	private Meeting meeting;
	
	public User() {
	}
	
	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}

}
