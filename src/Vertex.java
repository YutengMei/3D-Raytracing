/*
 * Vertex class that store vertices and handle vertices operation of the polygon.
 * Author: Yuteng Mei
 * Date: 4/19/16
 */
public class Vertex {
	public double x;
	public double y;
	public double z;
	public double c;

	public Vertex(double x, double y, double z, double c) {

		this.x = x / c;
		this.y = y / c;
		this.z = z / c;
		this.c = c / c;
	}

	public Vertex(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.c = 1.0;

	}

	public Vertex(Vertex vertex) {
		this.x=vertex.x;
		this.y=vertex.y;
		this.z=vertex.z;
	}
	
	public static boolean equals(Vertex v, Vertex v1){
		if(v.x==v1.x&&v.y==v1.y&&v.z==v1.z){
			return true;
		}
		else{
			return false;
		}
	}
	
	public double magnitude() {
		double magnitude = Math.sqrt(x * x + y * y + z * z);
		return magnitude;
	}


	// A method that subtract two vertices.
	public static Vertex subtractVertices(Vertex v1, Vertex v2) {
		Vertex v = new Vertex(0, 0, 0);
		v.x = v1.x - v2.x;
		v.y = v1.y - v2.y;
		v.z = v1.z - v2.z;

		return v;

	}

	public String toString() {
		return x + "," + y + "," + z + "," + c;
	}

}
