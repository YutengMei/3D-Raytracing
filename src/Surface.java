/*
 * Super class of sphere and polygon class
 * Author: Yuteng Mei
 * Data: 5/11/16
 */
public abstract class Surface
{
	public abstract Vertex hit(Ray ray);
	public abstract double getT();
	public abstract Vector getINormal();
	public abstract Vertex getIntersect();
	public abstract SurfaceProperties getSp();
	

}
