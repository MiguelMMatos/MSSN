package Projeto.Boids;

import processing.core.PApplet;
import processing.core.PImage;

public class LoadImagens {
    private String imagensDir = "Projeto/imagens/SideContent/";
    public static PImage sharpedo, squirtle, magikarp, omastar;
    public static PImage INVsharpedo, INVsquirtle, INVmagikarp, INVomastar;
    public static PImage sharpedo_Attack, INVsharpedo_Attack;
    public static PImage Glowsharpedo, Glowsquirtle, Glowmagikarp, Glowomastar;
    public static PImage GlowINVsharpedo, GlowINVsquirtle, GlowINVmagikarp, GlowINVomastar;
    public static PImage Glowsharpedo_Attack, GlowINVsharpedo_Attack;
    public static PImage YELLOWGlowsharpedo, YELLOWGlowsquirtle, YELLOWGlowmagikarp, YELLOWGlowomastar;
    public static PImage YELLOWGlowINVsharpedo, YELLOWGlowINVsquirtle, YELLOWGlowINVmagikarp, YELLOWGlowINVomastar;
    public static PImage YELLOWGlowsharpedo_Attack, YELLOWGlowINVsharpedo_Attack;
    public static PImage Corpse;
    public static PImage logo;

    //Main Menu
    public static PImage  btnPlay, btnHelp, btnReturn, background, backgroundEmpty;
    public static PImage mouseClick, keyC, keyV, keyP;
    public static PImage btnBack;

    public LoadImagens(PApplet p){

        logo = p.loadImage(imagensDir + "Logo.png");
        float offset=0.9f;
        sharpedo = p.loadImage(imagensDir + "Sharpedo/shaperdo_normal.png");
        sharpedo.resize((int)(sharpedo.width/offset),(int)(sharpedo.height/offset));
        INVsharpedo = p.loadImage(imagensDir + "Sharpedo/INVshaperdo_normal.png");
        INVsharpedo.resize((int)(INVsharpedo.width/offset),(int)(INVsharpedo.height/offset));
        sharpedo_Attack = p.loadImage(imagensDir + "Sharpedo/sharpedo_Attack.png");
        sharpedo_Attack.resize((int)(sharpedo_Attack.width/offset),(int)(sharpedo_Attack.height/offset));
        INVsharpedo_Attack = p.loadImage(imagensDir + "Sharpedo/INVsharpedo_Attack.png");
        INVsharpedo_Attack.resize((int)(INVsharpedo_Attack.width/offset),(int)(INVsharpedo_Attack.height/offset));

        Glowsharpedo = p.loadImage(imagensDir + "Sharpedo/Glowsharpedo_normal.png");
        Glowsharpedo.resize((int)(Glowsharpedo.width/offset),(int)(Glowsharpedo.height/offset));
        GlowINVsharpedo = p.loadImage(imagensDir + "Sharpedo/GlowINVsharpedo_normal.png");
        GlowINVsharpedo.resize((int)(GlowINVsharpedo.width/offset),(int)(GlowINVsharpedo.height/offset));
        Glowsharpedo_Attack = p.loadImage(imagensDir + "Sharpedo/Glowsharpedo_Attack.png");
        Glowsharpedo_Attack.resize((int)(Glowsharpedo_Attack.width/offset),(int)(Glowsharpedo_Attack.height/offset));
        GlowINVsharpedo_Attack = p.loadImage(imagensDir + "Sharpedo/GlowINVsharpedo_Attack.png");
        GlowINVsharpedo_Attack.resize((int)(GlowINVsharpedo_Attack.width/offset),(int)(GlowINVsharpedo_Attack.height/offset));

        YELLOWGlowsharpedo = p.loadImage(imagensDir + "Sharpedo/YELLOWGlowsharpedo_normal.png");
        YELLOWGlowsharpedo.resize((int)(YELLOWGlowsharpedo.width/offset),(int)(YELLOWGlowsharpedo.height/offset));
        YELLOWGlowINVsharpedo = p.loadImage(imagensDir + "Sharpedo/YELLOWGlowINVsharpedo_normal.png");
        YELLOWGlowINVsharpedo.resize((int)(YELLOWGlowINVsharpedo.width/offset),(int)(YELLOWGlowINVsharpedo.height/offset));
        YELLOWGlowsharpedo_Attack = p.loadImage(imagensDir + "Sharpedo/YELLOWGlowsharpedo_Attack.png");
        YELLOWGlowsharpedo_Attack.resize((int)(YELLOWGlowsharpedo_Attack.width/offset),(int)(YELLOWGlowsharpedo_Attack.height/offset));
        YELLOWGlowINVsharpedo_Attack = p.loadImage(imagensDir + "Sharpedo/YELLOWGlowINVsharpedo_Attack.png");
        YELLOWGlowINVsharpedo_Attack.resize((int)(YELLOWGlowINVsharpedo_Attack.width/offset),(int)(YELLOWGlowINVsharpedo_Attack.height/offset));

        offset=1.7f;
        squirtle = p.loadImage(imagensDir + "Squirtle/squirtle.png");
        squirtle.resize((int)(squirtle.width/offset),(int)(squirtle.height/offset));
        INVsquirtle = p.loadImage(imagensDir + "Squirtle/INVsquirtle.png");
        INVsquirtle.resize((int)(INVsquirtle.width/offset),(int)(INVsquirtle.height/offset));

        Glowsquirtle = p.loadImage(imagensDir + "Squirtle/Glowsquirtle.png");
        Glowsquirtle.resize((int)(Glowsquirtle.width/offset),(int)(Glowsquirtle.height/offset));
        GlowINVsquirtle = p.loadImage(imagensDir + "Squirtle/GlowINVsquirtle.png");
        GlowINVsquirtle.resize((int)(GlowINVsquirtle.width/offset),(int)(GlowINVsquirtle.height/offset));

        YELLOWGlowsquirtle = p.loadImage(imagensDir + "Squirtle/YELLOWGlowsquirtle.png");
        YELLOWGlowsquirtle.resize((int)(YELLOWGlowsquirtle.width/offset),(int)(YELLOWGlowsquirtle.height/offset));
        YELLOWGlowINVsquirtle = p.loadImage(imagensDir + "Squirtle/YELLOWGlowINVsquirtle.png");
        YELLOWGlowINVsquirtle.resize((int)(YELLOWGlowINVsquirtle.width/offset),(int)(YELLOWGlowINVsquirtle.height/offset));

        offset=1.6f;
        magikarp = p.loadImage(imagensDir + "Magikarp/magikarp.png");
        magikarp.resize((int)(magikarp.width/offset),(int)(magikarp.height/offset));
        INVmagikarp = p.loadImage(imagensDir + "Magikarp/INVmagikarp.png");
        INVmagikarp.resize((int)(INVmagikarp.width/offset),(int)(INVmagikarp.height/offset));

        Glowmagikarp = p.loadImage(imagensDir + "Magikarp/Glowmagikarp.png");
        Glowmagikarp.resize((int)(Glowmagikarp.width/offset),(int)(Glowmagikarp.height/offset));
        GlowINVmagikarp = p.loadImage(imagensDir + "Magikarp/GlowINVmagikarp.png");
        GlowINVmagikarp.resize((int)(GlowINVmagikarp.width/offset),(int)(GlowINVmagikarp.height/offset));

        YELLOWGlowmagikarp = p.loadImage(imagensDir + "Magikarp/YELLOWGlowmagikarp.png");
        YELLOWGlowmagikarp.resize((int)(YELLOWGlowmagikarp.width/offset),(int)(YELLOWGlowmagikarp.height/offset));
        YELLOWGlowINVmagikarp = p.loadImage(imagensDir + "Magikarp/YELLOWGlowINVmagikarp.png");
        YELLOWGlowINVmagikarp.resize((int)(YELLOWGlowINVmagikarp.width/offset),(int)(YELLOWGlowINVmagikarp.height/offset));

        offset=1.7f;
        omastar = p.loadImage(imagensDir + "Omastar/omastar.png");
        omastar.resize((int)(omastar.width/offset),(int)(omastar.height/offset));
        INVomastar = p.loadImage(imagensDir + "Omastar/INVomastar.png");
        INVomastar.resize((int)(INVomastar.width/offset),(int)(INVomastar.height/offset));

        Glowomastar = p.loadImage(imagensDir + "Omastar/Glowomastar.png");
        Glowomastar.resize((int)(Glowomastar.width/offset),(int)(Glowomastar.height/offset));
        GlowINVomastar = p.loadImage(imagensDir + "Omastar/GlowINVomastar.png");
        GlowINVomastar.resize((int)(GlowINVomastar.width/offset),(int)(GlowINVomastar.height/offset));

        YELLOWGlowomastar = p.loadImage(imagensDir + "Omastar/YELLOWGlowomastar.png");
        YELLOWGlowomastar.resize((int)(YELLOWGlowomastar.width/offset),(int)(YELLOWGlowomastar.height/offset));
        YELLOWGlowINVomastar = p.loadImage(imagensDir + "Omastar/YELLOWGlowINVomastar.png");
        YELLOWGlowINVomastar.resize((int)(YELLOWGlowINVomastar.width/offset),(int)(YELLOWGlowINVomastar.height/offset));

        offset=14f;
        Corpse = p.loadImage(imagensDir + "Magikarp/magikarpCorpse.png");
        Corpse.resize((int)(Corpse.width/offset),(int)(Corpse.height/offset));

        //Main Menu
        offset=1f;
        btnPlay = p.loadImage("Projeto/imagens/MainMenu/btnPlay.png");
        btnPlay.resize((int)(btnPlay.width/offset),(int)(btnPlay.height/offset));

        btnHelp = p.loadImage("Projeto/imagens/MainMenu/btnHelp.png");
        btnHelp.resize((int)(btnHelp.width/offset),(int)(btnHelp.height/offset));

        btnReturn = p.loadImage("Projeto/imagens/MainMenu/btnReturn.png");
        btnReturn.resize((int)(btnReturn.width/offset),(int)(btnReturn.height/offset));

        background = p.loadImage("Projeto/imagens/MainMenu/background.png");
        background.resize((int)(background.width/offset),(int)(background.height/offset));

        backgroundEmpty = p.loadImage("Projeto/imagens/MainMenu/backgroundEmpty.png");
        backgroundEmpty.resize((int)(backgroundEmpty.width/offset),(int)(backgroundEmpty.height/offset));

        offset=2f;
        mouseClick = p.loadImage("Projeto/imagens/MainMenu/mouseClick.png");
        mouseClick.resize((int)(mouseClick.width/offset),(int)(mouseClick.height/offset));

        keyC = p.loadImage("Projeto/imagens/MainMenu/teclaC.png");
        keyC.resize((int)(keyC.width/offset),(int)(keyC.height/offset));

        keyV = p.loadImage("Projeto/imagens/MainMenu/teclaV.png");
        keyV.resize((int)(keyV.width/offset),(int)(keyV.height/offset));

        keyP = p.loadImage("Projeto/imagens/MainMenu/teclaP.png");
        keyP.resize((int)(keyP.width/offset),(int)(keyP.height/offset));

        offset=13f;
        btnBack = p.loadImage("Projeto/imagens/back.png");
        btnBack.resize((int)(btnBack.width/offset),(int)(btnBack.height/offset));
    }
}
