
package com.kloudly;

/**
 * Demo class for properly overriding hashCode() and equals() in Java.
 */
public class HashCodeDemo {
	
	public static void main(String[] args){
		Person first = new Person("John Doe", 35);
		Person second = new Person("John Doe", 35);
		int firstHash = first.hashCode();
		int secondHash = second.hashCode();
		boolean isSame = first.equals(second);//true
		boolean equalHash = firstHash == secondHash ;//true
		System.out.println(isSame);
		System.out.println(equalHash);
	}
}
