package com.redhat.management.approval;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class Stage implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	private java.lang.String id;
	private java.lang.String uuid;
	private java.lang.String createdTime;

	private String groupId;

	public Stage() {
	}

	public java.lang.String getUuid() {
		return this.uuid;
	}

	public void setUuid(java.lang.String uuid) {
		this.uuid = uuid;
	}

	public java.lang.String getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(java.lang.String createdTime) {
		this.createdTime = createdTime;
	}

	public java.lang.String getId() {
		return this.id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(java.lang.String groupId) {
		this.groupId = groupId;
	}

	public Stage(java.lang.String id, java.lang.String uuid,
			java.lang.String createdTime, java.lang.String groupId) {
		this.id = id;
		this.uuid = uuid;
		this.createdTime = createdTime;
		this.groupId = groupId;
	}
	
	public String toString() {
	    return "\nStage:" +
	        "\n   id: " + id +
	        "\n   uuid: " + uuid +
	        "\n   group id: " + groupId +
	        "\n   createdTime: " + createdTime;
	}

}