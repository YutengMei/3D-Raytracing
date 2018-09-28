
public class Ray {
	public Vertex origin;
	public Vector direction;

	
	public Ray(Vertex origin,Vector direction)
	{
		direction.normalize();
		this.origin=new Vertex(origin);
		this.direction=new Vector(direction);
		
	}
}
