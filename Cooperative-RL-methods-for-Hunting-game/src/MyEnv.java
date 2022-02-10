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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.vecmath.Color3f;
import simbad.sim.*;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class MyEnv extends EnvironmentDescription {
    /*CherryAgent[] obstacleBlock=new CherryAgent[65];
     CherryAgent[] obstacleBlock2=new CherryAgent[50];
    
     CherryAgent[] obstacleX=new CherryAgent[30];
     CherryAgent[] obstacleZ=new CherryAgent[30];
     CherryAgent[] obstacleXZ=new CherryAgent[50];
     CherryAgent[] clusteredGoals=new CherryAgent[4];*/

    MersenneTwister TM1 = new MersenneTwister(), TM2 = new MersenneTwister();
    MyDiscretRobot[] robot;
    public CherryAgent but1;
    public double but1X, but1Z;

    //pour les courbes d'apprentissage
    public static int numExperience;
    public static boolean nouvelExperience = false;

    public static ArrayList<String> outputsList, outputsListCopy;//=new ArrayList();;
    //nombre de penalités
    //public static int nbrCollision=0;

    public MyEnv(MyDiscretRobot[] robot, CherryAgent but1, CherryAgent but2) {
        this.robot = robot;
        this.but1 = but1;

    }

    public void setWorldSize(float f) {
        super.setWorldSize(f);
    }


    /* try {
     //initiliser files 
                
     PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("outputs"+0+".txt"))));//true si on ne veut pas le vider
                
     pw.println("&&&&&&&*****$$$$$$$********£££££££******nouvelle expérience********************");
     pw.close();
     //pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("capturedElements_" + name + ".txt"))));
     //pw.close();

     } catch (IOException exception) {
     System.out.println("Erreur lors de la lecture : " + exception.getMessage());
     }*/
    public MyEnv() {
        numExperience = 0;
        try {
                //initiliser files 

          //  PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("E" + numExperience + "_outputs" + 0 + ".txt"))));//true si on ne veut pas le vider

 PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("outputs.txt"))));//true si on ne veut pas le vider
            pw.println("&&&&&&&*****$$$$$$$********£££££££******nouvelle expérience********************");
            pw.close();
                //pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("capturedElements_" + name + ".txt"))));
            //pw.close();

        } catch (IOException exception) {
            System.out.println("Erreur lors de la lecture : " + exception.getMessage());
        }
        outputsList = new ArrayList();
        outputsListCopy = new ArrayList();

        
        // la couleur du sol
        boxColor = ligthgray;

        double k = -14.5f, l = -15.5f;
        while (k < 15.5f) {
            while (l < 14.5f) {
                l += 1;
                Box Boxcenter = new Box(new Vector3d(l, 0, k), new Vector3f(1, 0.005f, 1), this);
                add(Boxcenter);// Boxcenter.setCanBeTraversed(false);
                //if(k==13.5f && l==13.5f) 
                Box Boxcenter2 = new Box(new Vector3d(l + 1, 0, k + 1), new Vector3f(1, 0.005f, 1), this);
                add(Boxcenter2);
                l += 1;

            }
            l = -15.5f;
            k += 2;

        }
        //le nid
        boxColor = new Color3f(0.6f, 0.5f, .3f);
        Box Boxcenter2 = new Box(new Vector3d(14.5f, 0, -7.5f), new Vector3f(1, 0.005f, 1), this);
        add(Boxcenter2);

        // la couleur de l'atmosphère
        backgroundColor = new Color3f(white);

        // les murs
        boxColor = new Color3f(0.6f, 0.9f, 0.9f);

        //Box box1=new Box(new Vector3d(0,0,8.5f),new Vector3f(30,1.4f,1),this);
        Box box1 = new Box(new Vector3d(0, 0, -15.5f), new Vector3f(30, 0.5f, 1), this);
        add(box1);
        box1.setCanBeTraversed(true);
        Box box2 = new Box(new Vector3d(15.5f, 0, 0), new Vector3f(1, 0.5f, 32), this);
        //Box box2=new Box(new Vector3d(-8.5f,0,0),new Vector3f(1,1.4f,32),this);
        add(box2);
        box2.setCanBeTraversed(true);
        //Box box3=new Box(new Vector3d(0,0,15.5f),new Vector3f(30,1.4f,1),this);//30*30
        Box box3 = new Box(new Vector3d(0, 0, -8.5f), new Vector3f(30, 0.5f, 1), this);
        add(box3);
        box3.setCanBeTraversed(true);
        Box box4 = new Box(new Vector3d(8.5f, 0, 0), new Vector3f(1, 0.5f, 32), this);//30*30
        //Box box4= new Box(new Vector3d(-15.5f,0,0),new Vector3f(1,0.5f,32),this);//-15.5
        // Box box4= new Box(new Vector3d(8.5f,0,0),new Vector3f(1,1.4f,32),this);
        add(box4);
        box4.setCanBeTraversed(true);

        //les obstacles QUtility
        //obs1
        /*Box box5 = new Box(new Vector3d(14, 0, -10.5f), new Vector3f(2, 0.5f, 1), this);
        add(box5);
        box5.setCanBeTraversed(true);
        Box box6 = new Box(new Vector3d(12.5, 0, -10f), new Vector3f(1, 0.5f, 2), this);
        add(box6);
        box6.setCanBeTraversed(true);
        //obs2
        Box box7 = new Box(new Vector3d(11.5, 0, -13f), new Vector3f(1, 0.5f, 2), this);
        add(box7);
        box7.setCanBeTraversed(true);
        Box box8 = new Box(new Vector3d(13, 0, -13.5f), new Vector3f(2, 0.5f, 1), this);
        add(box8);
        box8.setCanBeTraversed(true);
        // les deux obs interchangeables
        // Box box9=new Box(new Vector3d(13.5,0,-14.5f),new Vector3f(1,0.5f,1),this);
        //add(box9);box9.setCanBeTraversed(true);
        Box box10 = new Box(new Vector3d(14.5, 0, -13.5f), new Vector3f(1, 0.5f, 1), this);
        add(box10);
        box10.setCanBeTraversed(true);*/

        /* */
        /*  box6= new CherryAgent(new Vector3d(-11,0,14.5f),"source",0.7f);
         add(box6);box6.setColor(this.blue);*/
         //box6.translateTo(new Vector3d(-16,0,14.5f));
      // Les robots
        robot = new MyDiscretRobot[3];//System.out.println("robot.length="+robot.length);

            for(int i=0;i<robot.length;i++)
         {
         robot[i]=new MyDiscretRobot(i,this,new Vector3d(10.5f,0.1f,-14.5f+i),"robot"+i,TM1,TM2);add(robot[i]);robot[i].setColor(blue);
     
         }
         
        /*       robot[0]=new MyDiscretRobot(this,new Vector3d(6.5f,0.1f,-13.5f),"robotBlue",TM1,TM2,TM3,TM4);add(robot[0]);robot[0].setColor(blue);
         robot[1]=new MyDiscretRobot(this,new Vector3d(6.5f,0.1f,-13.5f),"robotPurple",TM1,TM2,TM3,TM4);add(robot[1]);robot[1].setColor(new Color3f(244,0,161));
         robot[2]=new MyDiscretRobot(this,new Vector3d(6.5f,0.1f,-13.5f),"robotGreen",TM1,TM2,TM3,TM4);add(robot[2]);robot[2].setColor(green);
         */
      /*  robot[0] = new MyDiscretRobot(this, new Vector3d(9.5f, 0.1f, -9.5f), "robotBlue", TM1, TM2);
        add(robot[0]);
        robot[0].setColor(blue);
        robot[1] = new MyDiscretRobot(this, new Vector3d(9.5f, 0.1f, -13.5f), "robotBlack", TM1, TM2);
        add(robot[1]);*/
        

        //System.out.println("hauteur"+robot[0].getHeight());
      // Les obstacles
   /*     addObstacleX(24,obstacleX,5.5f,-6.5f);//-4.5 env 10*10 -6.5 env 30*30 obs vert
         addObstacleZ(30,obstacleZ,-8.5f,-15f); //4.5f env 10*10 -8.5 env 30*30 obs horiz
         addObstacleXZ();
         */
        // Les buts
        boxColor = new Color3f(red);//2 -2
        // but1X=7.5f;but1Z=-11.5f;
        but1X = 14.5f;
        but1Z = -14.5f;
        but1 = new CherryAgent(new Vector3d(but1X, -0.3f, but1Z), "but", 0.5f);
        add(but1);//0.7f si collision avec robots

       // CherryAgent but2=new CherryAgent(new Vector3d( 8.5f, -0.3f, -8.5f), "but", 0.5f); add(but2);
     // Clustered Goals
     /*  clusteredGoals[0]= new CherryAgent(new Vector3d( 14.9f, 0, -14.5f), "but", 0.3f);add( clusteredGoals[0]);
         clusteredGoals[1]= new CherryAgent(new Vector3d( 14.1f, 0, -14.5f), "but", 0.3f);add( clusteredGoals[1]);
         clusteredGoals[2]= new CherryAgent(new Vector3d( 14.5f, 0, -14.9f), "but", 0.3f);add( clusteredGoals[2]);
         clusteredGoals[3]= new CherryAgent(new Vector3d( 14.5f, 0, -14.1f), "but", 0.3f);add( clusteredGoals[3]);
        
      
         */
        //obstacle à ajouter pour env 10*10
        // addObstacleZ(14,obstacleBlock,30f,-12.5f);
        //obstacle à ajouter pour env 30*30
        //the block
        //addObstacleZ(63,obstacleBlock,7.5f,-12.5f);
        //the L obstcle
        // addObstacleZ(51,obstacleBlock,7.5f,-7.5f);
        //  addObstacleX(50,obstacleBlock2,-12.5f,-7.5f);
    }

   /* public void addObstacleX(int l, CherryAgent[] obstacleX, double x, double z) {
        for (int i = 0; i < l; i++) {
            obstacleX[i] = new CherryAgent(new Vector3d(x, 0, z), "Obstacle", 0.5f);
            add(obstacleX[i]);
            obstacleX[i].setCanBeTraversed(true);
            obstacleX[i].setColor(new Color3f(0.6f, 0.9f, 0.9f));
            x += 0.4;

        }
    }

    public void addObstacleZ(int l, CherryAgent[] obstacleZ, double x, double z) {
        for (int i = 0; i < l; i++) {
            obstacleZ[i] = new CherryAgent(new Vector3d(x, 0, z), "Obstacle", 0.5f);
            add(obstacleZ[i]);
            obstacleZ[i].setCanBeTraversed(true);
            obstacleZ[i].setColor(new Color3f(0.6f, 0.9f, 0.9f));
            z += 0.4;

        }
    }

    public void addObstacleXZ() {
        addObstacleZ(11, obstacleXZ, -4.5f, 4.5f);
        addObstacleZ(11, obstacleXZ, 1.5f, 4.5f);

        addObstacleX(15, obstacleXZ, -4.5f, 4.5f);
        addObstacleX(15, obstacleXZ, -4.5f, 8.5f);

    }*/
}
