/*
 * Surface property class that stores color, ambient coefficence, diffuse coefficence, specular coefficence of the surface. 
 * Author: Yuteng Mei
 * Data: 5/11/16
 */
import java.awt.Color;
public class SurfaceProperties {
	public Color col;
	public double ambientCoeff;
	public double diffuseCoeff;
	public double specularCoeff;
	public double specularExponent;

	public SurfaceProperties(Color c,double a,double d,double sc,double se){
		this.col=c;
		this.ambientCoeff=a;
		this.diffuseCoeff=d;
		this.specularCoeff=sc;
		this.specularExponent=se;	
	}
		
	public double getAmbient() {
		return ambientCoeff;
	}
	public double getDiffuse() {
		return diffuseCoeff;
	}
	public double getSpecularc() {
		return specularCoeff;
	}
	public double getSpeculare() {
		return specularExponent;
	}
	
}
