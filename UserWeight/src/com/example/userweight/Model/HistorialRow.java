package com.example.userweight.Model;

public class HistorialRow {

	private int Id,Weight;
	private String Date;

	public HistorialRow(int id,String date,int weight){
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

	public int getWeight() {
		return Weight;
	}

	public void setWeight(int weight) {
		Weight = weight;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}
	
}
