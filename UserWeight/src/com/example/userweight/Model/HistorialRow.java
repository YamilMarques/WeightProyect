package com.example.userweight.Model;

public class HistorialRow {

	private int Id;
	private double Weight;
	private String Date;

	public HistorialRow(int id,String date,double weight){
		Id = id;
		Date = date;
		Weight = weight;
	}
	
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public double getWeight() {
		return Weight;
	}

	public void setWeight(double weight) {
		Weight = weight;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}
	
}
