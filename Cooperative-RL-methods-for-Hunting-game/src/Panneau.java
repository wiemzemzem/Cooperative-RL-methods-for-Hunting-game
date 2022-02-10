/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myprogram;

/**
 *
 * @author Wiem
 */
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Panneau extends JPanel {

    private int posX = 0;
    private int posY = 0;

    private int[][] vectPanX, vectPanY;//positions des robots 0 1 2 3
    private int[] lengthVectPanXY;
    private int[][][] coloration;
    int nbrRobots;
    MyEnv envColoration;
    private double[][] PherProgress;
    private int[][] ForagingColor, FerryingColor;

    public Panneau(MyEnv env, int nbr, int[] lengthVectXY, int[][] vectX, int[][] vectY) {
        vectPanX = vectX;
        vectPanY = vectY;//positions des robots 0 1 2 3 
        lengthVectPanXY = lengthVectXY;//taille remplie des VectX et vectY
        nbrRobots = nbr;//nbre de robots existants
        coloration = new int[1024][4][nbr];// 1024:positions 4:actions nbr:robots
        envColoration = env;
        PherProgress = new double[12][2];
        ForagingColor = new int[12][3];
        FerryingColor = new int[12][3];
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void paintComponent(Graphics g) {
      // System.out.println("repaint"+i);i++;
        //On décide d'une couleur de fond pour notre rectangle
       // g.setColor(Color.white);
        //On dessine celui-ci afin qu'il prenne tout la surface
       // g.fillRect(0, 0, this.getWidth(), this.getHeight());//0,0//important

       //ddcm
             //   initialise(g);
               // initColoration(g);
                //initObstacle(g);
           

       
    }
//les méthodes sont supprimées
}