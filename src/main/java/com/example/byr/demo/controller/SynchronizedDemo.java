package com.example.byr.demo.controller;

/**
 * @ClassName: SynchronizedDemo
 * @Description: TODO
 * @Author: yanrong
 * @Date: 8/27/2019 11:56 AM
 * @Version: 1.0
 */
public class SynchronizedDemo {
    public static void main(String[] args) {
        synchronized (SynchronizedDemo.class) {
        }
        method();
    }
    private static void method() {
    }

}
