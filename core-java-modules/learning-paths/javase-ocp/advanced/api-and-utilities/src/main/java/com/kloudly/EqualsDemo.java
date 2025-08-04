
package com.kloudly;

/**
 * Demo class for properly overriding equals() and hashCode() in Java.
 */
public class EqualsDemo {

	public static void main(String[] args){
		Person first = new Person("John Doe", 35);
		Person second = new Person("John Snow", 30);
		boolean isSame = first.equals(second);//false
		System.out.println(isSame);
	}
}

