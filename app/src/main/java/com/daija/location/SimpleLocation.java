package com.daija.location;

public class SimpleLocation
{
  private double pointX;
  private double pointY;
  
  public SimpleLocation() {}
  
  public SimpleLocation(double paramDouble1, double paramDouble2)
  {
    this.pointX = paramDouble1;
    this.pointY = paramDouble2;
  }
  
  public double getPointX()
  {
    return this.pointX;
  }
  
  public double getPointY()
  {
    return this.pointY;
  }
  
  public void setPointX(double paramDouble)
  {
    this.pointX = paramDouble;
  }
  
  public void setPointY(double paramDouble)
  {
    this.pointY = paramDouble;
  }
}
