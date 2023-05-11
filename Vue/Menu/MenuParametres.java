package Vue.Menu;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import Modele.Ball;

import Vue.Controleur;
import Vue.ImageImport;

public class MenuParametres extends JPanel {

    // Controleur
    private Controleur controleur;
    int width;
    int height;

    private int middleH;
    private int middleW;

    private BufferedImage background;
    private JButton btnRetour;

   
    private Skin skin ; 

    JButton plus;
    JButton minus;

    JLabel vitesse;

    public MenuParametres(Controleur c) {

        this.controleur = c;
        width = controleur.getWidth();
        height = controleur.getHeight();
        middleW = width / 2;
        middleH = height / 2 + 50;
        setSize(width, height);
        double pourcentageW = getSize().width / 1920.0;
        double pourcentageH = getSize().height / 1080.0;
        setLayout(null);
        setVisible(true);

        // background
        background = ImageImport.getImage("Menu/menuBackground.jpg", width, height);



        // BoutonMenu back
        btnRetour = new BoutonMenu("Retour", 200, 50);
        btnRetour.setLocation(40, 40);
        btnRetour.addActionListener(e -> controleur.launchMenu());
        add(btnRetour);


        skin = new Skin(width/5, height/2) ;
        skin.setVisible(true);
        int xCenterSkin = (width)/3; 
        skin.setLocation(xCenterSkin - (skin.getWidth()/2), (height - skin.getHeight())/2);
        add(skin) ;
        BoutonMenu buttonMusicOn = new BoutonMenu("Music On", 200, 50);
        buttonMusicOn.setLocation(middleW - (buttonMusicOn.getWidth()/2), middleH + (int)(148*pourcentageH) );
        BoutonMenu buttonMusicOff = new BoutonMenu("Music Off", 200, 50);
        buttonMusicOff.setLocation(middleW- (buttonMusicOn.getWidth()/2), middleH + (int)(148*pourcentageH));
        if (controleur.getMusic()) {
            buttonMusicOn.setVisible(true);
            buttonMusicOff.setVisible(false);
        } else {
            buttonMusicOn.setVisible(false);
            buttonMusicOff.setVisible(true);
        }
        add(buttonMusicOff);
        add(buttonMusicOn);
        JButton musiconIconOn = new JButton();
        musiconIconOn.setLocation(middleW + (buttonMusicOn.getWidth()/2) + 10, middleH + (int)(148*pourcentageH));
        musiconIconOn.setSize(50, 50);
        musiconIconOn.setIcon(new ImageIcon(ImageImport.getImage("Menu/MusicOn.png", 50, 50)));
        musiconIconOn.setBorderPainted(false);
        musiconIconOn.setContentAreaFilled(false);
        musiconIconOn.setFocusPainted(false);
        musiconIconOn.setOpaque(false);
        JButton musiconIconOff = new JButton();
        musiconIconOff.setLocation(middleW + (buttonMusicOn.getWidth()/2) + 10, middleH + (int)(148*pourcentageH));
        musiconIconOff.setSize(50, 50);
        musiconIconOff.setIcon(new ImageIcon(ImageImport.getImage("Menu/MusicOff.png", 50, 50)));
        musiconIconOff.setBorderPainted(false);
        musiconIconOff.setContentAreaFilled(false);
        musiconIconOff.setFocusPainted(false);
        musiconIconOff.setOpaque(false);
        
        buttonMusicOn.addActionListener(e -> {
            controleur.setMusic(false);
            controleur.stopMusic();
            buttonMusicOn.setVisible(false);
            buttonMusicOff.setVisible(true);
            musiconIconOff.setVisible(true);
            musiconIconOn.setVisible(false);            
        });
        buttonMusicOff.addActionListener(e -> {
            controleur.setMusic(true);
            controleur.playMusic();
            buttonMusicOn.setVisible(true);
            buttonMusicOff.setVisible(false);
            musiconIconOff.setVisible(false);
            musiconIconOn.setVisible(true);
        });
        musiconIconOn.addActionListener(e -> {
            controleur.setMusic(false);
            controleur.stopMusic();
            buttonMusicOn.setVisible(false);
            buttonMusicOff.setVisible(true);
            musiconIconOff.setVisible(true);
            musiconIconOn.setVisible(false);
        });
        musiconIconOff.addActionListener(e -> {
            controleur.setMusic(true);
            controleur.playMusic();
            buttonMusicOn.setVisible(true);
            buttonMusicOff.setVisible(false);
            musiconIconOff.setVisible(false);
            musiconIconOn.setVisible(true);
        });
        if (controleur.getMusic()) {
            musiconIconOn.setVisible(true);
            musiconIconOff.setVisible(false);
        } else {
            musiconIconOn.setVisible(false);
            musiconIconOff.setVisible(true);
        }
        add(musiconIconOff);
        add(musiconIconOn);

        BoutonMenu buttonSoundOn = new BoutonMenu("Sound On", 200, 50);
        buttonSoundOn.setLocation(middleW - (buttonSoundOn.getWidth()/2), middleH + (int)(50*pourcentageH));
        BoutonMenu buttonSoundOff = new BoutonMenu("Sound Off", 200, 50);
        buttonSoundOff.setLocation(middleW - (buttonSoundOn.getWidth()/2), middleH + (int)(50*pourcentageH));
        if (controleur.getSound()) {
            buttonSoundOn.setVisible(true);
            buttonSoundOff.setVisible(false);
        } else {
            buttonSoundOn.setVisible(false);
            buttonSoundOff.setVisible(true);
        }
        buttonSoundOn.addActionListener(e -> {
            controleur.setSound(false);
            buttonSoundOn.setVisible(false);
            buttonSoundOff.setVisible(true);
        });
        buttonSoundOff.addActionListener(e -> {
            controleur.setSound(true);
            buttonSoundOn.setVisible(true);
            buttonSoundOff.setVisible(false);
        });
        
        add(buttonSoundOff);
        add(buttonSoundOn);
        

        BoutonMenu backgroundBounton = new BoutonMenu("Background", 200, 50);
        backgroundBounton.setLocation(middleW - (backgroundBounton.getWidth()/2), middleH - (int)(48*pourcentageH));
        backgroundBounton.addActionListener(e -> {
            //TODO
        });
        add(backgroundBounton);




        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new BoutonMenu.BoutonClavier(new BoutonMenu[] {}, () -> controleur.launchMenu()));
    }


    public BufferedImage getEditedImage(String txt, int width, int height) {
        BufferedImage buffImg = ImageImport.getImage("Menu/planche_blanche.png", width, height);
        Graphics g = buffImg.getGraphics();
        Font rightFont = ImageImport.rightSizeCarton(txt, width);
        FontMetrics metrics = g.getFontMetrics(rightFont);
        g.setFont(rightFont);
        g.setColor(Color.WHITE);
        g.drawString(txt, width/2 - metrics.stringWidth(txt)/2, height/2  + metrics.getAscent()/2);
        return buffImg ;
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
    }
    
    public class Skin extends JPanel{
        private String[] allNameImage = new String[] { "Ball/ball.png", "Ball/basketBall.png", "Ball/smileysBall.png",
        "Ball/soccerBall.png", "Ball/tennisBall.png" };
        private BoutonBall[] tabBouton;

        private BufferedImage image ;


        Skin(int width, int height){
            setLayout(null);
            setSize(width, height);
            setOpaque(false);

            image = getEditedImage("    Skins    ", (width*2)/3, 50) ;

            int tailleSkin = (height-image.getHeight()) /allNameImage.length ; 
            System.out.println(tailleSkin);
            int gapSkin = tailleSkin /5 ;
            tailleSkin = (tailleSkin*4) /5 ;

            tabBouton = new BoutonBall[allNameImage.length ];

            int y = image.getHeight() + gapSkin ; 
            for (int i = 0 ; i < allNameImage.length ; i++){
                tabBouton[i] = new BoutonBall(i, tailleSkin);
                tabBouton[i].setLocation((this.getWidth()- tailleSkin)/2, y);
                add(tabBouton[i]);
                y += gapSkin + tailleSkin ;
            }
            
            iluminateButton() ;
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image,(this.getWidth() - image.getWidth())/2, 0, image.getWidth(), image.getHeight(),  this) ;
        }
        

        private void iluminateButton() {
            for (int i = 0; i < allNameImage.length; i++) {
                tabBouton[i].repaint();
            }
        }

        class BoutonBall extends JButton {

            int diametre, selecteur;
            ImageIcon imageIconNormal;
            ImageIcon imageIconOnHover;
    
    
            BoutonBall(int selecteur, int diametre) {
                this.diametre = diametre;
                this.selecteur = selecteur ;
                String texteImage = allNameImage[selecteur];
    
                BufferedImage tempNormal = ImageImport.getImage(texteImage, diametre, diametre);
    
                BufferedImage tempHover = ImageImport.getImage("Ball/hoverBall.png", diametre, diametre);
    
                Graphics g = tempHover.createGraphics();
                g.drawImage(ImageImport.getImage(texteImage, diametre - 6, diametre - 6), 3, 3, this);
    
                imageIconNormal = new ImageIcon(tempNormal);
                imageIconOnHover = new ImageIcon(tempHover);
    
                setIcon(imageIconNormal);
                addMouseListener((MouseListener) new MouseAdapter() {
                    public void mouseEntered(MouseEvent evt) {
                        setIcon(imageIconOnHover);
                    }
    
                    public void mouseExited(MouseEvent evt) { 
                        setIcon(imageIconNormal);
                    }
    
                    public void mousePressed(MouseEvent evt) {
                        Ball.setSelecteurImage(selecteur);
                        Ball.setImage((ImageImport.getImage(texteImage, 20, 20)));
                        iluminateButton() ;
                    }
                });
                // Parametrages du bouton
                setBorderPainted(false);
                setContentAreaFilled(false);
                setFocusPainted(false);
                setOpaque(false);
                setSize(diametre, diametre);
                setVisible(true);
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (selecteur == Ball.getSelecteurImage()) {
                    g.setColor(Color.RED);
                    g.fillOval((this.diametre*4)/10, (this.diametre*4)/10, this.diametre/5, this.diametre/5);
                }
            }
        }
    }

    

}
