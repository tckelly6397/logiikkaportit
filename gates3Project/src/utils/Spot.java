
package utils;

public class Spot {
	private double x;
	private double y;
	
	public Spot(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Spot(Spot s) {
		super();
		this.x = s.getX();
		this.y = s.getY();
	}
	
	public static double getDistance(Spot s1, Spot s2) {
		return Math.sqrt(Math.pow(s2.getX() - s1.getX(), 2) + Math.pow(s2.getY() - s1.getY(), 2));
	}
	
	public double getDistance(Spot s) {
		return Math.sqrt(Math.pow(s.getX() - x, 2) + Math.pow(s.getY() - y, 2));
	}
	
	public double getDistanceX(Spot s) {
		return s.getX() - x;
	}
	
	public double getDistanceY(Spot s) {
		return s.getY() - y;
	}
	
	public static double getAngle(Spot s1, Spot s2) {
		return Math.atan2((s2.getY() - s1.getY()), (s2.getX() - s1.getX()));
	}
	
	public double getAngle(Spot s) {
		return Math.atan2((s.getY() - y), (s.getX() - x));
	}
	
	public Spot subtract(Spot s2) {
		double newX = s2.getX() - this.x;
		double newY = s2.getY() - this.y;
		return new Spot(newX, newY);
	}
	
	public Spot add(Spot s2) {
		double newX = s2.getX() + this.x;
		double newY = s2.getY() + this.y;
		return new Spot(newX, newY);
	}
	
	public boolean setSpot(double x, double y) {
		this.x = x;
		this.y = y;
		
		return true;
	}
	
	public boolean setSpot(Spot s) {
		this.x = s.x;
		this.y = s.y;
		
		return true;
	}
	
	public int getXAsInt() {
		return (int)x;
	}
	
	public int getYAsInt() {
		return (int)y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Spot)) return false;
		Spot s2 = (Spot)o;
		
		if(x == s2.getX() && y == s2.getY())
			return true;
		
		return false;
	}

	@Override
	public String toString() {
		return "Spot [x=" + x + ", y=" + y + "]";
	}
}
