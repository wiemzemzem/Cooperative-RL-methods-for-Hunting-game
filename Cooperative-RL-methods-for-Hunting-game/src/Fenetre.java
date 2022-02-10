/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Wiem
 */
package myprogram;

import ChartDirector.ChartViewer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import static myprogram.MyEnv.outputsListCopy;

public class Fenetre extends JInternalFrame {

    private JButton bouton = new JButton("Go");
    private JButton bouton2 = new JButton("Stop");
    private JButton bouton3 = new JButton("Learning_Graph");
    private JPanel container = new JPanel();
    private int compteur = 0;
    private boolean animated = false;
    private boolean backX, backY;
    private int x, y;
    private Thread t;
    MyEnv envAnimation;
    int[][] vectX, vectY;
    int[] lengthVectXY;
    int nbrRobots;
    Panneau pan;
    int nbrOutputFiles, numCurrrentOutputsFile;
    

    //private Panneau pan;
    public Fenetre(MyEnv env) {

        super("Animation");
        nbrOutputFiles=1;//1 seul fichier outputs celui initial
        numCurrrentOutputsFile=0;//le fichier initial est le fichier num 0
        envAnimation = env;
        nbrRobots = env.robot.length;
        //les conteneurs des positions du robot et leurs longueurs
        vectX = new int[900][nbrRobots];//positions des robots 0 1 2 3
        vectY = new int[900][nbrRobots];
        lengthVectXY = new int[nbrRobots];// nombre des positions des robots 0 1 2 3 

        for (int i = 0; i < nbrRobots; i++) {
            lengthVectXY[i] = 0;
        }

        pan = new Panneau(env,nbrRobots,lengthVectXY,vectX,vectY);
        container.setBackground(Color.white);
        createGui2();
        container.setLayout(new BorderLayout());
        container.add(pan, BorderLayout.CENTER);
        bouton.addActionListener(new BoutonListener());
        bouton.setEnabled(true);
        bouton2.addActionListener(new Bouton2Listener());
        bouton2.setEnabled(false);
        bouton3.addActionListener(new Bouton3Listener());
        bouton3.setEnabled(true);
        JPanel south = new JPanel();
        south.add(bouton);
        south.add(bouton2);
        south.add(bouton3);
        this.setLayout(new BorderLayout());
        this.add(container, BorderLayout.CENTER);
        this.add(south, BorderLayout.NORTH);
        this.setResizable(true);

        go();
    }

    // </editor-fold>             
    private void go() {
      //  System.out.println("envAnimation.outputsList.size()=");//5000
//ArrayList <String> outputsListCopy=new ArrayList();
        while (this.animated) {
          //if(envAnimation.outputsList.size()>5000)
          //envAnimation.outputsList.clear(); 
         //    pan.repaint(); 
             if(envAnimation.outputsList.size()>500)//5000 //500
        {

                 outputsListCopy=(ArrayList) envAnimation.outputsList.clone();
           envAnimation.outputsList.clear(); 
         try {
                //file 
                 PrintWriter pw;
                //File thisFile=new File("E"+envAnimation.numExperience+"_outputs"+numCurrrentOutputsFile+".txt");
                File thisFile=new File("outputs.txt");
               
                if (thisFile.length()<500000000)//512Mo taille maximale fichier txt
               pw = new PrintWriter(new BufferedWriter(new FileWriter(thisFile,true)));
                else
                { numCurrrentOutputsFile++;
                //pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("E"+envAnimation.numExperience+"_outputs"+numCurrrentOutputsFile+".txt"))));//sans true
                pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("outputs.txt"))));//sans true
               }

             //System.out.println("taille fichier="+thisFile.length());
              
                if(outputsListCopy==null)
                    System.out.println("envAnimation.outputsList==null");
                else
                  //while (!envAnimation.outputsList.isEmpty()) 
                    //pw.println(envAnimation.outputsList.remove(0).toString());
                {  for(int i=0;i<outputsListCopy.size();i++)
                    pw.println(outputsListCopy.get(i).toString());
                 
                outputsListCopy.clear();}
                        
               // System.out.println( "suppression size outputsList="+envAnimation.outputsList.size());
                pw.close();

            } catch (IOException exception) {
                System.out.println("Erreur lors de la lecture : " + exception.getMessage());
            }
        
             }
            try {
                Thread.sleep(3000);//3000
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    

    

    class BoutonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            animated = true;
            t = new Thread(new PlayAnimation());
            t.start();
            bouton.setEnabled(false);
            bouton2.setEnabled(true);

        }
    }

    class Bouton2Listener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            animated = false;
            bouton.setEnabled(true);
            bouton2.setEnabled(false);
          // System.out.println("nombre de collisions= " +envAnimation.nbrCollision);

            // ne donne pas le nbre exact car les collisions ne sont pas comptées
            //  for (int i=0;i<nbrRobots;i++)
            //  System.out.println("nombre de positions occupés par le robot ("+i+") ="+lengthVectXY[i]);
        }
    }

    class Bouton3Listener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            //if(envAnimation.robot[0].methodAntC==1)
            //learning_graph3();
            //else
            //les 3 robots
            
            learning_graph1();
            //System.out.println("&&&&&&&&&&&& courbe moyennedes 3 robots");
            //learning_graph1_Moyenne();
            // le dernier robot à chaque épisode--> max Npas/épisode
           
//      learning_graph0();
            //courbe nbre de captures effectuées chaque 1000 itérations
           // System.out.println("&&&&&&&&&&&&& courbe aliments capturés");
            //learning_graph1000();

        }
    }

    /**
     * ****************************
     */
    public void learning_graph1() {
        ArrayList<Double>[] table = new ArrayList[envAnimation.robot.length];
        //récupérer les données des fichiers vers table
        for (int r = 0; r < envAnimation.robot.length; r++) {
            table[r] = new ArrayList();
           // System.out.println("lecture du fichier episodes_" + envAnimation.robot[r].getName());
            try {

                Scanner scanner = new Scanner(new BufferedReader(new FileReader(new File("E"+envAnimation.numExperience+"_episodes_" + envAnimation.robot[r].getName() + ".txt"))));
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
         int minSizeTable=table[0].size();
        for (int r = 1; r < envAnimation.robot.length; r++) 
          if(minSizeTable>table[r].size()) minSizeTable=table[r].size();
       // System.out.println("minSizeTabme= "+minSizeTable);
        
        //constuire les courbes de tous les robots
        //System.out.println("&&&&&&&&&&&& courbe des 3 robots");
        LearningGraph demo = new LearningGraph();
         //Create and set up the main window
        JFrame frame = new JFrame("Courbes d'apprentissage");//(demo.toString());
        frame.getContentPane().setBackground(Color.white);
        // Create the chart and put them in the content pane
        ChartViewer viewer = new ChartViewer();
        demo.createChartAllRobots("Courbes d'apprentissage",viewer, minSizeTable,table);
        frame.getContentPane().add(viewer);
        // Display the window
        frame.pack();
        frame.setVisible(true);
        
        //construire la courbe Max du système multi-robots
         //System.out.println("\n &&&&&&&&&&&&& courbe Max des robots");
        // LearningGraph demo2 = new LearningGraph();
         //Create and set up the main window
        JFrame frame2 = new JFrame("Courbes d'apprentissage");//(demo.toString());
        frame2.getContentPane().setBackground(Color.white);
        // Create the chart and put them in the content pane
        ChartViewer viewer2 = new ChartViewer();
        demo.createChartMaxRobots(envAnimation.numExperience,"Courbes d'apprentissage",viewer2, minSizeTable,table);
        frame2.getContentPane().add(viewer2);
        // Display the window
        frame2.pack();
        frame2.setVisible(true);
    }

    /**
     * *****************************************
     */
    //chaque robot possède sa propre ownEpisode, la nouvelle méthode avec attente
    // trois courbes sont construites
/*public void learning_graph1() {

     //Algorithme Ant_Colonies
     //il faut s'arreter dans le milieu de l'épisode i.e tt les robots ont parcouru le mem nbre d'épisodes
     //dataAntC sauvegarde pour chaque robot la durée de chaque épisode réalisé
     double[][] dataAntC;//= new int[envAnimation.nbrEpisode][envAnimation.robot.length];
     dataAntC=new double[envAnimation.robot.length][envAnimation.robot[0].vectAntC.size()];//[envAnimation.robot[0].ownEpisode];
     //episode: 0 1 2 .....
     String[]labelsAntC;
    
     for(int k=0;k<envAnimation.robot.length;k++)
     {   System.out.println("nombre épisode du robot ("+k+")="+envAnimation.robot[k].vectAntC.size()+", is the first?: "+envAnimation.robot[k].isTheFirst); 
        
              
     for (int i=0;i<envAnimation.robot[k].vectAntC.size();i++)
     {dataAntC[k][i]= Integer.parseInt(envAnimation.robot[k].vectAntC.elementAt(i).toString());
     System.out.println(dataAntC[k][i]);}
     //  labelsAntC[k][i]=Integer.toString(i);
     }
       
    
     //affichage
     System.out.print("les épisodes sont:");
     labelsAntC=new String[dataAntC[0].length];
     for (int i=0;i<dataAntC[0].length;i++) 
     {    
     labelsAntC[i]= Integer.toString(i);
     System.out.print(labelsAntC[i]+" * ");
                
     }
   
       
     LearningGraph demo = new LearningGraph();

     //Create and set up the main window
     JFrame frame = new JFrame("Courbes d'apprentissage");//(demo.toString());

     frame.getContentPane().setBackground(Color.white);

     // Create the chart and put them in the content pane
     ChartViewer viewer = new ChartViewer();
     demo.createChartAllRobots("Courbes d'apprentissage",viewer, 0, dataAntC,labelsAntC);
       
     // demo.createChart("Courbes d'apprentissage de Q_learning et DynaQ","Qlearning","DynaQ",viewer, 0, robot.dataQ, robot.labelsQ,robot.dataDynaQ, robot.labelsDynaQ);
     //  demo.createChart("Courbes d'apprentissage de DynaQ+, DynaQ+&&PS et DAC_PS","DynaQ+","DynaQ+&&PS","DAC_PS",viewer, 0, robot.dataDynaQ, robot.labelsDynaQ,robot.dataDynaQPS, robot.labelsDynaQPS,robot.dataDAC_PS,robot.labelsDAC_PS);

         


     frame.getContentPane().add(viewer);

     // Display the window
     frame.pack();
     frame.setVisible(true);
     }
     /*********************************************/
  //un épisode ne se termine que si tous les robots atteignent le but
    //utilisation d'une variable statique définit dans la classe MyEnv; "envAnimation.nbrEpisode"
    // une seule courbe est construit celle des max data
/*public void learning_graph0() {

     //Algorithme Ant_Colonies
     //il faut s'arreter dans le milieu de l'épisode i.e tt les robots ont parcouru le mem nbre d'épisodes
     //dataAntC sauvegarde pour chaque robot la durée de chaque épisode réalisé
     int[][] dataAntC= new int[envAnimation.robot[0].vectAntC.size()][envAnimation.robot.length];//[envAnimation.nbrEpisode][envAnimation.robot.length]
     //episode: 0 1 2 .....
     String[]labelsAntC=new String[envAnimation.robot[0].vectAntC.size()];
     double[] dataAntCMax= new double[envAnimation.robot[0].vectAntC.size()];//[envAnimation.nbrEpisode];
    
     for(int k=0;k<dataAntC.length;k++)
     {   for (int i=0;i<envAnimation.robot.length;i++)
     dataAntC[k][i]= Integer.parseInt(envAnimation.robot[i].vectAntC.elementAt(k).toString());
     
     labelsAntC[k]= Integer.toString(k); 
     }
    
     //afficher les valeurs
     int max;
     System.out.println("valeurs robots:");//+envAnimation.robot[i].getName());
     for(int k=0;k<dataAntC.length;k++)
     {    
     //à chaque épisode extraire la dataAntC maximale
     max=0; 
     for (int i=0;i<envAnimation.robot.length;i++) 
     {   System.out.print(dataAntC[k][i]+ "  *  "); 
     if( dataAntC[k][i]>max)
     max=dataAntC[k][i];
     }
     dataAntCMax[k]=max;
     System.out.print("max --> "+dataAntCMax[k]);
        
     System.out.print('\n');
     }  
     System.out.println("Les épisodes: "+dataAntC.length);//envAnimation.nbrEpisode);
     for(int k=0;k<dataAntC.length;k++)
     System.out.print(labelsAntC[k]+ "  *  ");
     /////////////////
    
     LearningGraph demo = new LearningGraph();

     //Create and set up the main window
     JFrame frame = new JFrame("Courbes d'apprentissage");//(demo.toString());

     frame.getContentPane().setBackground(Color.white);

     // Create the chart and put them in the content pane
     ChartViewer viewer = new ChartViewer();
     demo.createChart("Courbes d'apprentissage","Système de 3 robots",viewer, 0, dataAntCMax, labelsAntC);
       
     // demo.createChart("Courbes d'apprentissage de Q_learning et DynaQ","Qlearning","DynaQ",viewer, 0, robot.dataQ, robot.labelsQ,robot.dataDynaQ, robot.labelsDynaQ);
     //  demo.createChart("Courbes d'apprentissage de DynaQ+, DynaQ+&&PS et DAC_PS","DynaQ+","DynaQ+&&PS","DAC_PS",viewer, 0, robot.dataDynaQ, robot.labelsDynaQ,robot.dataDynaQPS, robot.labelsDynaQPS,robot.dataDAC_PS,robot.labelsDAC_PS);

         


     frame.getContentPane().add(viewer);

     // Display the window
     frame.pack();
     frame.setVisible(true);
     }*/
    /**
     * **************************************
     */
      //courbe nbre de captures effectuées chaque 1000 itérations
 /* public void learning_graph1000() {

     //Algorithme Ant_Colonies
     //il faut s'arreter dans le milieu de l'épisode i.e tt les robots ont parcouru le mem nbre d'épisodes
     //dataAntC sauvegarde pour chaque robot la durée de chaque épisode réalisé
     double[] data= new double[envAnimation.robot[0].vectNbrTasks.size()+1];
     //episode: 0 1 2 .....
     String[]labelsAntC=new String[data.length];
    
     System.out.println("axe Y: "+data.length);
     data[0]=0; System.out.println(data[0]+ "  *  ");
     labelsAntC[0]=Integer.toString(0); 
     if(data.length>1)
     for(int k=0;k<envAnimation.robot[0].vectNbrTasks.size();k++)
     {  
     data[k+1]= (int) envAnimation.robot[0].vectNbrTasks.elementAt(k);
     System.out.println(data[k+1]+ "  *  ");
     labelsAntC[k+1]= Integer.toString(k+1); 
     }
    
    
     for(int f=(data.length-1);f>0;f--)
     data[f]= data[f]-data[(f-1)];
       
     System.out.println("axe X: "+data.length);
     for(int k=0;k<data.length;k++)
     System.out.print(labelsAntC[k]+ "  *  ");
     /////////////////
     System.out.println("");
     LearningGraph demo = new LearningGraph();

     //Create and set up the main window
     JFrame frame = new JFrame("Courbes d'apprentissage");//(demo.toString());

     frame.getContentPane().setBackground(Color.white);

     // Create the chart and put them in the content pane
     ChartViewer viewer = new ChartViewer();
     demo.createChart1000("Courbes d'apprentissage","Aliments capturés chaque 1000 itérations",viewer, 0, data, labelsAntC);
       
     // demo.createChart("Courbes d'apprentissage de Q_learning et DynaQ","Qlearning","DynaQ",viewer, 0, robot.dataQ, robot.labelsQ,robot.dataDynaQ, robot.labelsDynaQ);
     //  demo.createChart("Courbes d'apprentissage de DynaQ+, DynaQ+&&PS et DAC_PS","DynaQ+","DynaQ+&&PS","DAC_PS",viewer, 0, robot.dataDynaQ, robot.labelsDynaQ,robot.dataDynaQPS, robot.labelsDynaQPS,robot.dataDAC_PS,robot.labelsDAC_PS);

         


     frame.getContentPane().add(viewer);

     // Display the window
     frame.pack();
     frame.setVisible(true);
     }*/
    /**
     * *****************************
     */
    /* public void learning_graph3() {

     //Algorithme Ant_Colonies
     //il faut s'arreter dans le milieu de l'épisode i.e tt les robots ont parcouru le mem nbre d'épisodes
     //dataAntC sauvegarde pour chaque robot la durée de chaque épisode réalisé
     double[][] dataAntC;//= new int[envAnimation.nbrEpisode][envAnimation.robot.length];
     dataAntC=new double[envAnimation.robot.length][6]; // 1 val pour chaque expérience 
     //episode: 0 1 2 .....
     String[]labelsAntC;
    
     for(int k=0;k<envAnimation.robot.length;k++)
     {   System.out.println("nombre épisode du robot ("+k+")="+6+", is the first?: "+envAnimation.robot[k].isTheFirst); 
        
              
     for (int i=0;i<6;i++)
     {dataAntC[k][i]= Integer.parseInt(envAnimation.robot[k].vectAntC.elementAt(i).toString());
     System.out.println(dataAntC[k][i]);}
     //  labelsAntC[k][i]=Integer.toString(i);
     }
       
    
     //affichage
     System.out.print("les épisodes sont:");
     labelsAntC=new String[dataAntC[0].length];
     for (int i=0;i<dataAntC[0].length;i++) 
     {    
     labelsAntC[i]= Integer.toString(i);
     System.out.print(labelsAntC[i]+" * ");
                
     }
   
       
     LearningGraph demo = new LearningGraph();

     //Create and set up the main window
     JFrame frame = new JFrame("Courbes d'apprentissage");//(demo.toString());

     frame.getContentPane().setBackground(Color.white);

     // Create the chart and put them in the content pane
     ChartViewer viewer = new ChartViewer();
     demo.createChartAllRobots("Courbes d'apprentissage de Ant_Colonies",viewer, 0, dataAntC,labelsAntC);
       
     // demo.createChart("Courbes d'apprentissage de Q_learning et DynaQ","Qlearning","DynaQ",viewer, 0, robot.dataQ, robot.labelsQ,robot.dataDynaQ, robot.labelsDynaQ);
     //  demo.createChart("Courbes d'apprentissage de DynaQ+, DynaQ+&&PS et DAC_PS","DynaQ+","DynaQ+&&PS","DAC_PS",viewer, 0, robot.dataDynaQ, robot.labelsDynaQ,robot.dataDynaQPS, robot.labelsDynaQPS,robot.dataDAC_PS,robot.labelsDAC_PS);

         


     frame.getContentPane().add(viewer);

     // Display the window
     frame.pack();
     frame.setVisible(true);
     }*/
    /**
     * *****************************************
     */
    //nbreEpisode, les robots s'attendent dès le 1er épisode
    // trois courbes sont construites
/*public void learning_graph() {

     //Algorithme Ant_Colonies
     //il faut s'arreter dans le milieu de l'épisode i.e tt les robots ont parcouru le mem nbre d'épisodes
     //dataAntC sauvegarde pour chaque robot la durée de chaque épisode réalisé
     double[][] dataAntC1,dataAntC2;//= new int[envAnimation.nbrEpisode][envAnimation.robot.length];
     dataAntC1=new double[envAnimation.robot.length][15];
     dataAntC2= new double[envAnimation.robot.length][15];
     //episode: 0 1 2 .....
     String[]labelsAntC;
     // Pour la première épisode
     for(int k=0;k<envAnimation.robot.length;k++)
     {   System.out.println("nombre épisode du robot ("+k+")="+(envAnimation.nbrEpisode-1)); 
        
              
     for (int i=0;i<15;i++)
     {dataAntC1[k][i]= Integer.parseInt(envAnimation.robot[k].vectAntC.elementAt(i).toString());
     System.out.println(dataAntC1[k][i]);}
            
     }
       
     //pour la deuxième méthode
     for(int k=0;k<envAnimation.robot.length;k++)
     {   System.out.println("nombre épisode du robot ("+k+")="+envAnimation.robot[k].ownEpisode); 
        
              
     for (int i=0;i<15;i++)
     {dataAntC2[k][i]= Integer.parseInt(envAnimation.robot[k].vectAntC.elementAt(15+i).toString());
     System.out.println(dataAntC2[k][i]);}
            
     }
    
     //affichage
     System.out.print("les épisodes sont:");
     labelsAntC=new String[dataAntC1[0].length];
     for (int i=0;i<dataAntC1[0].length;i++) 
     {    
     labelsAntC[i]= Integer.toString(i);
     System.out.print(labelsAntC[i]+" * ");
                
     }
   
       
     LearningGraph demo = new LearningGraph();

     //Create and set up the main window
     JFrame frame = new JFrame("Courbes d'apprentissage");//(demo.toString());

     frame.getContentPane().setBackground(Color.white);

     // Create the chart and put them in the content pane
     ChartViewer viewer = new ChartViewer();
     demo.createChartAllRobots ("Courbes d'apprentissage de Ant_Colonies",viewer, 0, dataAntC1,dataAntC2, labelsAntC);
       
     // demo.createChart("Courbes d'apprentissage de Q_learning et DynaQ","Qlearning","DynaQ",viewer, 0, robot.dataQ, robot.labelsQ,robot.dataDynaQ, robot.labelsDynaQ);
     //  demo.createChart("Courbes d'apprentissage de DynaQ+, DynaQ+&&PS et DAC_PS","DynaQ+","DynaQ+&&PS","DAC_PS",viewer, 0, robot.dataDynaQ, robot.labelsDynaQ,robot.dataDynaQPS, robot.labelsDynaQPS,robot.dataDAC_PS,robot.labelsDAC_PS);

         


     frame.getContentPane().add(viewer);

     // Display the window
     frame.pack();
     frame.setVisible(true);
     }*/
    /**
     * *************************************
     */
// moyenneEpisode[ownEpisode]+=NpasEpisode;
//courbe des 3 robots en moyenne de xxx expériences
/*public void learning_graph1_Moyenne() {

     //Algorithme Ant_Colonies
     //il faut s'arreter dans le milieu de l'épisode i.e tt les robots ont parcouru le mem nbre d'épisodes
     //dataAntC sauvegarde pour chaque robot la durée de chaque épisode réalisé
     double[][] table;//= new int[envAnimation.nbrEpisode][envAnimation.robot.length];
     table=new double[envAnimation.robot.length][envAnimation.robot[0].vectAntC.size()];//[envAnimation.robot[0].ownEpisode];
 
     //episode: 0 1 2 .....
     String[]labelsAntC;
     System.out.println("&&&&&&&&&&& moyenne");
     for(int k=0;k<envAnimation.robot.length;k++)
     {   System.out.println("nombre épisode du robot ("+k+")="+envAnimation.robot[k].vectAntC.size()+", is the first?: "+envAnimation.robot[k].isTheFirst+ " is the last?: "+envAnimation.robot[k].isTheLast);   
        
              
     for (int i=0;i<envAnimation.robot[k].vectAntC.size();i++)
     {
     table[k][i]= envAnimation.moyenneEpisode[k][i];
     System.out.println(table[k][i]);}
     //  labelsAntC[k][i]=Integer.toString(i);
     }
       
    
     //affichage
     System.out.print("les épisodes sont:");
     labelsAntC=new String[table[0].length];
     for (int i=0;i<table[0].length;i++) 
     {    
     labelsAntC[i]= Integer.toString(i);
     System.out.print(labelsAntC[i]+" * ");
                
     }
     // calcul moyenne
     //affichage
     System.out.print("la moyenne des épisodes sont:");
   
     for(int k=0;k<envAnimation.robot.length;k++)
     {   System.out.println("nombre épisode du robot ("+k+")="+envAnimation.robot[k].vectAntC.size()+", is the first?: "+envAnimation.robot[k].isTheFirst+ " is the last?: "+envAnimation.robot[k].isTheLast);  
        
              
     for (int i=0;i<envAnimation.robot[k].vectAntC.size();i++)
     {table[k][i]= table[k][i]/10;// divisé par le nbre des expériences
     System.out.println(table[k][i]);}
     //  labelsAntC[k][i]=Integer.toString(i);
     }
   
       
     LearningGraph demo = new LearningGraph();

     //Create and set up the main window
     JFrame frame = new JFrame("Courbes d'apprentissage");//(demo.toString());

     frame.getContentPane().setBackground(Color.white);

     // Create the chart and put them in the content pane
     ChartViewer viewer = new ChartViewer();
     demo.createChartAllRobots("Courbes moyenne d'apprentissage",viewer, 0, table,labelsAntC);
       
     // demo.createChart("Courbes d'apprentissage de Q_learning et DynaQ","Qlearning","DynaQ",viewer, 0, robot.dataQ, robot.labelsQ,robot.dataDynaQ, robot.labelsDynaQ);
     //  demo.createChart("Courbes d'apprentissage de DynaQ+, DynaQ+&&PS et DAC_PS","DynaQ+","DynaQ+&&PS","DAC_PS",viewer, 0, robot.dataDynaQ, robot.labelsDynaQ,robot.dataDynaQPS, robot.labelsDynaQPS,robot.dataDAC_PS,robot.labelsDAC_PS);

         


     frame.getContentPane().add(viewer);

     // Display the window
     frame.pack();
     frame.setVisible(true);
     }*/
    class PlayAnimation implements Runnable {

        public void run() {
           go();
            
           
        }}

    private void createGui2() {
        container = new JPanel();
        container.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Maze", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(204, 204, 204))); // NOI18N
        container.setPreferredSize(new java.awt.Dimension(560, 560));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(container);
        container.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 588, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 573, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(130, Short.MAX_VALUE))
        );

        pack();
    }
}
