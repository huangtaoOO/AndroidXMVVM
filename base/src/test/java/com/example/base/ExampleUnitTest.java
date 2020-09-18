package com.example.base;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        A ab = new B();
        ab = new B();
    }
}

class A{

    static int C = 3;
    static {
        System.out.println(C);
    }
    static {
        System.out.println("1");
    }

    public A() {
        System.out.println("2");
    }
}

class B extends A{
    static int D = 4;
    static {
        System.out.println(4);
    }
    static {
        System.out.println("A");
    }

    public B() {
        System.out.println("B");
    }
}