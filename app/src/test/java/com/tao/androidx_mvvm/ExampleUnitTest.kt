package com.tao.androidx_mvvm

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        Thread(Runnable {
            println(Thread.currentThread().name + " 1")
            Thread(Runnable {
                println(Thread.currentThread().name + " 2")
                Thread(Runnable {
                    println(Thread.currentThread().name + " 3")
                    Thread(Runnable {
                        println(Thread.currentThread().name + " 4")
                        Thread(Runnable {
                            println(Thread.currentThread().name + " 5")
                        }).start()
                    }).start()
                }).start()
            }).start()
        }).start()
    }
}
