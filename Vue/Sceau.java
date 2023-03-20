package Vue;
import Modele.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Image;

public class Sceau{
    public final int longeur = 100; // m
    public final int hauteur = 50;

    public double X, Y; // m
    public double speedX = 100; // m
    private Court court;
    private BufferedImage image; 

    Sceau(Court court){
        this.court = court;
        X = court.getWidth()/2 - longeur/2;
        Y = court.getWidth() - (hauteur + hauteur/2);

        image = ImageImport.getImage("bucket.png", longeur, hauteur);
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {  
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
    

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
    
        return dimg;
    } 

    public BufferedImage getImage(){
        return image;
    }


    public void move(double deltaT){
        double nextX = X + deltaT * speedX;

        // speedX = speedX
        if (touchedWallX(nextX)){
            speedX = -speedX;
            nextX = X + deltaT * speedX;
        };
        //System.out.println(inside(b));
        X = nextX;
    }

    public boolean inside(Ball b){
        return X <= b.nextBallX && b.nextBallX <= X + longeur &&
        Y <= b.nextBallY && b.nextBallY <= Y + hauteur;

    }

    public boolean touchedWallX(double nextX){
        return nextX < 0 || nextX> court.getWidth() - longeur;
    }
}