/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myprogram;

import ChartDirector.ChartViewer;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JFrame;
import javax.vecmath.Vector3d;
import simbad.sim.Agent;

/**
 *
 * @author Wiem
 */
public class MyDiscretRobot extends Agent {

    Boolean stoprobot;
    int state, action, orientation, ownEpisode;
    double maxIteration = 1000;
    final static int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3, NoMOVE = 4;
    MyLearningMethod renfMe;
    double startX, startZ, vx, vz, ancX, ancZ;
    MersenneTwister rnd1, rnd2, rnd3, rnd4;// rndexploration = new MersenneTwister();
    MyEnv myenv;
    public int NpasEpisode;//Nombre de déplacements(état discret-->état discret)
    //int nEpisode = 400, nExperience = 20, ChangEpisode = 200;
    int nEpisode = 200, nExperience = 1;// ChangEpisode = 50;
    //int otherRobot,
    int[] orientationOther, actionOther;
    double[] vxOther, vzOther;
    int numThisRobot;
    int tailleSystem;

    //les méthodes
    public MyDiscretRobot(int numRobot,MyEnv env, Vector3d position, String name, MersenneTwister MT1, MersenneTwister MT2) {
        super(position, name);
        numThisRobot=numRobot;
        rnd1 = MT1;
        rnd2 = MT2;
        
        orientationOther=new int[numRobot];
        actionOther=new int[numRobot];
        vxOther=new double[numRobot];
        vzOther=new double[numRobot];

        myenv = env;

        startX = position.x;
        startZ = position.z;

        initMethod();

        System.out.println("new Mymethod");
        renfMe = new MyLearningMethod(myenv.robot.length-1, maxIteration);

        //renfMe.affich_Table_P();
        // renfMe.affich_Table_Q();
        //renfMe.update_Table_P(990,renfMe.compute_indice_PQ(-5,-5,5,5), 4);//(NpasEpisode,5, 4);
        //int action=renfMe.ActionSelection(renfMe.compute_indice_PQ(-5,-5,5,5), true,rnd1,0);
        //  renfMe.compute_pos_R1R2(   renfMe.compute_indice_PQ(1,-2,0,-5));
        //renfMe.compute_pos_R1R2(renfMe.nextState_PQ(renfMe.compute_indice_PQ(1,-2,0,-5), RIGHT, LEFT));
    }

    public void initMethod() {

        /// System.out.println("x,z= "+startX+","+startZ);
        stoprobot = false;

        NpasEpisode = 0;

        state = 0;
        action = 0;
        orientation = 0;

        ownEpisode = 0;

        vx = this.startX;
        vz = this.startZ;
        ancX = vx;
        ancZ = vz;

    }

    /**
     * Initialize Agent's Behavior
     */
    public void initBehavior() {
     /* **************  if (this == myenv.robot[0]) {
            otherRobot = 1;
        } else {
            otherRobot = 0;
        }*/
         
    }

    /**
     * ********************************
     */
    boolean obstacleDetected(double x, double z) {
        boolean trouve = false;
        //test this robot hits the goal
        if ((x == myenv.but1X) && (z == myenv.but1Z))//||((myenv.robot[otherRobot].vx==myenv.but1X)&&(myenv.robot[otherRobot].vz==myenv.but1Z)))
        {
            trouve = true;
        }
        // test obs ou mur
        if (Math.abs(x) == 15.5f || Math.abs(z) == 15.5f || Math.abs(x) == 8.5f || Math.abs(z) == 8.5f || trouve == true) {
            // if (Math.abs(x) == 15.5f || Math.abs(z) == 15.5f || trouve == true) {
            return true;
        } else {
            return false;
        }

    }

    public void performBehavior1() { 
        System.out.println("*****************************"+this.getName()+"*************************************");
       
        renfMe.init_Table_P1(numThisRobot);
        renfMe.affich_Ligne_Table_P(0);
        renfMe.affich_Ligne_Table_Q(0);
          //renfMe.ValueAsA0_episode_Treshold_Is_Depassed(new int[] {0,0},myenv);
       int a=renfMe.ActionSelection(new int[] {0,0}, true, rnd1,3, myenv);
       System.out.println("action réel="+a);
   
      //  myenv.outputsList.add("*****************************"+this.getName()+"*************************************");
        //MyMethodFSKDBehavior();
      
    
    }
     public void performBehavior() {
         if (myenv.outputsList.size() > 50000) {
         myenv.outputsList.clear();
         }

    
        if (myenv.numExperience < nExperience) {
            myenv.outputsList.add("*******************new step: " + name);
            myenv.outputsList.add("**********************last expérience");
            myenv.outputsList.add("expérience: " + myenv.numExperience);
            myenv.outputsList.add("episode: " + ownEpisode);
            // if (myenv.robot[0].ownEpisode == nEpisode) {
            
                
            if (myenv.nouvelExperience == true) {
                myenv.nouvelExperience = false;
                learning_graph1();
                myenv.numExperience++;
                System.out.println("num expérience done=" + myenv.numExperience);
                //initialiser les autres robot ici
                for (int i = 0; i < myenv.robot.length; i++) {    // System.out.println("!!!!!!!!!!!!!!!nouvelle expérience");

                    myenv.robot[i].initMethod();
                    myenv.robot[i].moveToPosition(myenv.robot[i].startX, myenv.robot[i].startZ);
                    myenv.robot[i].renfMe.init_Table_P();
                    myenv.robot[i].renfMe.init_Table_Q();
                    myenv.robot[i].state = 0;
                    myenv.robot[i].ownEpisode = 0;

                }
            }
            //System.out.println("**************state="+state+"      ownEpisode="+ownEpisode);
            //System.out.println("avant behavior: vx="+vx+"   vz="+vz+"   ancvx="+ancX+"   ancZ="+ancZ+"     but=("+myenv.but1X+","+myenv.but1Z);
            MyMethodFSKDBehavior();
            //System.out.println("après behavior: vx="+vx+"   vz="+vz+"   ancvx="+ancX+"   ancZ="+ancZ+"     but=("+myenv.but1X+","+myenv.but1Z);      
        } else if (myenv.numExperience == nExperience) {
            learning_graphFinal();
            myenv.numExperience++;

        } else {
            this.setRotationalVelocity(0);
            this.setTranslationalVelocity(0);
        }

    }

    public void learning_graph1() {
        ArrayList<Double>[] table = new ArrayList[myenv.robot.length];
        //récupérer les données des fichiers vers table
        for (int r = 0; r < myenv.robot.length; r++) {
            table[r] = new ArrayList();
            // System.out.println("lecture du fichier episodes_" + envAnimation.robot[r].getName());
            try {

                Scanner scanner = new Scanner(new BufferedReader(new FileReader(new File("E" + myenv.numExperience + "_episodes_" + myenv.robot[r].getName() + ".txt"))));
                String num;
                Double d;

                while (true) {
                    try {
                        num = scanner.next();
                        //           System.out.print(num + ",");
                        d = Double.parseDouble(num);
                        table[r].add(d);

                    } catch (NoSuchElementException exception) {
                        break;
                    }
                }

                scanner.close();

                // System.out.println("\n transmission des données au tableau:");
                // for (int i = 0; i < table[r].size(); i++) {
                //   System.out.println("épisode(" + i + ")=" + table[r].get(i));
                //}
            } catch (FileNotFoundException exception) {
                System.out.println("Le fichier n'a pas été trouvé:" + exception.getMessage());
            }
        }
        // determiner minSizeTable
        int minSizeTable = table[0].size();
        for (int r = 1; r < myenv.robot.length; r++) {
            if (minSizeTable > table[r].size()) {
                minSizeTable = table[r].size();
            }
        }
       // System.out.println("minSizeTabme= "+minSizeTable);

        //constuire les courbes de tous les robots
        //System.out.println("&&&&&&&&&&&& courbe des 3 robots");
        LearningGraph demo = new LearningGraph();

        //construire la courbe Max du système multi-robots
        //System.out.println("\n &&&&&&&&&&&&& courbe Max des robots");
        // LearningGraph demo2 = new LearningGraph();
        //Create and set up the main window
        JFrame frame2 = new JFrame("Courbes d'apprentissage");//(demo.toString());
        frame2.getContentPane().setBackground(Color.white);
        // Create the chart and put them in the content pane
        ChartViewer viewer2 = new ChartViewer();
        demo.createChartMaxRobots(myenv.numExperience, "Courbes d'apprentissage", viewer2, minSizeTable, table);
        frame2.getContentPane().add(viewer2);
        // Display the window
        frame2.pack();
        frame2.setVisible(true);

    }

    public void learning_graphFinal() {

        ArrayList<Double>[] table = new ArrayList[myenv.numExperience];
        double[] moyenne = new double[nEpisode];

        //récupérer les données des fichiers vers table
        for (int r = 0; r < myenv.numExperience; r++) {
            table[r] = new ArrayList();
            // System.out.println("lecture du fichier episodes_" + envAnimation.robot[r].getName());
            try {

                Scanner scanner = new Scanner(new BufferedReader(new FileReader(new File("E" + r + "_episodes_Système.txt"))));
                String num;
                Double d;

                while (true) {
                    try {
                        num = scanner.next();
                        //           System.out.print(num + ",");
                        d = Double.parseDouble(num);

                        table[r].add(d);

                    } catch (NoSuchElementException exception) {
                        break;
                    }
                }

                scanner.close();

                // System.out.println("\n transmission des données au tableau:");
                // for (int i = 0; i < table[r].size(); i++) {
                //   System.out.println("épisode(" + i + ")=" + table[r].get(i));
                //}
            } catch (FileNotFoundException exception) {
                System.out.println("Le fichier n'a pas été trouvé:" + exception.getMessage());
            }
        }

        //déterminer la moyenne
        for (int r = 0; r < nEpisode; r++) {
            moyenne[r] = 0;
            System.out.println("nEpisode= " + r);
            for (int i = 0; i < myenv.numExperience; i++) {
                System.out.println("    Experience= " + i + "  table=" + table[i].get(r));
                moyenne[r] += table[i].get(r);
            }
            moyenne[r] = moyenne[r] / myenv.numExperience;
            System.out.println("   --> moyenne=" + moyenne[r]);
        }

        //constuire les courbes de tous les robots
        //System.out.println("&&&&&&&&&&&& courbe des 3 robots");
        LearningGraph demo = new LearningGraph();

        //construire la courbe Max du système multi-robots
        //System.out.println("\n &&&&&&&&&&&&& courbe Max des robots");
        // LearningGraph demo2 = new LearningGraph();
        //Create and set up the main window
        JFrame frame2 = new JFrame("Courbes d'apprentissage");//(demo.toString());
        frame2.getContentPane().setBackground(Color.white);
        // Create the chart and put them in the content pane
        ChartViewer viewer2 = new ChartViewer();
        demo.createChartMoyenneEpisodes("Courbe Moyenne d'apprentissage ", viewer2, moyenne);
        frame2.getContentPane().add(viewer2);
        // Display the window
        frame2.pack();
        frame2.setVisible(true);
    }

    //Nouvelle méthode FSKD  
    public void MyMethodFSKDBehavior() {

        //état initial: juste choix de la prochaine action
        if (state == 0) {  //Refaire une nouvelle expérience
            try {
                //initiliser files 
                // PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("episodes_" + name + ".txt"))));

                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("E" + myenv.numExperience + "_episodes_" + name + ".txt"))));
                pw.close();

            } catch (IOException exception) {
                System.out.println("Erreur lors de la lecture : " + exception.getMessage());
            }

            // myenv.outputsList.add
            myenv.outputsList.add("---état initial: state= " + state + " vx=" + vx + "   vz=" + vz+"        (vx - myenv.but1X)="+ (int)(vx - myenv.but1X)+"      (vz - myenv.but1Z)="+ (int) (vz - myenv.but1Z));
            rotation();

            state++;
        } else {
            myenv.outputsList.add("---état: state= " + state + " vx=" + vx + "   vz=" + vz+"        (vx - myenv.but1X)="+ (int)(vx - myenv.but1X)+"      (vz - myenv.but1Z)="+ (int) (vz - myenv.but1Z));
            testAllRobotsDepassSeuilMax();
            if (this.stoprobot == true) {
                stopRobot();//robot en arrêt dans la cible
                myenv.outputsList.add("robot en arrêt");
            } else {
                learningFSKD();
            }

            //renfMe.compute_pos_R1R2(renfMe.compute_indice_PQ(-5,-5,5,5,myenv),myenv);//((int)(vx-myenv.but1X),(int)(vz-myenv.but1Z),(int)(myenv.robot[otherRobot].vx-myenv.but1X),(int)(myenv.robot[otherRobot].vz-myenv.but1Z),myenv);
            state++;

        }
    }

    public void testAllRobotsDepassSeuilMax() {

        if (AllRobotsDepassSeuilMax() == true) {// tous les robots au voisinage de la cible: test vx vz non pas ancX et ancZ
            myenv.outputsList.add("Tous les autres robots détectent la cible");
            // System.out.println(this.name+" : Tous les autres robots détectent la cible");
// s'il s'agit du dernier robot et que c'est un nouvel episode
            if (this==myenv.robot[myenv.robot.length-1])
               
                         if (ownEpisode == nEpisode) {
                        myenv.nouvelExperience = true;
                         myenv.outputsList.add("nouvelle experience!!!");
                    }
            //sinon si le premier robot qui va déclencher une nouvell episode
            if (this == myenv.robot[0]) //mode Ferrying && premier robot-->retour de l'ensemble
            {

                
                    //tester les robots qui dépasse le max itération
                for (int i = 0; i < myenv.robot.length; i++) {
                    if (myenv.robot[i].NpasEpisode == maxIteration) {
                        myenv.robot[i].ownEpisode++;
                        try {
                            //file 
                            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("E" + myenv.numExperience + "_episodes_" + myenv.robot[i].name + ".txt"), true)));
                            pw.println(myenv.robot[i].NpasEpisode);// écrire chaque NpasEpisode dans une ligne
                            pw.close();

                        } catch (IOException exception) {
                            // System.out.println("Erreur lors de la lecture : " + exception.getMessage());
                        }
                    }}
                
                // tester si pas nouvel experience mais nouvel épisode
                    if (ownEpisode != nEpisode) 
                    {
                    myenv.outputsList.add("robot  " + this.getName() + " déclenche une nouv épisode");
                    // System.out.println(myenv.robot[i].name+ " !!! nouvelle episode="+myenv.robot[i].ownEpisode + "   NpasEpisode="+myenv.robot[i].NpasEpisode);
for (int i = 0; i < myenv.robot.length; i++) {
                    myenv.robot[i].stoprobot = false;
                    myenv.robot[i].moveToPosition(startX, startZ);//position initial
                    myenv.robot[i].orientation = 0;
                    myenv.robot[i].NpasEpisode = 0;
                    myenv.robot[i].vx = myenv.robot[i].startX;
                    myenv.robot[i].vz = myenv.robot[i].startZ;
                    myenv.robot[i].ancX = myenv.robot[i].startX;
                    myenv.robot[i].ancZ = myenv.robot[i].startZ;
                    //   myenv.outputsList.add
                    //System.out.println("retour état initial" + " vx=" + vx + "   vz=" + vz);
                  
}
                
                 for ( int i = 0; i < myenv.robot.length; i++)
                 { myenv.outputsList.add("*****"+myenv.robot[i].getName());
                     myenv.robot[i].rotation();}
                } 

            }
        }

    }
    

    /**
     * ****************
     */
//apprentissage selon la méthode FSKD
    public void learningFSKD() {

        NpasEpisode++;
        myenv.outputsList.add("NpasEpisode=" + NpasEpisode);
        //exécuter l'action précédemment choisie
        //test de l 'accessibilité de l'état prochain;
     /*déplacer dans l'état précèdent*///   this.majPositions();//anc==updated 
        //test obstacle
        if (this.obstacleDetected(ancX, ancZ) == true) {
            myenv.outputsList.add("Collision detected dans (" + ancX + "," + ancZ + ")    --> rester dans current=(" + vx + "," + vz + ")");
            ancX = vx;
            ancZ = vz;
            //learning rec=-0.05 int indice_PQ, int as, int a0, int recompense,MyEnv myenv
            //le problème est ici indice_PQ ici vx et vz non pas encore maj, cé ancX et ancZ qui stocke la position courante
           
           
            //learning
               for(int i=0; i<numThisRobot; i++)//vx et vz sont déjà modifié
                {
                     myenv.outputsList.add( myenv.robot[i].getName() + " (" + Math.abs(Math.abs(myenv.robot[i].vx) - Math.abs(myenv.but1X)) + "," + Math.abs(Math.abs(myenv.robot[i].vz) - Math.abs(myenv.but1Z)));
                //  robot(i) a déjà exécuter l'état suivant: utilser vxOther vzOther et orientationOther
               learning( i,renfMe.compute_indice_PQ((int) (vx - myenv.but1X), (int) (vz - myenv.but1Z), (int) (vxOther[i] - myenv.but1X), (int) (vzOther[i] - myenv.but1Z), myenv), actionOther[i], orientationOther[i], -0.05);
            
               learningInverse(i,renfMe.compute_indice_PQ((int) (vxOther[i] - myenv.but1X), (int) (vzOther[i] - myenv.but1Z),(int) (vx - myenv.but1X), (int) (vz - myenv.but1Z),  myenv), actionOther[i],orientationOther[i],   -0.05);
                }
                for(int i=numThisRobot+1; i<myenv.robot.length; i++)//utilise ancX et ancZ puisque vx et vz n'ont pas encore maj
                {
                    myenv.outputsList.add( myenv.robot[i].getName() + " (" + Math.abs(Math.abs(myenv.robot[i].ancX) - Math.abs(myenv.but1X)) + "," + Math.abs(Math.abs(myenv.robot[i].ancZ) - Math.abs(myenv.but1Z)));
                learning(i-1,renfMe.compute_indice_PQ((int) (vx - myenv.but1X), (int) (vz - myenv.but1Z), (int) (myenv.robot[i].vx - myenv.but1X), (int) (myenv.robot[i].vz - myenv.but1Z), myenv), myenv.robot[i].action, myenv.robot[i].orientation, -0.05);
                
               learningInverse(i-1,renfMe.compute_indice_PQ((int) (myenv.robot[i].vx - myenv.but1X), (int) (myenv.robot[i].vz - myenv.but1Z), (int) (vx - myenv.but1X), (int) (vz - myenv.but1Z), myenv), myenv.robot[i].action, myenv.robot[i].orientation, -0.05);
                    
                }

            this.setTranslationalVelocity(0);
        } //état accessible
        else {

            myenv.outputsList.add("Safe displacement current=(" + ancX + "," + ancZ + ")");
            if (action == NoMOVE) {
                this.setTranslationalVelocity(0);
            } else {
                this.setTranslationalVelocity(20);
            }
            //test voisinage de but occupé par deux robots
           if (TestDetectedGoal()==true)
            {
                ownEpisode++;
                 myenv.outputsList.add(this.name + ":    But Detected=   " + this.getName() + ") (" + Math.abs(Math.abs(ancX) - Math.abs(myenv.but1X)) + "," + Math.abs(Math.abs(ancZ) - Math.abs(myenv.but1Z))
                        + ")     OwnEpisode=" + ownEpisode);
                 
                
                //learning
                for(int i=0; i<numThisRobot; i++)//vx et vz sont déjà modifié
                {
                     myenv.outputsList.add( myenv.robot[i].getName() + " (" + Math.abs(Math.abs(myenv.robot[i].vx) - Math.abs(myenv.but1X)) + "," + Math.abs(Math.abs(myenv.robot[i].vz) - Math.abs(myenv.but1Z)));
                //  robot(i) a déjà exécuter l'état suivant: utilser vxOther vzOther et orientationOther
               learning(i,renfMe.compute_indice_PQ((int) (vx - myenv.but1X), (int) (vz - myenv.but1Z), (int) (vxOther[i] - myenv.but1X), (int) (vzOther[i] - myenv.but1Z), myenv), actionOther[i], orientationOther[i], 1);
            
               learningInverse(i,renfMe.compute_indice_PQ((int) (vxOther[i] - myenv.but1X), (int) (vzOther[i] - myenv.but1Z),(int) (vx - myenv.but1X), (int) (vz - myenv.but1Z),  myenv), actionOther[i],orientationOther[i],   1);
                            }
                for(int i=numThisRobot+1; i<myenv.robot.length; i++)//utilise ancX et ancZ puisque vx et vz n'ont pas encore maj
                {
                    myenv.outputsList.add( myenv.robot[i].getName() + " (" + Math.abs(Math.abs(myenv.robot[i].ancX) - Math.abs(myenv.but1X)) + "," + Math.abs(Math.abs(myenv.robot[i].ancZ) - Math.abs(myenv.but1Z)));
                learning(i-1,renfMe.compute_indice_PQ((int) (vx - myenv.but1X), (int) (vz - myenv.but1Z), (int) (myenv.robot[i].vx - myenv.but1X), (int) (myenv.robot[i].vz - myenv.but1Z), myenv), myenv.robot[i].action, myenv.robot[i].orientation, 1);
                
               learningInverse(i-1,renfMe.compute_indice_PQ((int) (myenv.robot[i].vx - myenv.but1X), (int) (myenv.robot[i].vz - myenv.but1Z), (int) (vx - myenv.but1X), (int) (vz - myenv.but1Z), myenv), myenv.robot[i].action, myenv.robot[i].orientation, 1);
                    
                }
                
                

                //le robot maj la position du but
                /**
                 * nouv suppression
                 */// renfPS.posBut.caseX = ancX;
                /**
                 * 
                 */// renfPS.posBut.caseZ = ancZ;
                // System.out.println(this.name+" :position but: "+ renfPS.posBut.caseX+" , " +renfPS.posBut.caseZ+",instant de detection="+renfPS.stateButDetected);
                stoprobot = true;
                stopRobot();
                // écrire chaque valeur de NpasEpisode dans une nouvelle ligne du fichier avec sauvegardes des anciens elts du fichier
                try {
                    //file 
                    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("E" + myenv.numExperience + "_episodes_" + name + ".txt"), true)));
                    pw.println(NpasEpisode);// écrire chaque NpasEpisode dans une ligne
                    pw.close();

                } catch (IOException exception) {
                    // System.out.println("Erreur lors de la lecture : " + exception.getMessage());
                }

            }
            else {
              //learning
               for(int i=0; i<numThisRobot; i++)//vx et vz sont déjà modifié
                {
                     myenv.outputsList.add( myenv.robot[i].getName() + " (" + Math.abs(Math.abs(myenv.robot[i].vx) - Math.abs(myenv.but1X)) + "," + Math.abs(Math.abs(myenv.robot[i].vz) - Math.abs(myenv.but1Z)));
                //  robot(i) a déjà exécuter l'état suivant: utilser vxOther vzOther et orientationOther
               learning(i,renfMe.compute_indice_PQ((int) (vx - myenv.but1X), (int) (vz - myenv.but1Z), (int) (vxOther[i] - myenv.but1X), (int) (vzOther[i] - myenv.but1Z), myenv), actionOther[i], orientationOther[i], -0.05);
            
               learningInverse(i,renfMe.compute_indice_PQ((int) (vxOther[i] - myenv.but1X), (int) (vzOther[i] - myenv.but1Z),(int) (vx - myenv.but1X), (int) (vz - myenv.but1Z),  myenv), actionOther[i],orientationOther[i],   -0.05);
                }
                for(int i=numThisRobot+1; i<myenv.robot.length; i++)//utilise ancX et ancZ puisque vx et vz n'ont pas encore maj
                {
                    myenv.outputsList.add( myenv.robot[i].getName() + " (" + Math.abs(Math.abs(myenv.robot[i].ancX) - Math.abs(myenv.but1X)) + "," + Math.abs(Math.abs(myenv.robot[i].ancZ) - Math.abs(myenv.but1Z)));
                learning(i-1,renfMe.compute_indice_PQ((int) (vx - myenv.but1X), (int) (vz - myenv.but1Z), (int) (myenv.robot[i].vx - myenv.but1X), (int) (myenv.robot[i].vz - myenv.but1Z), myenv), myenv.robot[i].action, myenv.robot[i].orientation, -0.05);
                
               learningInverse(i-1,renfMe.compute_indice_PQ((int) (myenv.robot[i].vx - myenv.but1X), (int) (myenv.robot[i].vz - myenv.but1Z), (int) (vx - myenv.but1X), (int) (vz - myenv.but1Z), myenv), myenv.robot[i].action, myenv.robot[i].orientation, -0.05);
                    
                }
               
                
             }
             //maj vx vz
             vx = ancX;
             vz = ancZ;
             

        }
        if (NpasEpisode == maxIteration) {
            stoprobot = true;
            myenv.outputsList.add(name + " dépasse le seuil et s'arrete");
        }
        // choix d'une nouvelle action
        if (stoprobot == false) {
            rotation();
        }

    }
    
   boolean TestDetectedGoal()
   {if ((Math.abs(Math.abs(ancX) - Math.abs(myenv.but1X)) != 1) || (Math.abs(Math.abs(ancZ) - Math.abs(myenv.but1Z)) != 1))
                    return false;//le robot actuel est loin du but
   else //le robot actuel detecte le but
   {
       for(int i=0; i<numThisRobot; i++)
        if(//ces robots ont déjà maj leur vx et vz en fn ancX et ancZ qui prennent ensuite un nouvel état
                   (Math.abs(Math.abs(myenv.robot[i].vx) - Math.abs(myenv.but1X)) != 1) || (Math.abs(Math.abs(myenv.robot[i].vz) - Math.abs(myenv.but1Z)) != 1)
                    ||(obstacleDetected(myenv.robot[i].vx, myenv.robot[i].vz)==true))
            return false;
       
       
      
       for(int i=numThisRobot+1; i<myenv.robot.length; i++)
    if (//ces robots n'ont pas encore maj leur vx et vz
        (Math.abs(Math.abs(myenv.robot[i].ancX) - Math.abs(myenv.but1X)) != 1) || (Math.abs(Math.abs(myenv.robot[i].ancZ) - Math.abs(myenv.but1Z)) != 1)
                   ||(obstacleDetected(myenv.robot[i].ancX, myenv.robot[i].ancZ)==true)
                    )
        return false;
               
   
   }
   return true;
   
   }
    
    public void learning(int numVoisin,int iPQ, int action2, int orient2, double rec )
    {
    
    if (action*action2==16)//NoMove1 /NoMove12
    { 
    renfMe.update_Table_P( numVoisin,NpasEpisode, iPQ, 4, myenv);
    renfMe.update_Table_Q(numVoisin,iPQ, 4, 4, rec, myenv);
   
    }
    
    else if (action==4)//NoMove1
    { 
    renfMe.update_Table_P( numVoisin,NpasEpisode, iPQ, orient2, myenv);
    renfMe.update_Table_Q(numVoisin,iPQ, 4, orient2, rec, myenv);
    }
    
    else if (action2==4)//NoMove2
    {
    renfMe.update_Table_P( numVoisin,NpasEpisode, iPQ, 4, myenv);
    renfMe.update_Table_Q(numVoisin,iPQ, orientation, 4, rec, myenv);
    }
    else
    {
    renfMe.update_Table_P( numVoisin,NpasEpisode, iPQ, orient2, myenv);
    renfMe.update_Table_Q(numVoisin,iPQ, orientation, orient2, rec, myenv);
    }
                          
    }
    
    public void learningInverse(int  numVoisin,int iPQ, int action2, int orient2, double rec )
    {
    
    if (action*action2==16)//NoMove1 /NoMove12
    { 
    renfMe.update_Table_P( numVoisin,NpasEpisode, iPQ, 4, myenv);
    renfMe.update_Table_Q(numVoisin,iPQ, 4, 4, rec, myenv);
   
    }
    
    else if (action==4)//NoMove1
    { 
    renfMe.update_Table_P( numVoisin,NpasEpisode, iPQ, 4, myenv);
    renfMe.update_Table_Q(numVoisin,iPQ, orient2, 4, rec, myenv);
    }
    
    else if (action2==4)//NoMove2
    {
    renfMe.update_Table_P( numVoisin,NpasEpisode, iPQ, orientation, myenv);
    renfMe.update_Table_Q(numVoisin,iPQ, 4, orientation, rec, myenv);
    }
    else
    {
    renfMe.update_Table_P( numVoisin,NpasEpisode, iPQ, orientation, myenv);
    renfMe.update_Table_Q(numVoisin,iPQ, orient2, orientation, rec, myenv);
    }
                          
    }

    /**
     * *****************************
     */
    public void majPositions() {
        if (action != 4) //action!=NoMove
        {
            switch (orientation) {
                case UP:
                    ancX++;
                    break;
                case RIGHT:
                    ancZ--;
                    break;
                case DOWN:
                    ancX--;
                    break;
                case LEFT:
                    ancZ++;
                    break;
                //case NoMOVE NOTHING 
            }
        }
    }

//tester si tous les robots atteignent le voisinage de la cible
    boolean AllRobotsDepassSeuilMax() {

       
        for (MyDiscretRobot robot : myenv.robot) {
            //  System.out.println(robot.name+ " robot.NpasEpisode="+robot.NpasEpisode+"      robot.vx ="+robot.vx +"    robot.vz ="+robot.vz+"    robot.ownEpisode ="+robot.ownEpisode);    
            //tester reaching Goal
            // if ((robot.NpasEpisode<maxIteration)&& ((Math.abs(Math.abs(robot.vx)-Math.abs(myenv.but1X))!=0)||(Math.abs(Math.abs(robot.vz)-Math.abs(myenv.but1Z))!=0)))
            //tester reaching voisinage de Goal
            if ((robot.NpasEpisode < maxIteration) && ((Math.abs(Math.abs(robot.vx) - Math.abs(myenv.but1X)) != 1) || (Math.abs(Math.abs(robot.vz) - Math.abs(myenv.but1Z)) != 1))) {
                return false;
            }

        }
       

        return true;

    }

    /**
     *
     * /**
     * *****************************
     */
    public void stopRobot() {
        setRotationalVelocity(0);
        setTranslationalVelocity(0);
    }

    //cas statique:FSKD
    public void rotation() {
        int[] indice_PQ=new int[myenv.robot.length-1];
//myenv.outputsList.add
      // System.out.println ("****************choix d'action FSKD: "+this.name);
       
        for(int i=0; i<numThisRobot; i++)
        { //System.out.println("robots précèdents"+this.name);
            //robot actuel=robot2 --> les états de tt les robots sont maj à la même instant
           indice_PQ[i]= renfMe.compute_indice_PQ((int) (vx - myenv.but1X), (int) (vz - myenv.but1Z), (int) (myenv.robot[i].vx - myenv.but1X), (int) (myenv.robot[i].vz - myenv.but1Z), myenv);
            //sauvgarder tt de rob1 car rob1 va changer avant de maj Qrob2_actuel
            vxOther[i] = myenv.robot[i].vx;
            vzOther[i] = myenv.robot[i].vz;
            orientationOther[i] = myenv.robot[i].orientation;
            actionOther[i] = myenv.robot[i].action;
        } 

         for(int i=numThisRobot+1; i<myenv.robot.length; i++)
               {//System.out.println("robots suivants"+this.name);
            //rob1_actuel doit déterminer l'état actuel en cas de parralel, puisque dans notre cas séquentiel robi n'a pas ncore maj son état
            //calcul état suivant: utiliser ancX et ancZ de l'autre
            indice_PQ[i-1]= renfMe.compute_indice_PQ((int) (vx - myenv.but1X), (int) (vz - myenv.but1Z), (int) (myenv.robot[i].ancX - myenv.but1X), (int) (myenv.robot[i].ancZ - myenv.but1Z), myenv);
            }

        
        //if ((ownEpisode * 0.75) < nEpisode) 
         
/*if (ownEpisode < (nEpisode*0.75))
{
            
            action = renfMe.ActionSelection(indice_PQ, false, rnd1, orientation, myenv);
           // action=ActionChoiceBeforeTreshold(indice_PQ);
        } else {
            //action = renfMe.ActionSelection(indice_PQ[0], true, rnd1, orientation, myenv);
         */    action=renfMe.ActionSelection(indice_PQ, true, rnd1,orientation, myenv);
       
        //}
 
        //if(state<3)action=1; else action=0;
        switch (action) {
            case RIGHT:
                setRotationalVelocity(10 * Math.PI);
                break;
            case DOWN:
                setRotationalVelocity(20 * Math.PI);
                break;
            case LEFT:
                setRotationalVelocity(-10 * Math.PI);
                break;
            default://UP NOMOVE
                setRotationalVelocity(0);
                break;
        }

        orientation = (orientation + action) % 4;
//"other_robot=" + this.otherRobot +
        myenv.outputsList.add( "         action prochaine:" + action + "          orientation= " + orientation);
        this.majPositions();//anc==updated 
    }
    /**** choix d'actions selon la sommPQ de tous les robots********/



    /*  public void learning_graphFinal() {
     
       
     ArrayList<Double>[] table = new ArrayList[myenv.numExperience];
     double [] moyenne=new double[nEpisode]; 
        
     //récupérer les données des fichiers vers table
     for (int r = 0; r < myenv.numExperience; r++) {
     table[r]=new ArrayList();
     // System.out.println("lecture du fichier episodes_" + envAnimation.robot[r].getName());
     try {

     Scanner scanner = new Scanner(new BufferedReader(new FileReader(new File("E"+r+"_episodes_Système.txt"))));
     String num;
     Double d;

     while (true) {
     try {
     num = scanner.next();
     //           System.out.print(num + ",");
     d = Double.parseDouble(num);
                        
     table[r].add(d);
                         

     } catch (NoSuchElementException exception) {
     break;
     }
     }

     scanner.close();

     // System.out.println("\n transmission des données au tableau:");
     // for (int i = 0; i < table[r].size(); i++) {
     //   System.out.println("épisode(" + i + ")=" + table[r].get(i));
     //}
     } catch (FileNotFoundException exception) {
     System.out.println("Le fichier n'a pas été trouvé:" + exception.getMessage());
     }
     }
    
     //déterminer la moyenne
     for (int r = 0; r < nEpisode; r++) 
     {  moyenne[r]= 0;
     System.out.println("nEpisode= "+r);
     for (int i = 0; i < myenv.numExperience; i++) 
     {   System.out.println("    Experience= "+i+"  table="+table[i].get(r));
     moyenne[r]+=table[i].get(r);}
     moyenne[r]= moyenne[r]/myenv.numExperience;
     System.out.println("   --> moyenne="+moyenne[r]);
     }
        
     //constuire les courbes de tous les robots
     //System.out.println("&&&&&&&&&&&& courbe des 3 robots");
     LearningGraph demo = new LearningGraph();
                
     //construire la courbe Max du système multi-robots
     //System.out.println("\n &&&&&&&&&&&&& courbe Max des robots");
     // LearningGraph demo2 = new LearningGraph();
     //Create and set up the main window
     JFrame frame2 = new JFrame("Courbes d'apprentissage");//(demo.toString());
     frame2.getContentPane().setBackground(Color.white);
     // Create the chart and put them in the content pane
     ChartViewer viewer2 = new ChartViewer();
     demo.createChartMoyenneEpisodes("Courbe Moyenne d'apprentissage ",viewer2,moyenne);
     frame2.getContentPane().add(viewer2);
     // Display the window
     frame2.pack();
     frame2.setVisible(true);
     }*/
    /**
     * *******************************
     */
    // les robots apprennent selon PS, chaque robot attend les autres robots aux but puis retourner ensemble suivant le chemin du robot le plus court 
    public void PS_Behavior() {

        //état initial: juste choix de la prochaine action
        if (state == 0) {  //Refaire une nouvelle expérience
            try {
                //initiliser files 
                // PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("episodes_" + name + ".txt"))));

                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("E" + myenv.numExperience + "_episodes_" + name + ".txt"))));
                pw.close();

            } catch (IOException exception) {
                System.out.println("Erreur lors de la lecture : " + exception.getMessage());
            }

            // myenv.outputsList.add
            myenv.outputsList.add("---état initial: state= " + state + " vx=" + vx + "   vz=" + vz);
            rotation();
            state++;
        } else {

            myenv.outputsList.add("state= " + state + " vx=" + vx + "   vz=" + vz);
            ////////////////

            // if ((this.AllRobotsReachedGoal() == true)|| 
            if (AllRobotsDepassSeuilMax() == true) {// tous les robots detectent la cible
                myenv.outputsList.add("Tous les autres robots détectent la cible");
                // System.out.println(this.name+" : Tous les autres robots détectent la cible");

                if (this == myenv.robot[0]) //mode Ferrying && premier robot-->retour de l'ensemble
                {

                    myenv.outputsList.add("robot  " + this.getName() + " déclenche une nouv épisode");

                    for (int i = 0; i < myenv.robot.length; i++) {
                        if (myenv.robot[i].NpasEpisode == maxIteration) {
                            myenv.robot[i].ownEpisode++;
                            try {
                                //file 
                                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("E" + myenv.numExperience + "_episodes_" + myenv.robot[i].name + ".txt"), true)));
                                pw.println(myenv.robot[i].NpasEpisode);// écrire chaque NpasEpisode dans une ligne
                                pw.close();

                            } catch (IOException exception) {
                                // System.out.println("Erreur lors de la lecture : " + exception.getMessage());
                            }
                        }
                        if (ownEpisode == nEpisode) {
                            myenv.nouvelExperience = true;
                            System.out.println("nouvelle experience!!!");
                        }
                        // System.out.println(myenv.robot[i].name+ " !!! nouvelle episode="+myenv.robot[i].ownEpisode + "   NpasEpisode="+myenv.robot[i].NpasEpisode);

                        myenv.robot[i].stoprobot = false;
                        myenv.robot[i].moveToPosition(startX, startZ);//position initial
                        myenv.robot[i].orientation = 0;
                        myenv.robot[i].NpasEpisode = 0;
                        myenv.robot[i].vx = startX;
                        myenv.robot[i].vz = startZ;
                        myenv.robot[i].ancX = startX;
                        myenv.robot[i].ancZ = startZ;
                        //   myenv.outputsList.add
                        //System.out.println("retour état initial" + " vx=" + vx + "   vz=" + vz);
                        myenv.robot[i].rotation();

                    }

                }
            }

            ////////////////
            if (this.stoprobot == true) {
                stopRobot();//robot en arrêt dans la cible
                myenv.outputsList.add("robot en arrêt");
            } else {//mode foraging
                NpasEpisode++;
                myenv.outputsList.add("NpasEpisode=" + NpasEpisode);
                //exécuter l'action précédemment choisie
                //test de l 'accessibilité de l'état prochain;
                this.majPositions();//anc==updated 
                //test obstacle
                if (this.obstacleDetected(ancX, ancZ) == true) {
                    ancX = vx;
                    ancZ = vz;
                    myenv.outputsList.add("Collision detected current=(" + vx + "," + vz + ")");
                    /**///earning(-100);//-90
                    this.setTranslationalVelocity(0);
                } //état accessible
                else {
                    myenv.outputsList.add("Safe displacement current=(" + ancX + "," + ancZ + ")");
                    if (action == NoMOVE) {
                        this.setTranslationalVelocity(0);
                    } else {
                        this.setTranslationalVelocity(20);
                    }
                    //test but
                    if ((ancX == myenv.but1X) && (ancZ == myenv.but1Z)) {
                        myenv.outputsList.add(this.name + ":    But Detected=(" + ancX + "," + ancZ + ") OwnEpisode=" + ownEpisode);
                        ownEpisode++;
                        /**///learning(100);//18000
                        //le robot maj la position du but
                        /**///renfPS.posBut.caseX = ancX;
                        /**///renfPS.posBut.caseZ = ancZ;
                        // System.out.println(this.name+" :position but: "+ renfPS.posBut.caseX+" , " +renfPS.posBut.caseZ+",instant de detection="+renfPS.stateButDetected);
                        stoprobot = true;
                        stopRobot();
                        // écrire chaque valeur de NpasEpisode dans une nouvelle ligne du fichier avec sauvegardes des anciens elts du fichier
                        try {
                            //file 
                            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("E" + myenv.numExperience + "_episodes_" + name + ".txt"), true)));
                            pw.println(NpasEpisode);// écrire chaque NpasEpisode dans une ligne
                            pw.close();

                        } catch (IOException exception) {
                            // System.out.println("Erreur lors de la lecture : " + exception.getMessage());
                        }

                    } else {
                        /**///learning(0);
                    }
                    //maj vx vz
                    vx = ancX;
                    vz = ancZ;

                }
                if (NpasEpisode == maxIteration) {
                    stoprobot = true;
                    System.out.println(name + " dépasse le seuil et s'arrete");
                }
                // choix d'une nouvelle action
                if (stoprobot == false) {
                    rotation();
                }
            }
            state++;

        }

    }

}
