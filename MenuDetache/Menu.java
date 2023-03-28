import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

class Menu extends JFrame {

    private int largeur;
    private int hauteur;

    private int middleH;
    private int middleW;

    JButton btnPlay;
    JButton btnCampagne;
    JButton btnOption;
    JButton btnQuit;

    Icon imageIconPlay2;
    Icon imageIconPlay;

    Icon imageIconOptions2;
    Icon imageIconOptions;

    Icon imageIconCampaing2;
    Icon imageIconCampaing;

    Icon imageIconQuit2;
    Icon imageIconQuit;

    Menu(int hauteur,int largeur) { 
        this.hauteur=hauteur;
        this.largeur=largeur;

        imageIconPlay = new ImageIcon("/Users/Joseph/Desktop/TP_Java/Menu/planche police blanche.png");
        Image image = ((ImageIcon) imageIconPlay).getImage(); // transform it 
        Image newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIconPlay = new ImageIcon(newimg);

        imageIconPlay2 = new ImageIcon("/Users/Joseph/Desktop/TP_Java/Menu/planche tour police jaune.png");
        image = ((ImageIcon) imageIconPlay2).getImage(); // transform it 
        newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIconPlay2 = new ImageIcon(newimg);



        imageIconOptions = new ImageIcon("/Users/Joseph/Desktop/TP_Java/Menu/planche option blanc.png");
        image = ((ImageIcon) imageIconOptions).getImage(); // transform it 
        newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIconOptions = new ImageIcon(newimg);

        imageIconOptions2 = new ImageIcon("/Users/Joseph/Desktop/TP_Java/Menu/planche option jaune.png");
        image = ((ImageIcon) imageIconOptions2).getImage(); // transform it 
        newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIconOptions2 = new ImageIcon(newimg);



        imageIconCampaing = new ImageIcon("/Users/Joseph/Desktop/TP_Java/Menu/wetransfer_planche-campaing-blanc-png_2023-03-24_1735/planche campaing blanc.png");
        image = ((ImageIcon) imageIconCampaing).getImage(); // transform it 
        newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIconCampaing = new ImageIcon(newimg);

        imageIconCampaing2 = new ImageIcon("/Users/Joseph/Desktop/TP_Java/Menu/wetransfer_planche-campaing-blanc-png_2023-03-24_1735/planche CAMPAING JAUNE.png");
        image = ((ImageIcon) imageIconCampaing2).getImage(); // transform it 
        newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIconCampaing2 = new ImageIcon(newimg);



        imageIconQuit = new ImageIcon("/Users/Joseph/Desktop/TP_Java/Menu/wetransfer_planche-campaing-blanc-png_2023-03-24_1735/planche quit blanc.png");
        image = ((ImageIcon) imageIconQuit).getImage(); // transform it 
        newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIconQuit = new ImageIcon(newimg);

        imageIconQuit2 = new ImageIcon("/Users/Joseph/Desktop/TP_Java/Menu/wetransfer_planche-campaing-blanc-png_2023-03-24_1735/planche quit jaune.png");
        image = ((ImageIcon) imageIconQuit2).getImage(); // transform it 
        newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIconQuit2 = new ImageIcon(newimg);


        middleW = largeur /2;
        middleH = hauteur/2 + 50;

        btnPlay = new JButton(imageIconPlay);
        btnPlay.setBounds(middleW-100,middleH-25-140,200,50); 

        btnPlay.addMouseListener((MouseListener) new MouseAdapter() 
        {
           public void mouseEntered(MouseEvent evt) 
           {
                btnPlay.setIcon(imageIconPlay2);
           }
           public void mouseExited(MouseEvent evt) 
           {
                btnPlay.setIcon(imageIconPlay);
           }
        });

        btnCampagne = new JButton(imageIconCampaing);
        btnCampagne.setBounds(middleW-100,middleH-25-70,200,50);
        
        btnCampagne.addMouseListener((MouseListener) new MouseAdapter() 
        {
           public void mouseEntered(MouseEvent evt) 
           {
            btnCampagne.setIcon(imageIconCampaing2);
           }
           public void mouseExited(MouseEvent evt) 
           {
            btnCampagne.setIcon(imageIconCampaing);
           }
        });
        

        btnOption = new JButton(imageIconOptions);
        btnOption.setBounds(middleW-100,middleH-25,200,50);  

        btnOption.addMouseListener((MouseListener) new MouseAdapter() 
        {
           public void mouseEntered(MouseEvent evt) 
           {
            btnOption.setIcon(imageIconOptions2);
           }
           public void mouseExited(MouseEvent evt) 
           {
            btnOption.setIcon(imageIconOptions);
           }
        });

        btnQuit = new JButton(imageIconQuit);
        btnQuit.setBounds(middleW-100,middleH-25+70,200,50);  

        btnQuit.addMouseListener((MouseListener) new MouseAdapter() 
        {
           public void mouseEntered(MouseEvent evt) 
           {
            btnQuit.setIcon(imageIconQuit2);
           }
           public void mouseExited(MouseEvent evt) 
           {
            btnQuit.setIcon(imageIconQuit);
           }
        });

        add(btnPlay);
        add(btnCampagne);
        add(btnOption);
        add(btnQuit);
        add(new Test());
        
        setSize(largeur, hauteur);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public class Test extends JPanel{
        private BufferedImage background;
        private BufferedImage title;
        public Test() {
            try {
                background = ImageIO.read(new File("/Users/Joseph/Desktop/TP_Java/Menu/Vue_Image_menuBackground.jpg"));
                background = resizeImage(background, largeur, hauteur);

                title = ImageIO.read(new File("/Users/Joseph/Desktop/TP_Java/Menu/truc jojo.png"));
            } catch (IOException ex) {
                
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.drawImage(background, 0, 0, this);
            g2d.drawImage(title, middleW-title.getWidth()/2-10, 40, this);
            


        }

    }
    
    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
    public static void main(String[] args){
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); 
        int hauteur = (int)tailleEcran.getHeight(); 
        int largeur = (int)tailleEcran.getWidth();
        Menu m = new Menu(hauteur,largeur);
        m.setVisible(true);
    }

}