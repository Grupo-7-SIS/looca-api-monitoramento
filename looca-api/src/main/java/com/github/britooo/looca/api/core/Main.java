package com.github.britooo.looca.api.core;

public class Main {

    public static void main(String[] args) {
        Looca looca = new Looca();
        System.out.println(looca.getSistema());
        System.out.println("-----------------------");
        System.out.println(looca.getMemoria());
        System.out.println("-----------------------");
        System.out.println(looca.getProcessador());
        System.out.println("-----------------------");
    }
}
