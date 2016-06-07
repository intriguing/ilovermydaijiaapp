package com.daijia.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapProvider
{
  public static Bitmap getBitmap(Context paramContext, InputStream paramInputStream)
  {
    return BitmapFactory.decodeStream(paramInputStream);
  }
  
  /* Error */
 public static Bitmap getBitmap(Context paramContext, String paramString)
  {
	 return BitmapFactory.decodeFile(paramString);
  }
  
  public static Bitmap getBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    return Bitmap.createScaledBitmap(paramBitmap, paramInt1, paramInt2, true);
  }
  
  public static Bitmap getBitmap(String paramString)
  {
    return BitmapFactory.decodeFile(paramString);
  }
  
  public static long getBitmapLength(Context paramContextt, Bitmap paramBitmap)
  {
    if (paramBitmap != null) {
      try
      {
       String paramContext = paramContextt.getCacheDir() + File.separator + "t";
        FileOutputStream localFileOutputStream = new FileOutputStream(paramContext);
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        paramBitmap.compress(Bitmap.CompressFormat.JPEG, 100, localByteArrayOutputStream);
        localFileOutputStream.write(localByteArrayOutputStream.toByteArray());
        localFileOutputStream.close();
        long l = new File(paramContext).length();
        return l;
      }
      catch (Exception paramContext)
      {
        paramContext.printStackTrace();
      }
    }
    return 0L;
  }
  
  public static Size getBitmapSize(String paramString)
  {
    Object localObject = new BitmapFactory.Options();
    ((BitmapFactory.Options)localObject).inJustDecodeBounds = true;
    Bitmap paramStringt = BitmapFactory.decodeFile(paramString, (BitmapFactory.Options)localObject);
    localObject = new Size(((BitmapFactory.Options)localObject).outWidth, ((BitmapFactory.Options)localObject).outHeight);
    if ((paramStringt != null) && (!paramStringt.isRecycled())) {
      paramStringt.recycle();
    }
    return (Size)localObject;
  }
  
  public static Bitmap getScaleBitmap(String paramString, float paramFloat)
  {
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inJustDecodeBounds = false;
    localOptions.inSampleSize = Math.round(paramFloat);
    return BitmapFactory.decodeFile(paramString, localOptions);
  }
  
  public static Bitmap getScaleBitmap(String paramString, int paramInt1, int paramInt2)
  {
	Bitmap paramStringt;
    Bitmap localBitmap = BitmapFactory.decodeFile(paramString);
    if (localBitmap.getWidth() == paramInt1)
    {
      paramStringt = localBitmap;
      if (localBitmap.getHeight() == paramInt2) {}
    }
    else
    {
      paramStringt = Bitmap.createScaledBitmap(localBitmap, paramInt1, paramInt2, true);
    }
    return paramStringt;
  }
  
  public static void saveImage(Context paramContext, String paramString, Bitmap paramBitmap)
    throws IOException
  {
    saveImage(paramContext, paramString, paramBitmap, 100);
  }
  
  public static void saveImage(Context paramContext, String paramString, Bitmap paramBitmap, int paramInt)
    throws IOException
  {
    if (paramBitmap == null) {
      return;
    }
    FileOutputStream paramContextt = paramContext.openFileOutput(paramString, 0);
    ByteArrayOutputStream paramStringt = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.JPEG, paramInt, paramStringt);
    paramContextt.write(paramStringt.toByteArray());
    paramContextt.close();
  }
  
  public static void saveJpgImageToSD(String paramString, Bitmap paramBitmap, int paramInt)
    throws IOException
  {
    if (paramBitmap != null)
    {
      FileOutputStream paramStringt = new FileOutputStream(paramString);
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      paramBitmap.compress(Bitmap.CompressFormat.JPEG, paramInt, localByteArrayOutputStream);
      paramStringt.write(localByteArrayOutputStream.toByteArray());
      paramStringt.close();
    }
  }
  
  public static void savePNGImageToSD(String paramString, Bitmap paramBitmap, int paramInt)
    throws Exception
  {
    if (paramBitmap != null)
    {
      FileOutputStream paramStringt = new FileOutputStream(paramString);
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      paramBitmap.compress(Bitmap.CompressFormat.JPEG, paramInt, localByteArrayOutputStream);
      paramStringt.write(localByteArrayOutputStream.toByteArray());
      paramStringt.close();
    }
  }
  
/*  public static Bitmap toRoundCorner(Bitmap paramBitmap, int paramInt)
  {
    Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    Paint localPaint = new Paint();
    Rect localRect = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
    RectF localRectF = new RectF(localRect);
    float f = paramInt;
    localPaint.setAntiAlias(true);
    localCanvas.drawARGB(0, 0, 0, 0);
    localPaint.setColor(-12434878);
    localCanvas.drawRoundRect(localRectF, f, f, localPaint);
    localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    localCanvas.drawBitmap(paramBitmap, localRect, localRect, localPaint);
    return localBitmap;
  }*/
}
