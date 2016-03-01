/**
 * @(#)LineEquations.java
 *
 *
 * @author Burak Savcý
 * @version 1.00 2013/3/19
 */

//contains a class which calculates the intersetion point of two instances of itself
public class LineEquations {
	
	double a,b,c,m,x,y ;
	double intersectionX, intersectionY , Angle , determinant ;
	

    public LineEquations(double a, double b, double c) {
    	
    	this.a = a ;
    	this.b = b ;
    	this.c = c ;
    	doChanges() ;
    	
    }
    
    public LineEquations(double xa, double ya, double xb, double yb)
    {
    	this.a = ya - yb ;
    	this.b = xb - xa ;
    	this.c = xa * yb - xb * ya ;
    	doChanges() ;
    }
    
    public void doChanges() 
    {
    	m = (-1) * a / b ;
    	x = (-1) * c / a ;
    	y = (-1) * c / b ;
    }
    
    public double getXConstant()
    {
    	return a ;
    }
    
    public double getYConstant()
    {
    	return b ;
    }
    
    public double getConstant()
    {
    	return c ;
    }
    
    public void setXConstant(double a)
    {
    	this.a = a ;
    	doChanges() ;
    }
    
    public void setYConstant(double b)
    {
    	this.b = b ;
    	doChanges() ;
    }
    
    public void setConstant(double c)
    {
    	this.c = c ;
    	doChanges() ;
    }
    
    public double getSlope()
    {
    	return m ;
    }
    
    public double getHorizontalInt()
    {
    	return x ;
    }
    
    public double getVerticalInt()
    {
    	return y ;
    }   
    
    public double getDeterminant(LineEquations LE)
    {
    	determinant = (this.getXConstant()*LE.getYConstant()) - (this.getYConstant()*LE.getXConstant()) ;
    	return determinant ;
    }
			
	public double getIntersectionX(LineEquations LE) 
	{
		double intA ;
		intA = ((this.getYConstant()*LE.getConstant())-(this.getConstant()*LE.getYConstant()))/this.getDeterminant(LE) ;
		return intA ;
	}
	
	public double getIntersectionY(LineEquations LE) 
	{
		double intB ;
		intB = (-1)*((this.getXConstant()*LE.getConstant())-(this.getConstant()*LE.getXConstant()))/this.getDeterminant(LE) ;
		return intB ;
	}
	
	public int findX(int y)
    {
    	double temp = ((-1*c)+(-b*y))/a ;
    	return (int)temp ;
    }

    
	public int findY(int x)
    {
    	double temp = ((-1*c)+(-a*x))/b ;
    	return (int) temp ;
    }
	
//	public abstract double getAngleBetweenS(LineEquations LE) ; 
		
//	public abstract double getAngleBetweenB(LineEquations LE) ;

//	public static  void main (String[] args)
//	{
//		LineEquations linex = new LineEquations(3,5,5,3) ;
//		LineEquations liney = new LineEquations(4,3,12,1) ;
//		
//		System.out.println("x:"+linex.getIntersectionX(liney)+"\ny:"+linex.getIntersectionY(liney));
//	}
    
    
    
    
}