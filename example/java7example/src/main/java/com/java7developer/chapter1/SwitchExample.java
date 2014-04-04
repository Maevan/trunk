package com.java7developer.chapter1;

public class SwitchExample {
	public void printDay(String dayOfWeek) {
		switch (dayOfWeek) {
		case "Sunday":
			System.out.println("Dimanche");
			break;
		case "Monday":
			System.out.println("Lundi");
			break;
		case "Tuesday":
			System.out.println("Mardi");
			break;
		case "Wednesday":
			System.out.println("Mercredi");
			break;
		case "Thursday":
			System.out.println("Jeudi");
			break;
		case "Friday":
			System.out.println("Vendredi");
			break;
		case "Saturday":
			System.out.println("Samedi");
			break;
		default:
			System.out.println("Error : '" + dayOfWeek + "' is not a day of week");
			break;
		}
	}
}
