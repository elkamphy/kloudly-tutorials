package com.kloudly.classes;

public class ClassAndObjectDemo {
    public static void main(String[] args) {
        demoObject();
        demoMultipleObjects();
    }

    static void classDemoSingleFile(){
        Student student = new Student("Emily", 20);
        student.addCourse("Computer Science");
        student.addCourse("Mathematics");
        student.addCourse("Philosophy");
    }

    static void demoObject(){
        CarV1 myCar = new CarV1();
        myCar.brand = "Toyota";
        myCar.speed = 50;
        myCar.accelerate();
        System.out.println(myCar.speed); // Output: 60
    }

    static void demoMultipleObjects(){
        CarV1 firstCarObject = new CarV1();
        firstCarObject.brand = "Toyota";
        firstCarObject.speed = 50;
        firstCarObject.accelerate();
        System.out.println(firstCarObject.speed); // Output: 60
        CarV1 secondCarObject = new CarV1();
        secondCarObject.brand = "Hyundai";
        secondCarObject.speed = 50;
        secondCarObject.decelerate();
        System.out.println(secondCarObject.speed); // Output: 40
    }

    static void defaultConstructorBad(){
        //Car bad = new Car(); //won't compile
    }

    static void demoConstructor(){
        Car car1 = new Car("Honda", 40);
    }
}

class CarV1 {
    String brand;
    int speed;

    void accelerate() {
        speed += 10;
    }

    void decelerate() {
        speed -= 10;
    }
}


class Car {
    private String brand;
    private int speed;

    // Constructor
    public Car(String brand, int speed) {
        this.brand = brand;
        this.speed = speed;
    }

    // Default Constructor
    public Car(){
        this("Toyota",10);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if (speed >= 0) {
            this.speed = speed;
        }
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}