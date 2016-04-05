package com.daijia.utils;

import android.content.Context;
import android.media.SoundPool;
import java.util.HashMap;
import java.util.Map;

public class SoundManager
{
  private Context context;
  private SoundPool soundPool;
  private Map<Integer, Integer> soundPoolMap;
  
  public SoundManager(Context paramContext)
  {
    this.context = paramContext;
    this.soundPool = new SoundPool(5, 3, 100);
    this.soundPoolMap = new HashMap();
    this.soundPoolMap.put(Integer.valueOf(1), Integer.valueOf(this.soundPool.load(paramContext, 2130968576, 1)));
  }
  
  public void playShakeSound()
  {
    this.soundPool.play(1, 100.0F, 100.0F, 0, 0, 1.0F);
  }
}