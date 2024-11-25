package com.kloudly.demo;

import javax.crypto.DecapsulateException;
import javax.crypto.KEM;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Java21NewFeaturesDemo {
    private static PublicKey publicKey;
    public static void main(String[] args) throws Exception{
        // Uncomment the methods below to test Java 21 features
        // recordPattern();
        // patternMatchingSwitch();
        sequencedCollections();
        // kemApi();
        // virtualThreads();
        // virtualThreadHighConcurrency();
    }

    public static void recordPattern(){
        Student student = new Student("John","Doe");
        //before Java 21
        recordBeforeJava21(student);
        //with Java 21
        recordWithJava21(student);

    }

    private static void recordBeforeJava21(Object object){
        if(object instanceof Student student){
            String firstName = student.firstName();
            String lastName = student.lastName();
            System.out.println(firstName +" " +lastName);
        }
    }

    private static void recordWithJava21(Object object){
        if(object instanceof Student(String firstName, String lastName)){
            System.out.println(firstName +" " +lastName);
        }
    }

    public static void patternMatchingSwitch(){
        Integer i = 2;
        switchBeforeJava21(i);
        switchWithJava21(i);
    }

    private static void switchBeforeJava21(Object object){
        if(object == null){
            System.out.println("I'm null");
        }else if(object instanceof Integer i){
            if(i % 2 == 0)
                System.out.println("I'm even");
            else
                System.out.println("I'm odd");
        }else if(object instanceof String s){
            System.out.println("I'm a String : "+s);
        }else{
            System.out.println("I'm a different object");
        }
    }

    private static void switchWithJava21(Object object){
        switch (object){
            case null -> System.out.println("I'm null");
            case Integer i when i%2 == 0 -> System.out.println("I'm even");
            case Integer i when i%2 == 1 -> System.out.println("I'm odd");
            case String s -> System.out.println("I'm a String : "+s);
            default -> System.out.println("I'm a different object");
        }
    }

    public static void sequencedCollections(){
        sequencedList();
        sequencedSet();
        sequencedMap();
    }

    private static void sequencedList(){
        System.out.println("*****Sequenced List*****");
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        String firstItem = list.getFirst();
        String lastItem = list.getLast();
        List<String> reversedList = list.reversed();
        System.out.println(firstItem); //Hello
        System.out.println(lastItem);  // World
        System.out.println(reversedList); //[World, Hello]
    }

    private static void sequencedSet(){
        System.out.println("*****Sequenced Set*****");
        SequencedSet<String> set = new LinkedHashSet<>();
        set.add("Hello");
        set.add("World");
        String firstItem = set.getFirst();
        String lastItem = set.getLast();
        SequencedSet<String> reversedSet = set.reversed();
        System.out.println(firstItem);  //Hello
        System.out.println(lastItem);  // World
        System.out.println(reversedSet); //[World, Hello]
    }
    private static void sequencedMap(){
        System.out.println("*****Sequenced Map*****");
        SequencedMap<String,String> map = new LinkedHashMap<>();
        map.put("Hello","Hello");
        map.put("World","World");
        Map.Entry<String,String> firstItem = map.firstEntry();
        Map.Entry<String,String> lastItem = map.lastEntry();
        SequencedMap<String,String> reversedMap = map.reversed();
        System.out.println(firstItem);  //Hello=Hello
        System.out.println(lastItem);  //World=World
        System.out.println(reversedMap);  //{World=World, Hello=Hello}
    }


    public static void virtualThreads(){
        Thread thread = Thread.ofVirtual().start(() -> {
            System.out.println("Hello from a virtual thread!");
        });

        System.out.println(thread.isVirtual());//True
    }

    public static void virtualThreadHighConcurrency(){
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 1_000_000).forEach(i ->
                    executor.submit(() -> {
                        // Simulate a task
                        System.out.println("Task " + i + " running");
                    })
            );
        }

    }

    public static void kemApi() throws NoSuchAlgorithmException, DecapsulateException, IOException, InvalidKeyException {
        // Receiver side
        KeyPairGenerator g = KeyPairGenerator.getInstance("X25519");
        KeyPair kp = g.generateKeyPair();
        publishKey(kp.getPublic());

        // Sender side
        KEM kemServer = KEM.getInstance("DHKEM");
        PublicKey publicKeyReceiver = retrieveKey();
        KEM.Encapsulator e = kemServer.newEncapsulator(publicKeyReceiver);
        KEM.Encapsulated enc = e.encapsulate();
        SecretKey sharedSecretSender = enc.key();
        byte[] keyEncapsulationMessage = enc.encapsulation();
        //...The key encapsulation message should be sent to the receiver here
        // using for example a method like sendBytes(keyEncapsulationMessage)

        // Receiver side
        // ...The receiver should receive the key encapsulation message  here
        // using for example a method like receiveBytes()
        byte[] em = keyEncapsulationMessage;
        KEM kemR = KEM.getInstance("DHKEM");
        KEM.Decapsulator d = kemR.newDecapsulator(kp.getPrivate());
        SecretKey sharedSecretReceiver = d.decapsulate(em);

        // sharedSecretSender and sharedSecretReceiver will be identical
        assert Arrays.equals(sharedSecretSender.getEncoded(),sharedSecretReceiver.getEncoded());
    }

    private static PublicKey retrieveKey() {
        //This is just a dummy method to simulate a public key reception
        return Java21NewFeaturesDemo.publicKey;
    }

    private static void publishKey(PublicKey publicKey) {
        //This is just a dummy method to simulate a public key publishing
        Java21NewFeaturesDemo.publicKey = publicKey;
    }
}

record Student(String firstName, String lastName){}
