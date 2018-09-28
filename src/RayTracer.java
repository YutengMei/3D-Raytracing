/*
   RayTracer contains methods that 
    read input files - read in environment files
    RT trace, RT shade - main methods of the ray tracer.
    
   Author: Yuteng Mei
   Date: 5/11/16
   Spring 2016

*/

import java.awt.Color;
import java.io.*;
import java.util.*;

public class RayTracer {

	public static Vertex v[];
	public static Vertex CoP = new Vertex(0, 0, 0);
	public static Vertex pointLight[];
	public static ArrayList<Surface> List = new ArrayList<Surface>();
	public static Polygon polygon[];
	public static Color pointColor[];
	public static Sphere sphere[];
	public static int maxDepth = 5;
	public static double xmin = 0, xmax = 0, ymin = 0, ymax = 0;
	public static ArrayList<Surface> deltedObj = new ArrayList<Surface>();

	public static void main(String args[]) throws IOException {

		// NOTE: this program as written expects the 2 command line arguments
		// the first is the object file name
		// the second is the viewing paramater file name
		// in Eclipse, enter these separated by a space in the
		// Run..., Java Application name, Arguments tab, Program arguments text
		// area

		/*
		 * this is Java code to read in an object file and a viewing parameter
		 * file
		 */
		// ==========================================================================
		// ========================Read Polygons File================================
		// ==========================================================================
		BufferedReader objFileBR;
		String line, tempstr;
		StringTokenizer st;

		String objfName = args[0]; // get the object file name from first
									// command line parameter

		int numVs = 0, numPolys = 0;

		try {
			objFileBR = new BufferedReader(new FileReader(objfName));

			line = objFileBR.readLine(); // should be the VERTICES line
			st = new StringTokenizer(line, " ");
			tempstr = st.nextToken();
			if (tempstr.equals("VERTICES")) {
				tempstr = st.nextToken();
				numVs = Integer.parseInt(tempstr);
				v = new Vertex[numVs];

			} else {
				numVs = 0;
				System.out.println("Expecting VERTICES line in file "
						+ objfName);
				System.exit(1);
			}

			line = objFileBR.readLine(); // should be the POLYGONS line
			st = new StringTokenizer(line, " ");
			tempstr = st.nextToken();
			if (tempstr.equals("POLYGONS")) {
				tempstr = st.nextToken();
				numPolys = Integer.parseInt(tempstr);
				polygon = new Polygon[numPolys];
			} else {
				System.out.println("Expecting POLYGONS line in file "
						+ objfName);
				System.exit(1);
			}

			line = objFileBR.readLine(); // should be the VERTEX LIST line
			st = new StringTokenizer(line, " ");
			tempstr = st.nextToken();
			if (tempstr.equals("VERTEX")) {
				tempstr = st.nextToken();
				if (!tempstr.equals("LIST")) {
					System.out.println("Expecting VERTEX LIST line in file "
							+ objfName);
					System.exit(1);
				}
			} else {
				System.out.println("Expecting VERTEX LIST line in file "
						+ objfName);
				System.exit(1);
			}
			// if we get here we successfully processed the VERTEX LIST line

			// reads each of the vertex coordinates and creates a Vertex object
			// for each one
			for (int i = 0; i < numVs; i++) {
				line = objFileBR.readLine();
				st = new StringTokenizer(line, " ");
				double x1 = 0, y1 = 0, z1 = 0;
				tempstr = st.nextToken();
				x1 = Double.parseDouble(tempstr);
				tempstr = st.nextToken();
				y1 = Double.parseDouble(tempstr);
				tempstr = st.nextToken();
				z1 = Double.parseDouble(tempstr);
				v[i] = new Vertex(x1, y1, z1, 1.0);

			}

			line = objFileBR.readLine(); // should be the POLYGON LIST line
			st = new StringTokenizer(line, " ");
			tempstr = st.nextToken();
			if (tempstr.equals("POLYGON")) {
				tempstr = st.nextToken();
				if (!tempstr.equals("LIST")) {
					System.out.println("Expecting POLYGON LIST line in file "
							+ objfName);
					System.exit(1);
				}
			} else {
				System.out.println("Expecting POLYGON LIST line in file "
						+ objfName);
				System.exit(1);
			}
			// if we get here we successfully processed the POLYGON LIST line

			for (int i = 0; i < numPolys; i++) {
				Color c = new Color(0, 0, 0);
				SurfaceProperties s1 = new SurfaceProperties(c, 0, 0, 0, 0);
				line = objFileBR.readLine();
				st = new StringTokenizer(line, " ");
				st.nextToken(); // ignore the string COUNT
				tempstr = st.nextToken(); // this is the value of count (number
											// of vertices for this poly)
				int numVsForThisPoly = Integer.parseInt(tempstr);
				polygon[i] = new Polygon(numVsForThisPoly, s1);
				st.nextToken(); // ignore the string VERTICES

				// example line: COUNT 5 VERTICES 5 4 3 2 1 COLOR .4 .2 .4

				for (int j = 1; j <= numVsForThisPoly; j++) {
					tempstr = st.nextToken();
					polygon[i].addV(v[Integer.parseInt(tempstr) - 1]);
				}

				st.nextToken(); // ignore the string COLOR
				float x1, y1, z1;
				tempstr = st.nextToken();
				x1 = (Float.parseFloat(tempstr));
				tempstr = st.nextToken();
				y1 = (Float.parseFloat(tempstr));
				tempstr = st.nextToken();
				z1 = (Float.parseFloat(tempstr));
				Color c1 = new Color(x1, y1, z1);
				s1.col = c1;
				tempstr = st.nextToken();
				tempstr = st.nextToken();
				s1.ambientCoeff = Double.parseDouble(tempstr);
				tempstr = st.nextToken();
				tempstr = st.nextToken();
				s1.diffuseCoeff = Double.parseDouble(tempstr);
				tempstr = st.nextToken();
				tempstr = st.nextToken();
				s1.specularCoeff = Double.parseDouble(tempstr);
				tempstr = st.nextToken();
				tempstr = st.nextToken();
				s1.specularExponent = Double.parseDouble(tempstr);

			}

			objFileBR.close();

		} catch (FileNotFoundException fnfe) {
			System.out.println("File not found");
		} catch (IOException ioe) {
			System.out.println("couldn't read from file");
		}

		// ==================================================================
		// ------------------------Read Sphere files-------------------------
		// ==================================================================
		int numSphere = 0;
		String objfName1 = args[2];
		BufferedReader sphereObj;
		try {
			sphereObj = new BufferedReader(new FileReader(objfName1));
			line = sphereObj.readLine();
			st = new StringTokenizer(line, " ");
			tempstr = st.nextToken();

			if (tempstr.equals("SPHERE"))
				;
			{
				tempstr = st.nextToken();
				numSphere = Integer.parseInt(tempstr);
				sphere = new Sphere[numSphere];
			}

			for (int i = 0; i < numSphere; i++) {
				double radius = 0;
				double x2 = 0, y2 = 0, z2 = 0;
				float x = 0, y = 0, z = 0;
				Vertex c;
				Color r;
				line = sphereObj.readLine();
				st = new StringTokenizer(line, " ");
				tempstr = st.nextToken();
				// Radius
				tempstr = st.nextToken();
				radius = Double.parseDouble(tempstr);
				tempstr = st.nextToken();// Center
				tempstr = st.nextToken();
				x2 = Double.parseDouble(tempstr);
				tempstr = st.nextToken();
				y2 = Double.parseDouble(tempstr);
				tempstr = st.nextToken();
				z2 = Double.parseDouble(tempstr);
				c = new Vertex(x2, y2, z2);
				tempstr = st.nextToken();
				// color
				tempstr = st.nextToken();
				x = (Float.parseFloat(tempstr));
				tempstr = st.nextToken();
				y = (Float.parseFloat(tempstr));
				tempstr = st.nextToken();
				z = (Float.parseFloat(tempstr));
				r = new Color(x, y, z);
				SurfaceProperties s = new SurfaceProperties(r, 0, 0, 0, 0);
				tempstr = st.nextToken();
				tempstr = st.nextToken();
				s.ambientCoeff = Double.parseDouble(tempstr);
				tempstr = st.nextToken();
				tempstr = st.nextToken();
				s.diffuseCoeff = Double.parseDouble(tempstr);
				tempstr = st.nextToken();
				tempstr = st.nextToken();
				s.specularCoeff = Double.parseDouble(tempstr);
				tempstr = st.nextToken();
				tempstr = st.nextToken();
				s.specularExponent = Double.parseDouble(tempstr);
				sphere[i] = new Sphere(radius, c, s);

			}
			sphereObj.close();

		} catch (FileNotFoundException fnfe) {
			System.out.println("File not found");
		} catch (IOException ioe) {
			System.out.println("couldn't read from file");
		}

		// ================================================================
		// ----------------Read Environment File---------------------------
		// ================================================================

		String viewfName = args[1]; // second command line arg
		BufferedReader viewFileBR;

		try {
			viewFileBR = new BufferedReader(new FileReader(viewfName));
			line = viewFileBR.readLine(); // should be the WINDOW line
			st = new StringTokenizer(line, " ");
			tempstr = st.nextToken();
			if (tempstr.equals("WINDOW")) {
				tempstr = st.nextToken();
				xmin = Double.parseDouble(tempstr);
				tempstr = st.nextToken();
				xmax = Double.parseDouble(tempstr);
				tempstr = st.nextToken();
				ymin = Double.parseDouble(tempstr);
				tempstr = st.nextToken();
				ymax = Double.parseDouble(tempstr);
			} else {
				System.out
						.println("Expecting WINDOW line in file " + viewfName);
				System.exit(1);
			}

			line = viewFileBR.readLine();
			st = new StringTokenizer(line, " ");
			tempstr = st.nextToken();
			int numlight = 0;
			if (tempstr.equals("POINTLIGHT"))
				;
			{
				tempstr = st.nextToken();
				numlight = Integer.parseInt(tempstr);
				pointLight = new Vertex[numlight];
				pointColor = new Color[numlight];
			}

			for (int i = 0; i <= numlight - 1; i++) {
				line = viewFileBR.readLine();
				st = new StringTokenizer(line, " ");
				double x3 = 0, y3 = 0, z3 = 0;
				float x4 = 0, y4 = 0, z4 = 0;
				tempstr = st.nextToken();
				x3 = Double.parseDouble(tempstr);
				tempstr = st.nextToken();
				y3 = Double.parseDouble(tempstr);
				tempstr = st.nextToken();
				z3 = Double.parseDouble(tempstr);
				pointLight[i] = new Vertex(x3, y3, z3);
				tempstr = st.nextToken();
				x4 = Float.parseFloat(tempstr);
				tempstr = st.nextToken();
				y4 = Float.parseFloat(tempstr);
				tempstr = st.nextToken();
				z4 = Float.parseFloat(tempstr);
				pointColor[i]=new Color(x4,y4,z4);
			}

			viewFileBR.close();

		} catch (FileNotFoundException fnfe) {
			System.out.println("File not found");
		} catch (IOException ioe) {
			System.out.println("couldn't read from file");
		}
		
		
		for (int i = 0; i < polygon.length + sphere.length; i++) {

			if (i < polygon.length) {
				List.add(new Polygon(polygon[i]));
			} else {
				List.add(new Sphere(sphere[-(polygon.length - i)]));
			}
		}

		//=========================================================================
		//--------------------Draw Background Color as black-----------------------
		//=========================================================================
		RGBPixel image[][] = new RGBPixel[(int) (ymax * 2) + 1][(int) xmax * 2 + 1];

		for (int i = 0; i <= (int) (ymax * 2); i++) {
			for (int j = 0; j <= (int) (xmax * 2); j++) {
				image[i][j] = new RGBPixel(0, 0, 0);
			}
		}
		
		// ====================================================
		// ==============PRINT IMAGE===========================
		// ====================================================
		int high = 0;
		for (double i = ymax; i > ymin; i--) {
			int weigh = 0;
			for (double j = xmin; j < xmax; j++) {
				weigh++;
				Vertex plane = new Vertex(j, i, -200);
				Ray ray = new Ray(CoP, Vector.subtract(plane, CoP));
				Color pixel = RT_trace(ray, 1);
				image[high][weigh] = new RGBPixel(pixel.getRed(),
						pixel.getGreen(), pixel.getBlue());
			}
			high++;
		}
		JPGAndRGBPixelArray.writeImage(image, "image.jpg");

	}

	/**
	 * Method that used for recursive ray tracing, determine the closest object that intersects with the ray.
	 * @param ray - Ray that intersect with the object.
	 * @param depth -  current depth of the bouncing ray.
	 * @return Color of a pixel.
	 */
	public static Color RT_trace(Ray ray, int depth) {
		Color color = new Color(0, 0, 0);
		double min = Double.MAX_VALUE;
		Surface closestObj = null;
		boolean hit = false;
		int index = 0;
		
		//loop through all the objects, and determine the closest one.
		for (int k = 0; k < List.size(); k++) {
			if (List.get(k).hit(ray) != null) {
				hit = true;
				if (List.get(k).getT() < min) {
					min = List.get(k).getT();
					closestObj = List.get(k);
					index = k;
				}
			}
		}
		
		//If there is an intersection with object, pass the object to RT_shade method to calculate the color intensities with ambient, diffuse, and specular terms.
		if (hit == true) {
			List.remove(index);
			return RT_shade(closestObj, ray, closestObj.getIntersect(),
					closestObj.getINormal(), depth);
		} else {
			return color;
		}
	}

	/**
	 * Method that implement ambient illumination, diffuse illumination, specular reflection and shadows.
	 * @param o -  closest object intersects with the ray.
	 * @param r - Ray that comes from COP to intersection point.
	 * @param intersection - intersection points.
	 * @param n - normal of the intersection.
	 * @param depth -  current depth of the bouncing ray.
	 * @return -  color
	 */
	public static Color RT_shade(Surface o, Ray r, Vertex intersection,
			Vector n, int depth) {
		// System.out.println("RTshade");
		Surface object = o;
		Ray ray = r;
		Vertex point = intersection;
		Vector normal = n;
		normal.normalize();
		// Vector V = from the intersection point to the eye(CoP).

		Vector V = Vector.scalar(-1, ray.direction);
		V.normalize();

		Color color;// color of ray
		Ray rRay, sRay;// reflected, and shadow rays
		Color rColor;// color of reflected ray

		// ambient term.
		float Ir = (float) (0.6 * object.getSp().ambientCoeff * (object.getSp().col
				.getRed() / 255.0));
		float Ig = (float) (0.6 * object.getSp().ambientCoeff * (object.getSp().col
				.getGreen() / 255.0));
		float Ib = (float) (0.6 * object.getSp().ambientCoeff * (object.getSp().col
				.getBlue() / 255.0));

		for (int p = 0; p < pointLight.length; p++) {
			boolean hits = true;
			double d = Vector.subtract(pointLight[p], point).magnitude();
			// direction of the shadow ray= intersection point to the light
			// source.
			Vector L = Vector.subtract(pointLight[p], point);
			L.normalize();
			sRay = new Ray(point, L);
			double LN = Vector.dotProduct(L, normal);

			for (int k = 0; k < List.size(); k++) {

				if (List.get(k).hit(sRay) != null) {
					hits = false;
				}
			}

			// ==========R=2(L*N)N-L=====================
			// ==========compute cos(phi)= R*V===========
			Vector twoN = Vector.scalar((2 * Vector.dotProduct(L, normal)),
					normal);
			Vector R = Vector.subtractV(twoN, L);
			double cos_phi = Vector.dotProduct(R, V);
			// ==========================================

			if (Vector.dotProduct(normal, L) > 0) {
				// Let a0=0, a1=0,a3=0.000001
				double fradatten = 1 / (0.000001 * d * d);
				// compute diffuse and specular terms.
				// f(radatten)*I(Kd*Or*LN)

				if (hits == true) {
					Ir += (pointColor[p].getRed()/255.0)
							* fradatten
							* (object.getSp().diffuseCoeff
									* (object.getSp().col.getRed() / 255.0)
									* LN + object.getSp().specularCoeff
									* Math.pow(cos_phi,
											object.getSp().specularExponent));
					Ig += (pointColor[p].getGreen()/255.0)
							* fradatten
							* (object.getSp().diffuseCoeff
									* (object.getSp().col.getGreen() / 255.0)
									* LN + object.getSp().specularCoeff
									* Math.pow(cos_phi,
											object.getSp().specularExponent));
					Ib += (pointColor[p].getBlue()/255.0)
							* fradatten
							* (object.getSp().diffuseCoeff
									* (object.getSp().col.getBlue() / 255.0)
									* LN + object.getSp().specularCoeff
									* Math.pow(cos_phi,
											object.getSp().specularExponent));
				}

			}

		}

		// Recursive method that handle bouncing rays.
		if (depth < maxDepth) {
			if (object.getSp().specularCoeff > 0) {
				Vector twoN = Vector.scalar((2 * Vector.dotProduct(V, normal)),
						normal);
				Vector reflection = Vector.subtractV(twoN, V);
				rRay = new Ray(point, reflection);
				rColor = RT_trace(rRay, depth + 1);

				Ir += (float) (object.getSp().specularCoeff * (rColor.getRed() / 255.0));
				Ig += (float) (object.getSp().specularCoeff * (rColor
						.getGreen() / 255.0));
				Ib += (float) (object.getSp().specularCoeff * (rColor.getBlue() / 255.0));
			}

		}

		List.add(object);

		if (Ir > 1) {
			Ir = 1;
		}
		if (Ig > 1) {
			Ig = 1;
		}
		if (Ib > 1) {
			Ib = 1;
		}

		color = new Color(Ir, Ig, Ib);
		return color;
	}
}
