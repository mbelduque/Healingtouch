package com.healingtouch.model;

import java.util.Date;

/** @author Mauricio Belduque */
public class Patient {

	private String names;
	private String surnames;
	private long document_id;
	private String phone;
	private Date birthdate;
	private String address;

	public Patient() {
	}

	public Patient(String names, String surnames, long document_id, String phone, Date birthdate, String address) {
		this.names = names;
		this.surnames = surnames;
		this.document_id = document_id;
		this.phone = phone;
		this.birthdate = birthdate;
		this.address = address;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getSurnames() {
		return surnames;
	}

	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}

	public long getDocument_id() {
		return document_id;
	}

	public void setDocument_id(long document_id) {
		this.document_id = document_id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
