package com.accomplice.jacoco;

public class Employee {
	
	private String eName;
	private int eAge;
	private int eSal;
	private int eDept;
	
	public Employee(String eName, int eAge, int eSal, int eDept) {
		super();
		this.eName = eName;
		this.eAge = eAge;
		this.eSal = eSal;
		this.eDept = eDept;
	}

	/**
	 * @return the eName
	 */
	public String geteName() {
		return eName;
	}

	/**
	 * @param eName the eName to set
	 */
	public void seteName(String eName) {
		this.eName = eName;
	}

	/**
	 * @return the eAge
	 */
	public int geteAge() {
		return eAge;
	}

	/**
	 * @param eAge the eAge to set
	 */
	public void seteAge(int eAge) {
		this.eAge = eAge;
	}

	/**
	 * @return the eDept
	 */
	public int geteDept() {
		return eDept;
	}

	/**
	 * @param eDept the eDept to set
	 */
	public void seteDept(int eDept) {
		this.eDept = eDept;
	}
}
