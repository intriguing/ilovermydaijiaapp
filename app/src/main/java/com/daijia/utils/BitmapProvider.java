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
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore_0
    //   4: aconst_null
    //   5: astore_2
    //   6: new 26	java/io/FileInputStream
    //   9: dup
    //   10: aload_1
    //   11: invokespecial 29	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   14: astore_1
    //   15: aload_1
    //   16: invokestatic 17	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   19: astore_0
    //   20: aload_1
    //   21: invokevirtual 32	java/io/FileInputStream:close	()V
    //   24: aload_0
    //   25: areturn
    //   26: astore_0
    //   27: aload_2
    //   28: astore_1
    //   29: aload_0
    //   30: astore_2
    //   31: aload_1
    //   32: astore_0
    //   33: aload_2
    //   34: invokevirtual 35	java/io/FileNotFoundException:printStackTrace	()V
    //   37: aload_1
    //   38: invokevirtual 32	java/io/FileInputStream:close	()V
    //   41: aconst_null
    //   42: areturn
    //   43: astore_0
    //   44: aload_0
    //   45: invokevirtual 36	java/lang/Exception:printStackTrace	()V
    //   48: aconst_null
    //   49: areturn
    //   50: astore_2
    //   51: aload_3
    //   52: astore_1
    //   53: aload_1
    //   54: astore_0
    //   55: aload_2
    //   56: invokevirtual 37	java/lang/OutOfMemoryError:printStackTrace	()V
    //   59: aload_1
    //   60: invokevirtual 32	java/io/FileInputStream:close	()V
    //   63: aconst_null
    //   64: areturn
    //   65: astore_0
    //   66: aload_0
    //   67: invokevirtual 36	java/lang/Exception:printStackTrace	()V
    //   70: aconst_null
    //   71: areturn
    //   72: astore_1
    //   73: aload_0
    //   74: invokevirtual 32	java/io/FileInputStream:close	()V
    //   77: aload_1
    //   78: athrow
    //   79: astore_0
    //   80: aload_0
    //   81: invokevirtual 36	java/lang/Exception:printStackTrace	()V
    //   84: goto -7 -> 77
    //   87: astore_1
    //   88: aload_1
    //   89: invokevirtual 36	java/lang/Exception:printStackTrace	()V
    //   92: aload_0
    //   93: areturn
    //   94: astore_2
    //   95: aload_1
    //   96: astore_0
    //   97: aload_2
    //   98: astore_1
    //   99: goto -26 -> 73
    //   102: astore_2
    //   103: goto -50 -> 53
    //   106: astore_2
    //   107: goto -76 -> 31
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	110	0	paramContext	Context
    //   0	110	1	paramString	String
    //   5	29	2	localContext	Context
    //   50	6	2	localOutOfMemoryError1	OutOfMemoryError
    //   94	4	2	localObject1	Object
    //   102	1	2	localOutOfMemoryError2	OutOfMemoryError
    //   106	1	2	localFileNotFoundException	java.io.FileNotFoundException
    //   1	51	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   6	15	26	java/io/FileNotFoundException
    //   37	41	43	java/lang/Exception
    //   6	15	50	java/lang/OutOfMemoryError
    //   59	63	65	java/lang/Exception
    //   6	15	72	finally
    //   33	37	72	finally
    //   55	59	72	finally
    //   73	77	79	java/lang/Exception
    //   20	24	87	java/lang/Exception
    //   15	20	94	finally
    //   15	20	102	java/lang/OutOfMemoryError
    //   15	20	106	java/io/FileNotFoundException
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
