package com.example.userweight.Model;

public class UserContainer {

	public static User user_logued = null;
	
	public static void UserAsingnation(User us){
		UserContainer.user_logued = us;
	}
	
	public static User GiveMeUser(){
		return UserContainer.user_logued;
	}
	
}
