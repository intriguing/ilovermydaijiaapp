package com.daijia.utils;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;

public class ThreadManager
{
  private static final int ALIVE_THREAD_SIZE = 2;
  private static final int MAX_QUEUE_LENGTH = 128;
  private static final int MAX_THREAD_SIZE = 5;
  private static final int THREAD_ALIVE_SECONDS = 60;
  private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 5, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue(128), new ThreadPoolExecutor.DiscardOldestPolicy());
  
  public static final ThreadPoolExecutor getPool()
  {
    return threadPool;
  }
}
