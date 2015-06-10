package com.expensetracker.gui.pojo;

import java.io.Serializable;

public class Job implements Serializable {

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = -4159517912120931624L;
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((job == null) ? 0 : job.hashCode());
		result = prime * result + ((jobType == null) ? 0 : jobType.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Job other = (Job) obj;
		if (job == null) {
			if (other.job != null)
				return false;
		} else if (!job.equals(other.job))
			return false;
		if (jobType == null) {
			if (other.jobType != null)
				return false;
		} else if (!jobType.equals(other.jobType))
			return false;
		return true;
	}

	private String jobType;
	private Object job;
	
	public Job(String jobType, Object job) {
		super();
		this.jobType = jobType;
		this.job = job;
	} 
	
	/**
	 * @return the jobType
	 */
	public String getJobType() {
		return jobType;
	}
	/**
	 * @param jobType the jobType to set
	 */
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	/**
	 * @return the job
	 */
	public Object getJob() {
		return job;
	}
	/**
	 * @param job the job to set
	 */
	public void setJob(Object job) {
		this.job = job;
	}

	@Override
	public String toString() {
		return "Job [jobType=" + jobType + ", job=" + job + "]";
	}
}
