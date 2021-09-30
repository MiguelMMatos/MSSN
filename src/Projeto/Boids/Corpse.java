package Projeto.Boids;

import Projeto.Setup.SubPlot;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Corpse {
    private PVector pos;
    private PImage image;

    public Corpse(SubPlot plt, PVector pos, int type){
        float[] wc = plt.getPixelCoord(pos.x, pos.y);
        this.pos = new PVector(wc[0], wc[1]);

        switch (type){
            case 0:
                image = LoadImagens.Corpse;
                break;
        }
    }

    public void display(PApplet p){
        p.image(image, pos.x, pos.y);
    }
}
