import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent ;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
// import ImageImport ;




public class Canon extends JPanel{
    
    //pour le tracer la ligne de viser et eviter des calculs inutiles:
        int[] x = new int[10];
        int[] y = new int[10];
        double angleOrientation_old;
    
    private Ball balleATirer ;
    private BufferedImage image; 
    // Pointer à gauche revient à 0, à droite pi
    // en Radiant
    private double angleOrientation ;
    private Court court;

    
    // parametre du canon :
        private double gravity = 100 ; 
        private int maxDistanceLigneTir ;
        private Point pivotDeRotation ;
        private double vitesseTir = 600 ;
        private double vitesseTirX = 0 ;
        private double vitesseTirY = 0 ;
        private double tailleCanon = 9/100.0; // en pourcentage de la taille de l'écran



    public Canon(Court court){ 

        
        
        // definition du Gameview
        this.court = court ;
        int largeurTerrain = court.getWidth() ;
        
        // Mise à l'echelle du canon :
        int tailleImage = (int) (largeurTerrain * tailleCanon ) ;
        image = ImageImport.getImage("cannonGrand.png", tailleImage, tailleImage) ;
 
        
        // position du canon initial à la vertical
        angleOrientation = Math.PI/2 ;     
        
        setBounds(largeurTerrain/2 - image.getWidth()/2, getHeight()/4, image.getWidth(), image.getHeight());

        // definition point de pivot de rotation
        pivotDeRotation = new Point(getX() + getWidth()/2, getY() + getHeight()/2) ;


        // def max distance ligne tir :
            maxDistanceLigneTir = court.getHeight()/3 ;

        setOpaque(false);
    }

    // TODO pas forcément utile ça dépend de l'implémentation futur
    public void setBalleATirer(Ball balleATirer) {
        this.balleATirer = balleATirer;
        gravity = balleATirer.getG();
        
        // Placement graphique :
        placementBallCanon();
        
    }
    
    private void placementBallCanon(){
        balleATirer.ballX = pivotDeRotation.x -balleATirer.ballRadius/2 - Math.cos(angleOrientation)*( this.getHeight()/2 );
        balleATirer.ballY = pivotDeRotation.y -balleATirer.ballRadius/2 + Math.sin(angleOrientation)*( this.getHeight()/2 ); 
    }



     /**
     * @description Défini la vitesse de la balle pour amorcer le tir 
     *  place un nouvelle balle dans le lanceur
     * @author Thibault
     * @return Ball // ball qui vient d'être tirer
     */
    public Ball tirer(){
        // place la balle au bout du canon
        placementBallCanon();

        // definition de la vitesse de la balle
        balleATirer.ballSpeedX =  - vitesseTir*Math.cos(angleOrientation);
        balleATirer.ballSpeedY =  vitesseTir*Math.sin(angleOrientation);

        // creation d'une nouvelle ball
        Ball Ball_lancer = balleATirer ;
        balleATirer = new Ball(0, 0, 0, 0, balleATirer.getCourt()) ;
        gravity = balleATirer.getG() ;

        // return la balle tirer
        return Ball_lancer ;
    }

   
    /**
     * @author Thibault
     * @param Mouse Event
     * @description Permet de faire bouger le canon en fonction des coordonnées de la souris
     */
    public void DeplacementCanon(MouseEvent e){
        // calcul angle du canon 
        angleOrientation = Math.atan2((e.getY() - pivotDeRotation.y), (pivotDeRotation.x-e.getX()));

        // Correction pour eviter des positions incongrue
            if (angleOrientation >= 170*(Math.PI/180) || angleOrientation < 10*(Math.PI/180) ){
                if (e.getX()> pivotDeRotation.x) {
                    angleOrientation = 170*(Math.PI/180);
                }else{
                    angleOrientation = 10*(Math.PI/180) ; 
                }
            }

        // System.out.println(angleOrientation*(180/Math.PI));        
    }

    private double calculDeltaT(Point depart){
        double a = gravity*11*11 / 2.0; 
        double b = 11*vitesseTir*Math.sin(angleOrientation) ; 
        double delta = b*b - 4*a*(depart.y - maxDistanceLigneTir) ;

        return  (-b + Math.sqrt(delta))/(2*a) ; //racine du polynome
    }

    public void updateTrajetLigne()  {
        Point depart = new Point((int)(pivotDeRotation.x + court.getInsets().left -  Math.cos(angleOrientation)*(this.getHeight()/2)), (int)(pivotDeRotation.y + Math.sin(angleOrientation)*(this.getHeight()/2) + court.getInsets().top )) ;
        
        //calcul du point d'arrivé de la ligne de visée
        boolean touchpegs = false;
        double deltaT = calculDeltaT(depart) ;
        int i = 0; // adapte le deltaT pour que la ligne de viser s'adpate avec la taille maximun imposer
        if(angleOrientation != angleOrientation_old){
            vitesseTirX = -vitesseTir*Math.cos(angleOrientation);
            vitesseTirY = vitesseTir*Math.sin(angleOrientation);
            for (int t = 1; t < 11; t++) {
                x[t-1] = (int)(depart.x - deltaT*t * vitesseTir * Math.cos(angleOrientation));
                y[t-1] = (int)(depart.y + deltaT *t* vitesseTir * Math.sin(angleOrientation) + gravity*deltaT * deltaT*t*t  / 2.0);
                
            }
        }
        angleOrientation_old = angleOrientation;
    }
    
    public int[] getXTab() {
        return x;
    }

    public int[] getYTab() {
        return y;
    }

    // @Override
    // public void paint(Graphics g) {
    //     super.paint(g);

    //     Graphics2D g2DGameview = (Graphics2D) court.getGraphics() ;
       
    //     Point depart = new Point((int)(pivotDeRotation.x + court.getInsets().left -  Math.cos(angleOrientation)*(this.getHeight()/2)), (int)(pivotDeRotation.y + Math.sin(angleOrientation)*(this.getHeight()/2) + court.getInsets().top )) ;
        
    //     //calcul du point d'arrivé de la ligne de visée
    //     boolean touchpegs = false;
    //     double deltaT = calculDeltaT(depart) ;
    //     int i = 0; // adapte le deltaT pour que la ligne de viser s'adpate avec la taille maximun imposer
    //     if(angleOrientation != angleOrientation_old){
    //         vitesseTirX = -vitesseTir*Math.cos(angleOrientation);
    //         vitesseTirY = vitesseTir*Math.sin(angleOrientation);
    //         for (int t = 1; t < 11; t++) {
    //             x[t-1] = (int)(depart.x - deltaT*t * vitesseTir * Math.cos(angleOrientation));
    //             y[t-1] = (int)(depart.y + deltaT *t* vitesseTir * Math.sin(angleOrientation) + gravity*deltaT * deltaT*t*t  / 2.0);
                
    //         }
    //     }
    //     angleOrientation_old = angleOrientation;

    //     // traçage ligne de viser
    //         g2DGameview.setColor(Color.RED);
    //         float dash1[] = {20.0f};
    //         BasicStroke dashed = new BasicStroke(5.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
    //         g2DGameview.setStroke(dashed);
    //         g2DGameview.drawPolyline(x, y, 10);
    // }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g ;
        
        // orientation du canon :
        
        g2D.rotate( Math.PI/2- angleOrientation, getWidth()/2, getHeight()/2);
        g2D.drawImage(image, 0, 0, this) ;
        
    }


}