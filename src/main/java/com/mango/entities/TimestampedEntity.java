package com.mango.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.mango.exception.MangoException;

@MappedSuperclass
public class TimestampedEntity implements Serializable {

	private static final long serialVersionUID = -6408959677018347490L;

	@Column(name = "created_at")
	private Long createdTs;

	@Column(name = "updated_at")
	private Long updatedTs;

	/**
	 * @return the createdTs
	 */
	public Long getCreatedTs() {
		return createdTs;
	}

	/**
	 * @param createdTs
	 *            the createdTs to set
	 */
	public void setCreatedTs(Long createdTs) {
		// No need to set the current time explicitly. It will be set
		// automatically.
		this.createdTs = createdTs;
	}

	/**
	 * @return the updatedTs
	 */
	public Long getUpdatedTs() {
		return updatedTs;
	}

	/**
	 * @param updatedTs
	 *            the updatedTs to set
	 */
	public void setUpdatedTs(Long updatedTs) {
		this.updatedTs = updatedTs;
	}

	@PrePersist
	public void callBeforePersist() throws MangoException {
		this.updatedTs = System.currentTimeMillis();
		this.createdTs = System.currentTimeMillis();
	}

	@PreUpdate
	public void callBeforeUpdate() throws MangoException {
		this.updatedTs = System.currentTimeMillis();
	}
}
