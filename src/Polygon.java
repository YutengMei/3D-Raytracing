/*
 * Polygon class that stores vertices and surface property of a polygon.
 * Method - determine the intersection point of the polygon and a ray. 
 * Author: Yuteng Mei
 * Data: 5/11/16
 */

public class Polygon extends Surface {
	public Vertex [] v;
	public Vertex [] twodv;
	public Vector normal;
	public Vector iNormal;
	private int current;
	public static int numberOfVs;
    public SurfaceProperties sp;
    private double t;
    public static Vertex intersect=new Vertex(0,0,0);
    
	public Polygon(int numVs, SurfaceProperties s1) {
		numberOfVs = numVs;
		v = new Vertex[numVs];
		twodv = new Vertex[numVs];
		this.sp=s1;
		current = 0;
	}
	public Polygon(Polygon polygon) {
		numberOfVs = polygon.numberOfVs;
		v = polygon.v;
		twodv = polygon.twodv;
		this.sp=polygon.sp;
		current = 0;
	}
	public SurfaceProperties getSp(){
		return sp;
	}
		
	public void addV(Vertex vx) {
		v[current] = vx;
		current++;
	}
	
	public Vector getINormal(){
		  return iNormal;
	  }
	
	public int getNumVs() {
		return numberOfVs;
	}
	
	public Vector Getnormal(){
		
		Vertex p1=Vertex.subtractVertices(v[1], v[0]);
		Vector pa=new Vector(p1.x,p1.y,p1.z);
		Vertex p2=Vertex.subtractVertices(v[2], v[0]);
		Vector pb=new Vector(p2.x,p2.y,p2.z);
		normal=Vector.crossProduct(pa, pb);
		return normal;
	}
	
	public void getV()
	{
		for(int i=0;i<current;i++)
		{
			System.out.println(v[i].x+","+v[i].y+","+v[i].z);
		}
	}
	
    public double getT(){
    	return t;
    }
    
	  public Vertex getIntersect(){
		  return intersect;
	  }
	  
    
	// Method that return a intersection point with a ray.
	public Vertex hit(Ray ray) {
		Vertex twoDinter=new Vertex(0,0,1);
		Getnormal();
		normal.normalize();
		if(Vector.dotProduct(normal, ray.direction)>0)
		{
			normal.x=-normal.x;
			normal.y=-normal.y;
			normal.z=-normal.z;
			
		}
		else if(Vector.dotProduct(normal, ray.direction)==0){
			return null;
		}
		
		Vector p0=new Vector(ray.origin.x,ray.origin.y,ray.origin.z);
		double D=-1*(normal.x* v[0].x + normal.y*v[0].y + normal.z*v[0].z);
		t=-1*(Vector.dotProduct(normal, p0)+D)/(Vector.dotProduct(normal, ray.direction));
		
		//check if the ray intersect the plane of the polygon.
		if(t < 0){
			//System.out.println();
			return null;
		}
		else{
			intersect.x=ray.origin.x+ray.direction.x*t;
			intersect.y=ray.origin.y+ray.direction.y*t;
			intersect.z=ray.origin.z+ray.direction.z*t;
			iNormal=new Vector(normal.x,normal.y,normal.z);
			//Neglect the largest value of the normal.
			//Need to handle equality?------------------------
			double A=Math.abs(normal.x);
			double B=Math.abs(normal.y);
			double C=Math.abs(normal.z);
			double temp=Math.max(Math.max(A, B), C);
			if (temp==A){
				for(int i=0;i<numberOfVs;i++){
					twodv[i]=new Vertex(v[i].y,v[i].z,1);
				}
				twoDinter.x=intersect.y;
				twoDinter.y=intersect.z;
			}
			else if (temp==B)
			{
				for(int i=0;i<numberOfVs;i++){
					twodv[i]=new Vertex(v[i].x,v[i].z,1);
				}
				twoDinter.x=intersect.x;
				twoDinter.y=intersect.z;
			}
			else if(temp==C){
				
				for(int i=0;i<numberOfVs;i++){
					
					twodv[i]=new Vertex(v[i].x,v[i].y,1);
				}
				
				twoDinter.x=intersect.x;
				twoDinter.y=intersect.y;
			}
			
			int numCross=polygonC(twodv,twoDinter);
			
			if(numCross%2!=0){
				return intersect;
			}
			
			else{
				//System.out.println("null11");
				return null;
				
			}
		}
	
		//------------------------------
		
	}
	
	public static int polygonC(Vertex twod[],Vertex inter)
	{
		Vertex uva=new Vertex(0,0,0);
		Vertex uvb=new Vertex(0,0,0);
		Vertex twod2[]=new Vertex[numberOfVs];
		int numCross=0;
		int signHold1,signHold2;
		
		for(int i=0;i<numberOfVs;i++){
			twod2[i]=new Vertex(twod[i].x-inter.x,twod[i].y-inter.y,1);
		}
		
		if (twod2[0].y>0){
			signHold1 = 1;
		}
		else{
			signHold1 = -1;
		}
		
		//loop through each edge
		for (int i=0; i<numberOfVs;i++)
		{
			//The vertices of last edge is v[last] and v[first].
			if(i==numberOfVs-1){
				uva=twod2[i];
				uvb=twod2[0];
			}
			
			else{
				uva=twod2[i];
				uvb=twod2[i+1];
			}
			
			if (uvb.y>0){
				signHold2=1;
			}
			else{
				signHold2=-1;
			
			}
			if (signHold1!=signHold2){
				
				if(uva.x>0&&uvb.x>0){
					numCross++;
				}
				
				else if(uva.x>0 || uvb.x>0)
				{
					if(crossPosUAxis(uva.x,uva.y,uvb.x,uvb.y)){
						numCross++;
					}
				}
			}
			signHold1=signHold2;
		}
		return numCross;
	}
	
	public static boolean crossPosUAxis(double ua,double va,double ub,double vb){
		if ((ub+(ua-ub)*(vb/(vb-va)))>0){
			return true;
		}
		else{
		return false;
	}
		}
}
