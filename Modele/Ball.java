package Modele;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import Vue.Court;
import Vue.ImageImport;
import Vue.Sceau;
import java.io.*;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
public class Ball{

    public final static int ballRadius = 10; // m

    public double ballX, ballY; // m
    public double ballSpeedX, ballSpeedY; // m

    public double nextBallX,nextBallY;

    // private int height=500;
    // private int width=500;
    private static boolean musicOn = true;

    public double p1,p2,p3,p4,p5;

    private boolean ispresent = true;
    private boolean atoucherpegs = false;
    private boolean hitground = false;
    private boolean notBlocked = true;
    private ArrayList<Point> previousPosition = new ArrayList<Point>();

    private double g=300; // m/s
    private double coeffRebond = 0.8;
    private int combo = 0;
    private File sound = ImageImport.getAudioFile();
    AudioInputStream audioStream;
    AudioFormat format;
    DataLine.Info info = new DataLine.Info(Clip.class, format); 
    Clip audioClip;
    {
    try {
        audioStream = AudioSystem.getAudioInputStream(ImageImport.getAudioFile());
        audioClip = (Clip) AudioSystem.getLine(info);
        format = audioStream.getFormat();
        audioClip.open(audioStream);
    } catch (Exception e ) {
        e.printStackTrace();
    }
    }


    private static int selecteurImage = 0 ;
    public static int getSelecteurImage() {
        return selecteurImage;
    }

    public static void setSelecteurImage(int selecteurImage) {
        Ball.selecteurImage = selecteurImage;
    }
    private static BufferedImage image = ImageImport.getImage("Ball/ball.png", (int) ballRadius*2, (int) ballRadius*2);
    public static void setImage(BufferedImage skin){
        Ball.image = skin ;
    }
   
 
    private Court court;
    private Pegs pegderniertoucher;


    public boolean inLevel = true;

    double x,y;


    /* Important coordonée de la balle centre en X mais tout en haut pour Y */

    public Court getCourt() {
        return court;
    }

    public static BufferedImage getImgBall(){
        return image;
    }

    public Ball(int x,int y,int vx0,int vy0,Court c){
        ballX=x;
        ballY=y;
        ballSpeedX=vx0;
        ballSpeedY=vy0;
        court=c;
    }
    public double getG() {
        return g;
    }

    public void setPresent(boolean b){
        ispresent = b;
    }

    public boolean isPresent(){
        return ispresent;
    }

    public void updateBall(double deltaT,Sceau sceau) {
        // first, compute possible next position if nothing stands in the way
        nextBallX = ballX + deltaT * ballSpeedX;
        nextBallY = ballY + deltaT * ballSpeedY + 1/2*g*(deltaT*deltaT);

        ballSpeedY = ballSpeedY + g*deltaT;
        
        // next, see if the ball would meet some obstacle
        if (touchedWallY(nextBallY)) { 
            ballSpeedY = -ballSpeedY*coeffRebond;
            // System.out.println("touched wall Y");
            nextBallY = ballY + deltaT * ballSpeedY + 1/2*g*(deltaT*deltaT);
        }

        if (touchedWallX(nextBallX)){
            ballSpeedX = -ballSpeedX;
            nextBallX = ballX + deltaT * ballSpeedX;
        }
        if (sceau.toucheBordureSceau(this)){
            ballSpeedY =- ballSpeedY;
        }

        if (sceau.inside(this)){
            System.out.println("inside");
            sceau.getCourt().augmenteNbDeBall();
            hitground=true;
            ispresent=false;
        }
        // detect blocked ball
        // en probabilité la chance que la balle soit au meme endroit 5 fois de suite est tres faible si elle n'est pas bloqué
        previousPosition.add(new Point((int)ballX,(int)ballY));
        if (previousPosition.size()>5){
            previousPosition.remove(0);
        }
        if (previousPosition.size()==5){
            p1 = previousPosition.get(0).getX();
            p2 = previousPosition.get(1).getX();
            p3 = previousPosition.get(2).getX();
            p4 = previousPosition.get(3).getX();
            p5 = previousPosition.get(4).getX();
            if (p1==p2 && p2==p3 && p3==p4 && p4==p5){
                notBlocked = false;
            }
        }


        Pegs p = null;
        if (notBlocked){
        p = touchedPegs();
        }



        
        if (p!=null && p != pegderniertoucher){
        if (p!=null && !atoucherpegs && inLevel){
            if(musicOn){
                try {
                    playSound();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            if (!p.getHit()){combo++;}
            p.setTouche(true);
            double ux = (nextBallX+ ballRadius) - (p.getX());
            double uy = (nextBallY+ballRadius) - (p.getY());
            double vx = ballSpeedX;
            double vy = ballSpeedY;
            ballSpeedX = vx - 2*ux*(ux*vx + uy*vy)/(ux*ux + uy*uy);
            ballSpeedY = vy - 2*uy*(ux*vx + uy*vy)/(ux*ux + uy*uy);
            ballSpeedX = coeffRebond * ballSpeedX;
            ballSpeedY = coeffRebond * ballSpeedY;
            
            pegderniertoucher = p;
        }
        }else if (p==null){
                pegderniertoucher = null;
        }
        ballX = nextBallX;
        ballY = nextBallY;
    }

    public void inLevelTrue(){
        inLevel = true;
    }
    public void inLevelFalse(){
        inLevel = false;
    }


    public boolean touchedWallX(double nextBallX){
        return nextBallX < 0 || nextBallX> court.getWidth() - ballRadius;
    }

    public boolean touchedWallY(double nextBallY){
        if (nextBallY > court.getHeight() - ballRadius*2 - 15){
            hitground=true;
        }
        return nextBallY < 0 || nextBallY > court.getHeight() - ballRadius*2 - 15;
    }

    private void playSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioClip.stop();
        audioClip.setFramePosition(0);
        audioClip.start();
    }
    


    public Pegs touchedPegs(){
        Pegs p=null;
        for (Pegs peg: court.getPegs()){
            
            if(peg.contains(nextBallX + ballRadius, nextBallY + ballRadius )){
                p=peg;
            }
        }
        if (p==null){
            atoucherpegs=false;
        }
        return p;
    }

    public boolean getHitGround(){
        return hitground;
    }

    public Image getImage() {
        return image;
    }

    public int getCombo() {
        return combo;
    }

    public void setCombo(int i) {
        combo = i;
    }

    public static void setSound( boolean b){
        musicOn = b;
    }

    public int getComboScore() {
        int score = 1;
        for (int i = 2; i <= combo; i++) {
            score += i;
        }
        return score;
    }
    



}