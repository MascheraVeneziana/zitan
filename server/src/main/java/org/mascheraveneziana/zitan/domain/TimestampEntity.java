package org.mascheraveneziana.zitan.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public abstract class TimestampEntity {
	public Timestamp updateTime;
	
	@Column(updatable=false)
	public Timestamp createdTime;
	
	@PrePersist
	public void prePersist() {
		Timestamp ts = new Timestamp((new Date()).getTime());
		this.createdTime = ts;
		this.updateTime = ts;
	}
	
	@PreUpdate
	public void preUpdate() {
		this.updateTime = new Timestamp((new Date()).getTime());
	}
}
