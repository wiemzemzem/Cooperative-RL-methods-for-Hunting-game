/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myprogram;

import static java.lang.Math.pow;
import java.util.ArrayList;

/*java -jar "C:\Users\Wiem\Google Drive\thèse wiem\travail wiem octobre 2013\Java Program\à partir de 13 mars\MyProgram\dist\MyProgram.jar"
*/


/**
 *
 * @author Wiem
 */
public class MyProg {

    public static void main(String args[]) {
       
        
        // java.awt.EventQueue.invokeLater(new Runnable() {
        //   public void run() {
        /*
         normalisée = (originale - MIN) * (max - min) / (MAX - MIN) + min
 
         [MIN,MAX] : interval d'origine
         [min,max] : interval cible
         originale : valeur dans l'interval d'origine
         normalisée : valeur normalisée dans l'interval cible
        
         *//*
double[] actionProb = new double[4];
        double[] number = new double[4];
       for (int k = 0; k < 200; k++) {
            System.out.println("*******************************");
            int ownEpisode = k;
            //number[0]=5.846006549323628E-6;number[1]=2.394524282602959E-6;number[2]= 2.394524282602959E-6;number[3]=3.7414441915671226E-6;
            number[0] = 0      ;
            number[1] =180*0.9*0.9*0.9*0.9    ;
            number[2] = 0      ;
            number[3] = 0    ;//B
           
           /* System.out.println("Avant normalization: " + number[0] + " * " + number[1] + " * " + number[2] + " * " + number[3]);
           
          double max =  number[0], min = number[2];
            for (int i = 0; i < 4; i++) {
                number[i] = (number[i] - min) * (1 + 1) / (max - min) - 1;
            }
          System.out.println("Après normalization: " + number[0] + " * " + number[1] + " * " + number[2] + " * " + number[3]);
         */  
            /* double prob = 0, somActionProb = 0, T = 30.0/(ownEpisode+1);//Math.exp((30.0- ownEpisode)/10);//40.0/(Math.exp(ownEpisode)+1);///prob= somme exp(Q/T); somActionProb=somme des actionProb; T=T0/(b+1)
            System.out.println("ownEpisode=" + ownEpisode + " T=" + T);
            // calcul somme exp(Q/T)=prob
             for (int i = 0; i < 4; i++) {
                prob += Math.exp(number[i] / T);//somme
            }
            System.out.println("Somme prob=" + prob);
            // calcul de chaque actionProb
            for (int i = 0; i < 4; i++) {
               // actionProb[i] = Math.exp(number[i] / T) / prob;
                actionProb[i] = 1 / (Math.exp((number[0]-number[i])/ T)+Math.exp((number[1]-number[i]) / T)+Math.exp((number[2]-number[i]) / T)+ Math.exp((number[3]-number[i]) / T));
                System.out.println("number[" + i + "]=" + number[i] + "    *   " + "actionProb[" + i + "]= " + actionProb[i]);
                
            }
            System.out.println("somActionProb= " + somActionProb);
        }*/
 /*     double[] Qsa=new double[4];  
      double[] Utility=new double[4];  
      double somQ;
      int NpasEpisode=1000;
      MersenneTwister rnd=new MersenneTwister();
      Qsa[0]=100;  Qsa[1]=-100;  Qsa[2]=0;  Qsa[3]=0; 
        for (int j = 0; j < 4; j++)
                 {   somQ=Math.exp(Qsa[0]-Qsa[j])+Math.exp(Qsa[1]-Qsa[j])+Math.exp(Qsa[2]-Qsa[j])+Math.exp(Qsa[3]-Qsa[j]);
                
                 Utility[j]=0.4*Math.exp(-NpasEpisode/30)+0.6/somQ;
                 System.out.println("Qsa="+Qsa[j]+" *   "+"somQ="+somQ+" *   "+" Utility="+Utility[j]+" *   ar="+rnd.nextInt(4));
                 
                 }
        for (int j = 0; j < 4; j++)
                 {   somQ=Math.exp(Qsa[0])+Math.exp(Qsa[1])+Math.exp(Qsa[2])+Math.exp(Qsa[3]);
                
                 Utility[j]=0.4*Math.exp(-NpasEpisode/30)+0.6* Math.exp(Qsa[j])/somQ;
                 System.out.println("Qsa="+Qsa[j]+" *   "+"somQ="+somQ+" *   "+" Utility="+Utility[j]);
                 
         
        }
      */
        
        /*int ownEpisode=350,nEpisode=400;
        if (ownEpisode < (nEpisode*0.75))
             System.out.println("inférieur= "+(nEpisode*0.75));
        else
             System.out.println("supérieur= "+(nEpisode*0.75));
         ArrayList <Integer> [] intListP = new ArrayList[4];
        for (int i=0;i<4;i++)
        intListP[i]=new ArrayList<Integer>();
        for (int i=1;i<4;i++)
        {intListP[i-1].add(i*4);
        intListP[i-1].add(i*9);
        intListP[i-1].add(i*99);
        System.out.println(i+"="+intListP[i-1].get(0));
        System.out.println(i+"="+intListP[i-1].get(1));
        System.out.println(i+"="+intListP[i-1].get(2));}
        intListP[0].remove(0);intListP[0].remove(1);
         intListP[3].add(6);
          intListP[3].add(5);
          
        
        System.out.println("*********Affichage de intListP.length="+intListP.length);
        for (int i=0;i<intListP.length;i++)
        {  for (int k=0;k<intListP[i].size();k++)
                  System.out.print(intListP[i].get(k)+"  *  ");
        
         System.out.println();
        }
        //calcul actions jointes
        int NbActJointes=1;
       for (int i=0;i<intListP.length;i++) 
           NbActJointes*=intListP[i].size();
        System.out.println("********* NbActJointes= "+ NbActJointes);
        int[][] ActionsJointes=new int[NbActJointes][intListP.length];
        //remplissage tableau des actions jointes
        /*
        a1 b1 c1
        a1 b1 c2 
        a1 b1 c3
        a1 b2 c1
        a1 b2 c2 
        a1 b2 c3
        ....
        *//*
        //le dernier robot intListP.length-1
          for (int action=0;action<intListP[intListP.length-1].size();action++) 
        //répéter la même séquence d'actions différentes pour NbActJointes/NbActionsDernierRobot
        for(int rep=0;rep<NbActJointes/intListP[intListP.length-1].size();rep++)
            ActionsJointes[action+rep*intListP[intListP.length-1].size()][intListP.length-1]=intListP[intListP.length-1].get(action);

          //les autres robots         
        int robo;
        int NbSuccessionSameActions=1;//combien la même action se répète de fois successivement dans la même colonne 
       
        //pour chaque voisin intListP.length=nbVoisin 
       
        for (int r=1;r<intListP.length;r++)
        {   robo=intListP.length-r-1;
            NbSuccessionSameActions*=intListP[robo+1].size();
        System.out.println("NbSuccessionSameActions*="+NbSuccessionSameActions);
        for (int rep1=0;rep1<NbActJointes;rep1+=(NbSuccessionSameActions*intListP[robo].size()))
            
        for (int action=0;action<intListP[robo].size();action++)        
        for (int rep2=0;rep2<NbSuccessionSameActions;rep2++) 
             ActionsJointes[action*NbSuccessionSameActions+rep2+rep1][robo]=intListP[robo].get(action);
          
        
       
        }
       
        //affichage tableau des actions jointes
        
        System.out.println("********* Affichage Tableau des Actions Jointes  ********");
       for (int a_jointe=0;a_jointe<ActionsJointes.length;a_jointe++) 
        { System.out.print("n"+a_jointe+"  *  ");
            for (int rob=0;rob<ActionsJointes[0].length;rob++)
                  System.out.print(ActionsJointes[a_jointe][rob]+"  *  ");
        
         System.out.println();
        }
        */
        
           MyEnv  env= new MyEnv();
         //changer la taille du world pour supporter 30 unités
         env.setWorldSize(30);
         MySimbad frame = new MySimbad(env,env,false);   
         frame.createFenetre(env);
 
       /*  ArrayList<Experience> intList = new ArrayList();
         int a=0;
         double x=10.5,z=0.5;
         for (int i=0; i<10; i++)
         {a++;z++;x++;
             if(intList.size()<4)
             intList.add(new Experience(x,z,a));
             else
             {
             intList.remove(0);
             intList.add(new Experience(x,z,a));
             
             }
             System.out.println("contenu de la liste i=:"+i);
           for (int k=0; k<intList.size(); k++)
          System.out.println(intList.get(k).xExp+" * "+intList.get(k).zExp+" * "+intList.get(k).aExp);

       
         
         }*/
    }


}