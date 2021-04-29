package com.learning.andfix.web;


import com.learning.andfix.Replace;

public class Calculator {
    //修复类
    @Replace(clazz = "com.learning.andfix.Calculator",method = "div")
    public int div(int a,int b)
    {
        return 10;
    }

}
