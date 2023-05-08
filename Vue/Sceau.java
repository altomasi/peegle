package Vue;
import Modele.*;

import java.awt.image.BufferedImage;

public class Sceau{
    public final int longeur = 100; // m
    public final int hauteur = 50;
    public double speedX = 100; // m

    private int bordure = (longeur * 65) / 405;

    public double Xb, Yb; // m
    public double Xh, Yh; // m
    private Court court;
    private BufferedImage imageBAS,imageHAUT; 

    Sceau(Court court){
        System.out.println(bordure);
        this.court = court;
        Xb = court.getWidth()/2 - longeur/2;
        Yb = court.getHeight() - (hauteur);

        Xh = court.getWidth()/2 - longeur/2;
        Yh = court.getHeight() - (hauteur);

        imageBAS = ImageImport.getImage("bucketBAS.png", longeur, hauteur);
        imageHAUT = ImageImport.getImage("bucketHAUT.png", longeur, hauteur);

        // tester si le sceau est bien dans le court
        
    }
    public BufferedImage getImageHAUT(){
        return imageHAUT;
    }

    public BufferedImage getImageBAS(){
        return imageBAS;
    }


    public void move(double deltaT){
        double nextXb = Xb + deltaT * speedX;

        // speedX = speedX
        if (touchedWallX(nextXb)){
            speedX = -speedX;
            nextXb = Xb + deltaT * speedX;
        };
        //System.out.println(inside(b));
        Xb = nextXb;
        Xh=nextXb;
    }

    public boolean inside(Ball b){
        return Xb + bordure <= b.nextBallX && b.nextBallX + Ball.ballRadius*2 <= Xb + longeur - bordure &&
        Yb  <= b.nextBallY && b.nextBallY <= Yb + hauteur;

    }

    public boolean touchedWallX(double nextX){
        return nextX < 0 || nextX> court.getWidth() - longeur;
    }
    public boolean toucheBordureSceau(Ball b){
        return ((Xb <= b.nextBallX + Ball.ballRadius*2  && b.nextBallX   <= Xb + bordure) || (Xb + longeur - bordure  <= b.nextBallX + Ball.ballRadius*2  && b.nextBallX <= Xb  + longeur) ) && 
        Yb <= b.nextBallY && b.nextBallY <= Yb + hauteur; //hardcoding pour toucher plus bas
    }

    public Court getCourt(){
        return court;
    }
}