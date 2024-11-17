package com.kloudly.exception;

import java.util.ArrayList;
import java.util.List;

public class ConcurrentModificationExceptionDemo {
    public static void main(String[] args) {
        //Uncomment any of the method below to reproduce the ConcurrentModificationException
        inMultiThreadedEnv();
        //inSingleThreadedEnv();
    }

    public static void inMultiThreadedEnv(){
        List<String> list = new ArrayList<>();
        for(int i=0; i<10; i++){
            list.add("hello");
        }
        Thread simpleThread = new Thread(new SimpleThread(list));
        simpleThread.start();
        for(String value: list){//ConcurrentModificationException due to ListIterator#next() method
            System.out.println(value);
        }
    }

    public static void inSingleThreadedEnv(){
        List<String> list = new ArrayList<>();
        list.add("hello");
        for(String value : list){// ConcurrentModificationException
            System.out.println(value);
            list.add("world");
        }
    }
}

class SimpleThread implements Runnable{
    private List<String> list;
    public SimpleThread(List list){
        this.list = list;
    }

    @Override
    public void run() {
        this.list.add("world");
    }
}