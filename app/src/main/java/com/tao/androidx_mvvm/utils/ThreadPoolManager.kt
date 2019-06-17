package com.tao.androidx_mvvm.utils

import java.util.*
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author: tao
 * @time: 2019/6/12
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */

object ThreadPoolManager {

    private var fixedThreadPool: ThreadPoolExecutor? = null
    private var cachedThreadPool: ThreadPoolExecutor? = null
    var keepAliveTime = Long.MAX_VALUE
    const val maximumPoolSize = 9
    const val maximumTasksCount = 40
    private val waitingTasks = LinkedList<()->Unit>()
    private val tasksCount = AtomicInteger(0)
    private var isChecking = AtomicBoolean(false)


    @Synchronized
    private fun getFixedThreadPool(): ThreadPoolExecutor {
        if (fixedThreadPool == null){
            fixedThreadPool = ThreadPoolExecutor(1, 1,
                keepAliveTime, TimeUnit.MILLISECONDS, LinkedBlockingQueue<Runnable>()
            )
        }
        return fixedThreadPool!!
    }

    @Synchronized
    private fun getCachedThreadPool(): ThreadPoolExecutor {
        if (cachedThreadPool == null){
            cachedThreadPool = ThreadPoolExecutor(0, maximumPoolSize -1,
                keepAliveTime, TimeUnit.MILLISECONDS, SynchronousQueue<Runnable>()
            )
        }
        return cachedThreadPool!!
    }

    @Synchronized
    fun execute(command:()->Unit){
        if (doIfTasksCountIsLess(maximumPoolSize-1) {
                tasksCount.incrementAndGet()
                try {
                    getCachedThreadPool().execute {
                        run(command)
                    }
                } catch (e: RejectedExecutionException) {
                    tasksCount.decrementAndGet()
                    waitingTasks.add(0,command)
                    executeWaitingTasks()
                }
            }){}
        else if (waitingTasks.size<maximumTasksCount){
            waitingTasks.add(command)
            executeWaitingTasks()
        }
    }

    @Synchronized
    private fun doIfTasksCountIsLess(value:Int,block:()->Unit):Boolean{
        val result = tasksCount.get() < value
        if (result) block()
        return result
    }

    @Synchronized
    private fun executeWaitingTasks(){
        if (isChecking.compareAndSet(false, true)) {
            getFixedThreadPool().execute {
                while (isChecking.get()) {
                    if (doIfTasksCountIsLess(maximumPoolSize - 1) {
                            tasksCount.incrementAndGet()
                            val task = waitingTasks.removeFirst()
                            try {
                                getCachedThreadPool().execute {
                                    run(task)
                                }
                            } catch (e: RejectedExecutionException) {
                                tasksCount.decrementAndGet()
                                waitingTasks.add(0,task)
                            }
                        }) { }
                    isChecking.set(waitingTasks.size > 0)
                }
            }
        }
    }

    private fun run(command:()->Unit){
        command()
        tasksCount.decrementAndGet()
    }
}

