package Projeto.Particles;

import processing.core.PVector;

public class PSControl {
	private float averageAngle;
	private float dispersionAngle;
	private float minVelocity;
	private float maxVelocity;
	private float minLifeTime;
	private float maxLifeTime;
	private float minRadius;
	private float maxRadius;
	private float flow;
	private int color;
	
	public PSControl(float[] velControl, float[] lifetime, float[] radius, float flow, int color) {
		setVelParams(velControl);
		setRadius(radius);
		setLifetime(lifetime);
		setFlow(flow);
		setColor(color);
	}
	
	

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public float getFlow() {
		return flow;
	}
	
	public void setFlow(float flow) {
		this.flow = flow;
	}
	
	public float getRndLifetime() {
		return getRnd(minLifeTime, maxLifeTime);
	}
	
	public void setLifetime(float[] lifetime) {
		this.minLifeTime = lifetime[0];
		this.maxLifeTime = lifetime[1];
	}
	
	public float getRndRadius() {
		return getRnd(minRadius, maxRadius);
	}
	
	public void setRadius(float[] radius) {
		this.minRadius = radius[0];
		this.maxRadius = radius[1];
	}
	
	public void setVelParams(float[] velControl) {
		this.averageAngle = velControl[0];
		this.dispersionAngle = velControl[1];
		this.minVelocity = velControl[2];
		this.maxVelocity = velControl[3];
	}

	
	public PVector getRndVel() {
		float angle = getRnd(this.averageAngle - this.dispersionAngle/2, this.averageAngle + this.dispersionAngle/2);
		PVector v = PVector.fromAngle(angle);
		v.mult(getRnd(this.minVelocity, this.maxVelocity));
		
		return v;
	}
	
	public static float getRnd(float min, float max) {
		return min + (float)(Math.random() * (max - min));
	}

}
