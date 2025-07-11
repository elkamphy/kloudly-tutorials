package com.kbytes.sealed_classes;

public class SealedClassesAndInterfaces {
    public static void main(String[] args){
        MathOperation operation = new Add(1,2);
        executeOperation(operation);
    }

    static void executeOperation(MathOperation operation) {
        switch (operation) {
            case Add add -> System.out.println(add.x() + add.y());
            case Subtract subtract -> System.out.println(subtract.x() - subtract.y());
            case Multiply multiply -> System.out.println(multiply.x() * multiply.y());
        }
    }
}

sealed interface MathOperation permits Add, Subtract, Multiply {}
record Add(int x, int y) implements MathOperation {}
record Subtract(int x, int y) implements MathOperation {}
record Multiply(int x, int y) implements MathOperation {}