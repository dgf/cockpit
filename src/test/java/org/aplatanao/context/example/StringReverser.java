package org.aplatanao.context.example;

public class StringReverser {

    public String reverse(String message) {
        return new StringBuilder(message).reverse().toString();
    }

}
