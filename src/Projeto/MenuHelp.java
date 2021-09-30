package Projeto;

import Projeto.Boids.LoadImagens;
import Projeto.Graphics.Button;
import Projeto.Setup.SubPlot;
import Projeto.Terrain.WorldConstants;
import processing.core.PApplet;
import processing.core.PImage;

public class MenuHelp {
    private PApplet p;
    private SubPlot plt;
    private ProjetoApp app;

    private float[] viewport = {0f, 0f, 1f, 1f};

    private Button btnReturn, mouseClick, keyV, keyC, keyP;
    private PImage background;

    public MenuHelp(ProjetoApp app, PApplet p) {
        this.p = p;
        this.plt = new SubPlot(WorldConstants.WINDOW, viewport, p.width, p.height);
        this.app = app;

        btnReturn = new Button(null, LoadImagens.btnReturn,p.width - (LoadImagens.btnReturn.width) - 20, p.height - (LoadImagens.btnReturn.height) - 20);
        background = LoadImagens.backgroundEmpty;

        mouseClick = new Button(null, LoadImagens.mouseClick,p.width/4 - (LoadImagens.mouseClick.width), p.height/3.4f - (LoadImagens.mouseClick.height));
        mouseClick.setText(" - Selects the pokemon.");

        keyV = new Button(null, LoadImagens.keyV,p.width/4 - (LoadImagens.keyV.width), p.height/2.4f - (LoadImagens.keyV.height));
        keyV.setText(" - Activates the vision camp.");

        keyC = new Button(null, LoadImagens.keyC,p.width/4 - (LoadImagens.keyC.width), p.height/1.8f - (LoadImagens.keyC.height));
        keyC.setText(" - Changes between Auto and Manual Mode.");

        keyP = new Button(null, LoadImagens.keyP,p.width/4 - (LoadImagens.keyP.width), p.height/1.42f - (LoadImagens.keyP.height));
        keyP.setText(" - Changes between panels.");
    }

    public void display() {
        p.image(background, 0, 0);

        btnReturn.displayImageWithHover(p, true);
        mouseClick.displayImageWithHover(p, false);
        keyC.displayImageWithHover(p, false);
        keyV.displayImageWithHover(p, false);
        keyP.displayImageWithHover(p, false);

    }

    public void mousePressed(PApplet p){
        if(btnReturn.clickImage(p.mouseX, p.mouseY)){
            app.setMenuSelection(0);
        }
    }
}
