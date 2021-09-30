package Projeto.Graphics;

import processing.core.PApplet;
import processing.core.PImage;

public class RadioButton {
	private int color, textSize;
	private float rectX, rectY, rectWidth, rectHeight, textX, textY;
	private String text;

	private static int colorFill = 0;

	private static float circleXIncrease = 10;
	private static float circleYIncrease = 10.5f;
	private static int circleRadius = 10;

	private boolean isSelected;

	private int alpha;

	public RadioButton(float rectX, float rectY, float rectWidth, float rectHeight, String text, float textX, float textY, int textSize, boolean isSelected) {
		this.rectX = rectX;
		this.rectY = rectY;
		this.rectWidth = rectWidth;
		this.rectHeight = rectHeight;
		this.text = text;
		this.textX = textX;
		this.textY = textY;
		this.textSize = textSize;

		this.isSelected = isSelected;

		this.alpha = 100;
	}

	
	public void display(PApplet p) {
		p.pushStyle();

		p.textSize(textSize);

		if(isSelected)
			p.fill(colorFill);
		else
			p.noFill();

		p.circle(rectX - circleXIncrease, rectY + circleYIncrease, 10);

		p.fill(0);
		p.text(text, textX, textY);
    	p.popStyle();
	}

	
	public int isPressed(PApplet p) {
		if( PApplet.dist(p.mouseX, p.mouseY, rectX - circleXIncrease, rectY + circleYIncrease) <= (circleRadius / 2)){
			isSelected = !isSelected;
			return (isSelected)? 1 : 0;
		}
		return -1;
	}
	
	public boolean isSelected() {
		return this.isSelected;
	}
	
	public void setIsSelected(boolean value) {
		this.isSelected = value;
	}
	
}
