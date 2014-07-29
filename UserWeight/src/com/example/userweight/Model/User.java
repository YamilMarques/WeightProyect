package com.example.userweight.Model;

public class User {

	private int Id,WeightType;
	private String Name,Mail,ContactEmail;
	
	public User(int id,String name,String mail,String contemail,int weighttype){
		Id = id;
		Name = name;
		Mail = mail;
		ContactEmail = contemail;
		WeightType = weighttype;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getWeightType() {
		return WeightType;
	}

	public void setWeightType(int weightType) {
		WeightType = weightType;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getMail() {
		return Mail;
	}

	public void setMail(String mail) {
		Mail = mail;
	}

	public String getContactEmail() {
		return ContactEmail;
	}

	public void setContactEmail(String contactEmail) {
		ContactEmail = contactEmail;
	}
	
}
