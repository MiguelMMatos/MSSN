package Projeto.Graphics;

import Projeto.Terrain.WorldConstants;
import processing.core.PApplet;
import processing.core.PImage;

public class Button {
	private int color, textSize;
	private float rectX, rectY, rectWidth, rectHeight, textX, textY;
	private String text;
	private PImage image;
	
	private WorldConstants.PokeTypes type;
	
	private boolean isSelected = false;
	private boolean canHover;
	
	private int alpha, alphaImage;

	public Button(int color, float rectX, float rectY, float rectWidth, float rectHeight, String text, float textX, float textY, int textSize, boolean hover) {
		this.color = color;
		this.rectX = rectX;
		this.rectY = rectY;
		this.rectWidth = rectWidth;
		this.rectHeight = rectHeight;
		this.text = text;
		this.textX = textX;
		this.textY = textY;
		this.textSize = textSize;
		this.canHover = hover;
		
		this.alpha = 100;
	}
	
	public Button(WorldConstants.PokeTypes type, PImage image, float rectX, float rectY) {
		this.image = image;
		this.rectX = rectX;
		this.rectY = rectY;
		this.type = type;

		this.alphaImage = 255;
	}

	public void display(PApplet p) {
		p.pushStyle();

		p.textSize(textSize);
		hover(p);
		
		p.fill(color, alpha);
    	p.rect(rectX, rectY, rectWidth, rectHeight);
    	p.fill(255);
    	p.text(text, textX, textY);

    	p.popStyle();
	}
	
	public void displayImage(PApplet p) {
		p.pushStyle();

		if(isSelected) {
			p.fill(0);
			p.circle(rectX + (image.width/2), rectY + (image.height/2), 50);
		}	
		
		p.image(image, rectX, rectY);

		p.popStyle();
	}

	public void displayImageWithHover(PApplet p, boolean value) {
		p.pushStyle();

		if(value)
			hoverButton(p);

		p.tint(255, alphaImage);
		p.image(image, rectX, rectY);

		if(text != null)
			showText(p);

		p.popStyle();
	}

	private void showText(PApplet p){
		p.pushStyle();

		p.fill(0);
		p.textSize(30);
		p.text(text, rectX + (image.width), rectY + (image.height/2) + 10);

		p.popStyle();
	}

	public void setText(String text){
		this.text = text;
	}
	
	public boolean clickImage(float posX, float posY) {
		if( (posX >= rectX && posX <= (rectX + image.width)) && (posY >= rectY && posY <= (rectY + image.height)) ) {
			return true;
		}
		else {
			return false;
		}
	}

	private void hoverButton(PApplet p){
		if( (p.mouseX >= rectX && p.mouseX <= (rectX + image.width)) && (p.mouseY >= rectY && p.mouseY <= (rectY + image.height)) ) {
			this.alphaImage = 120;
		}
		else
			this.alphaImage = 255;
	}
	
	private void hover(PApplet p) {
		if(!this.canHover) {
			this.alpha = 0;
		}
		else if( (p.mouseX >= rectX && p.mouseX <= (rectX + rectWidth)) && (p.mouseY >= rectY && p.mouseY <= (rectY + rectHeight)) ) {
			this.alpha = 50;
		}
		else
			this.alpha = 100;
	}
	
	public boolean isPressed(PApplet p) {
		if( (p.mouseX >= rectX && p.mouseX <= (rectX + rectWidth)) && (p.mouseY >= rectY && p.mouseY <= (rectY + rectHeight)) ) 
			return true;

		return false;
	}
	
	public void setSelected(boolean value) {
		this.isSelected = value;
	}
	
	public WorldConstants.PokeTypes getType() {
		return type;
	}
	
	public boolean isSelected() {
		return this.canHover;
	}
	
	public void setIsSelected(boolean value) {
		this.canHover = value;
	}
	
}
