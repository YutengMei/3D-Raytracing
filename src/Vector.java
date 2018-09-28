/*
 * Vector class that store vectors and handle vector operation.
 * Author: Yuteng Mei
 * Date: 4/19/16
 */
public class Vector {
	public double x;
	public double y;
	public double z;

	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector(Vector direction) {
		// TODO Auto-generated constructor stub
		this.x=direction.x;
		this.y=direction.y;
		this.z=direction.z;
	}
	public static Vector subtract(Vertex v1, Vertex v2){
		Vector v = new Vector(0, 0, 0);
		v.x = v1.x - v2.x;
		v.y = v1.y - v2.y;
		v.z = v1.z - v2.z;
		return v;
		
	}
	public static Vector subtractV(Vector v3, Vector v4){
		Vector m= new Vector (0,0,0);
		m.x = v3.x - v4.x;
		m.y = v3.y - v4.y;
		m.z = v3.z - v4.z;
		return m;		
	}
	public static Vector scalar(double s, Vector v5){
		Vector n=new Vector (0,0,0);
		n.x=s*v5.x;
		n.y=s*v5.y;
		n.z=s*v5.z;
		return n;
	}
	// A method that calculate the cross product of two vectors.
	public static Vector crossProduct(Vector v1, Vector v2) {
		Vector v3 = new Vector(0.0, 0.0, 0.0);
		v3.x = v1.y * v2.z - v1.z * v2.y;
		v3.y = v1.z * v2.x - v1.x * v2.z;
		v3.z = v1.x * v2.y - v1.y * v2.x;
		return v3;
	}

	// A method that calculate the dot product of two vectors.
	public static double dotProduct(Vector v1, Vector v2) {
		double x;
		
		if (v1 == null || v2 == null)
		{
			System.out.println("v1 = " + v1);
			System.out.println("v2 = " + v2);
		}
		x = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
		return x;

	}

	// A method that find the length of a vector.
	public double magnitude() {
		double magnitude = Math.sqrt(x * x + y * y + z * z);
		return magnitude;
	}

	// A method that normalize a vertex.
	public void normalize() {
		double m = magnitude();
		this.x = x / m;
		this.y = y / m;
		this.z = z / m;
	}

	public String toString() {
		return x + "," + y + "," + z;
	}
	

}
