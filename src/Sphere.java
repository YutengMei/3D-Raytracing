/*
 * Sphere class that stores radius, center point and surface property of a sphere.
 * Method - determine the intersection point of the sphere and a ray. 
 * Author: Yuteng Mei
 * Data: 5/11/16
 */
public class Sphere extends Surface 
{
	public Vertex Pc;
	public double radius;
	public SurfaceProperties s;
	private double t;
	private Vector internormal;
	public static Vertex intersect = new Vertex(0, 0, 0);
	public Sphere(double radius, Vertex c, SurfaceProperties s) {
		this.radius = radius;
		this.Pc = c;
		this.s = s;
	}

	public SurfaceProperties getSp() {
		return s;
	}

	public Sphere(Sphere sphere) {
		this.radius = sphere.radius;
		this.Pc = sphere.Pc;
		this.s = sphere.s;
	}

	public Vector getINormal() {
		return internormal;
	}

	public Vertex getIntersect() {
		return intersect;
	}

	// Method that return a intersection point with a ray.
	public Vertex hit(Ray r) {
		// p needs to handle.
		Vertex p = new Vertex(0, 0, 0);
		Vertex p0pc = Vertex.subtractVertices(Pc, r.origin);
		double mp0pc = p0pc.magnitude();
		Vector ppc = new Vector(p0pc.x, p0pc.y, p0pc.z);
		double L = Vector.dotProduct(ppc, r.direction);
		double EE = radius * radius - mp0pc * mp0pc + L * L;
		// step 1.
		if (mp0pc * mp0pc < radius * radius) {
			// step4-inside
			t = L + Math.sqrt(EE);
			intersect.x = r.origin.x + r.direction.x * t;
			intersect.y = r.origin.y + r.direction.y * t;
			intersect.z = r.origin.z + r.direction.z * t;
			internormal = new Vector((Pc.x - intersect.x) / radius,
					(Pc.y - intersect.y) / radius, (Pc.z - intersect.z)
							/ radius);
			return intersect;
		}
		// outside
		else {
			// step 2
			// doesn't intersect.
			if (L < 0) {
				return null;
			}
			// step 3
			else {
				// doesn't intersect.
				if (EE < 0) {
					return null;
				}

				else {
					t = L - Math.sqrt(EE);
					intersect.x = r.origin.x + r.direction.x * t;
					intersect.y = r.origin.y + r.direction.y * t;
					intersect.z = r.origin.z + r.direction.z * t;
					internormal = new Vector((intersect.x - Pc.x) / radius,
							(intersect.y - Pc.y) / radius, (intersect.z - Pc.z)
									/ radius);
					return intersect;
				}
			}
		}
	}

	public String toString() {
		return "Radius: " + radius + "," + "Center: " + Pc.x + " " + Pc.y + " "
				+ Pc.z + " " + s.ambientCoeff + " " + s.diffuseCoeff + " "
				+ s.specularCoeff + " " + s.specularExponent;
	}

	public double getT() {
		return t;
	}

}
