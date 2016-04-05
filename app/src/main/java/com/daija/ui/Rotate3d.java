package com.daija.ui;


import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3d
  extends Animation
{
  private static final String TAG = "Rotate3d";
  private Camera mCamera;
  private float mCenterX;
  private float mCenterY;
  private float mFromDegree;
  private float mLeft;
  private float mToDegree;
  private float mTop;
  
  public Rotate3d(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.mFromDegree = paramFloat1;
    this.mToDegree = paramFloat2;
    this.mLeft = paramFloat3;
    this.mTop = paramFloat4;
    this.mCenterX = paramFloat5;
    this.mCenterY = paramFloat6;
  }
  
  protected void applyTransformation(float paramFloat, Transformation paramTransformation)
  {
    paramFloat = this.mFromDegree + (this.mToDegree - this.mFromDegree) * paramFloat;
    float f1 = this.mCenterX;
    float f2 = this.mCenterY;
    Matrix matrix = paramTransformation.getMatrix();
    if (paramFloat <= -76.0F)
    {
      this.mCamera.save();
      this.mCamera.rotateY(-90.0F);
      this.mCamera.getMatrix(matrix);
      this.mCamera.restore();
    }
    for (;;)
    {
    	matrix.preTranslate(-f1, -f2);
    	matrix.postTranslate(f1, f2);
      if (paramFloat >= 76.0F)
      {
        this.mCamera.save();
        this.mCamera.rotateY(90.0F);
        this.mCamera.getMatrix(matrix);
        this.mCamera.restore();
      }
      else
      {
        this.mCamera.save();
        this.mCamera.translate(0.0F, 0.0F, f1);
        this.mCamera.rotateY(paramFloat);
        this.mCamera.translate(0.0F, 0.0F, -f1);
        this.mCamera.getMatrix(matrix);
        this.mCamera.restore();
      }
    }
  }
  
  public void initialize(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.initialize(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mCamera = new Camera();
  }
}
