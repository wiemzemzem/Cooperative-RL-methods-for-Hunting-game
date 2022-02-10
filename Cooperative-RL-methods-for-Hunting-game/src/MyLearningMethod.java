/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myprogram;

import java.util.ArrayList;

/**
 *
 * @author Wiem
 */
public class MyLearningMethod{

    Case[] table_S;
    double[][][] table_P;
    double[][][] table_Q;
    int maxIndicePosRelatives, nbPositionsRelatives, nbAction, nbOtherRobots;
    double beta = 0.8, max_TimeSteps, alpha = 0.8, gamma = 0.9;

    /* MersenneTwister rndMT1;
     MersenneTwister rndMT2;
     MersenneTwister rndMT3;
     MersenneTwister rndMT4;*/
    public MyLearningMethod(int numVoisins, double maxTime) {
        max_TimeSteps=maxTime;
        nbOtherRobots=numVoisins;
        maxIndicePosRelatives = 6;//6-->env6*6  5--->env 5*5
        nbPositionsRelatives = (int) Math.pow((2 * maxIndicePosRelatives + 1), 2);
        nbAction = 5;
        table_P = new double[nbOtherRobots][nbPositionsRelatives * nbPositionsRelatives][nbAction]; //posRob1*posRob2 actionRob2 
        table_Q = new double[nbOtherRobots][nbPositionsRelatives * nbPositionsRelatives][nbAction * nbAction];
        /***reduction******
         * table_P = new double[nbPositionsRelatives * (nbPositionsRelatives+1)/2][nbAction]; //posRob1*posRob2 actionRob2 
        table_Q = new double[nbPositionsRelatives * (nbPositionsRelatives+1)/][nbAction * nbAction];
         */
        init_Table_P();
        init_Table_Q();
    }

    public void init_Table_P() {// remplissage UP RIGHT DOWN LEFT NOMOVE
        //i div nbPositionsRelatives--> indice position rob1 dans table_S
        // i mod nbPositionsRelatives--> indice position rob2 dans table_S
        //sinon  table_P= new double[][nbAction+2] avec 2 indice pos rob1 et rob2
        for (int k = 0; k < table_P.length; k++) 
        for (int i = 0; i < table_P[0].length; i++) {
            for (int j = 0; j < table_P[0][0].length; j++) {
                table_P[k][i][j] = 0.2;
            }
        }
        
        //changer table_P indice_PQ=0
         
          /*  for (int j = 0; j < table_P[0][0].length; j++) {
                table_P[0][0][j] = 0.2+0.2*j;
            }
             for (int j = 0; j < table_P[0][0].length; j++) {
                table_P[1][0][j] = 0.2+0.5*j;
            }*/
         /*table_P[120][0] = 0.1;
         table_P[120][1] = 0.1;
         table_P[120][2] = 0.2;
         table_P[120][3] = 0.4;
         table_P[120][4] = 0.2;*/
         
    }

    public void affich_Table_P() {
         for (int k = 0; k < table_P.length; k++) 
         {
        System.out.println("*********** La matrice Table_P du robot "+k+"   **************");

        for (int i = 0; i < table_P[0].length; i++) {
            System.out.print(i);
            for (int j = 0; j < table_P[0][0].length; j++) {
                System.out.print(" * " + table_P[k][i][j]);
            }
            System.out.println();
        }
    }
    }
    public void init_Table_P1(int rob) {// remplissage UP RIGHT DOWN LEFT NOMOVE
       
        
        //changer table_P indice_PQ=0
          for (int k = 0; k < table_P.length; k++) 
            for (int j = 0; j < table_P[0][0].length; j++) {
                table_P[k][0][j] = 0.2+(0.2*k);//+j);
            }
             table_P[0][0][0] = 0.9;//+j);
         for (int k = 0; k < table_Q.length; k++) 
        for (int i = 0; i < table_Q[0].length; i++) {
            for (int j = 0; j < table_Q[0][0].length; j++) {
                table_Q[k][0][j] = 0.04+0.04*k;//+j);;
            }
        }
           table_Q[1][0][0] = 0.9;//+j);
         
    }
     public void affich_Ligne_Table_P(int l) {
         for (int k = 0; k < table_P.length; k++) 
         {
        System.out.println("*********** La matrice Table_P du robot "+k+"   **************");

        
            System.out.print("ligne "+l+"-->");
            for (int j = 0; j < table_P[0][0].length; j++) {
                System.out.print(" * " + table_P[k][l][j]);
            }
            System.out.println();
        }
    
    }
    public void init_Table_Q() {// remplissage Couple1(a1,a2) .... Couple25(a1,a2): il y a 5*5 (a1,a2) pour chaque (s1,s2)

        //i div nbPositionsRelatives--> indice position rob1 dans table_S
        // i mod nbPositionsRelatives--> indice position rob2 dans table_S
        //j div nbActions--> action rob1
        // j mod nbActions-->action rob2
        for (int k = 0; k < table_Q.length; k++) 
        for (int i = 0; i < table_Q[0].length; i++) {
            for (int j = 0; j < table_Q[0][0].length; j++) {
                table_Q[k][i][j] = 0.04;
            }
        }
        /*table_Q[120][3] = 0.1;
         table_Q[120][8] = 0.1;
         table_Q[120][13] = 0.2;
         table_Q[120][18] = 0.4;
         table_Q[120][23] = 0.2;*/
    }

    public void affich_Table_Q() {
        System.out.println("*********** La matrice Table_Q**************");
        for (int i = 0; i < table_Q.length; i++) {
            System.out.print(i);
            for (int j = 0; j < table_Q[0].length; j++) {
                System.out.print(" * " + table_Q[i][j]);
            }
            System.out.println();
        }
    }
     public void affich_Ligne_Table_Q(int l) {
        for (int k = 0; k < table_Q.length; k++) 
         {
        System.out.println("*********** La matrice Table_Q du robot "+k+"   **************");

     
            System.out.print("ligne "+l+"-->");
            for (int j = 0; j < table_Q[k][0].length; j++) {
                System.out.print(" * " + table_Q[k][l][j]);
            }
            System.out.println();
         }
    }
    
    
/***********************************************************/
    // les méthodes compute_pos_R1R2 et compute_indice_PQ avec |s|*|s|= nbPositionsRelatives=121*121
    //déterminer la ligne équivalente à (pos_Rob1,pos_Rob2) dans la table_P et table_Q
    public int compute_indice_PQ(int x1, int z1, int x2, int z2,MyEnv myenv) //x et z sont des positions relatives
    {
        int indice_1, indice_2;//déterminer l'équivalence de (x,z) dans [0,120]
        int indice_PQ;//déterminer la ligne équivalente à (indice_1,indice_2) dans la table_P et table_Q
        indice_1 = (x1 + maxIndicePosRelatives) * (2 * maxIndicePosRelatives + 1) + (z1 + maxIndicePosRelatives);
        indice_2 = (x2 + maxIndicePosRelatives) * (2 * maxIndicePosRelatives + 1) + (z2 + maxIndicePosRelatives);
       // myenv.outputsList.add("indice_1=" + indice_1);
        //myenv.outputsList.add("indice_2=" + indice_2);
        indice_PQ = indice_1 * nbPositionsRelatives + indice_2;
        myenv.outputsList.add("("+x1+","+z1+") ("+x2+","+z2+")-->"+ "indice_PQ=" + indice_PQ);
        return indice_PQ;
    }
    //compute_pos_R1R2 Inverse de compute_indice_PQ: déterminer (pos_Rob1,pos_Rob2) équivalent à indice_PQ
    public int[] compute_pos_R1R2(int indice_PQ,MyEnv myenv) 
    {   int[] posR1R2=new int[4];//x1  z1  x2  z2
          myenv.outputsList.add("compute_pos_R1R2:      indice_PQ=" + indice_PQ);
        int indice_1, indice_2;//déterminer l'équivalence de (x,z) dans [0,120]
        indice_1=(int)(indice_PQ/nbPositionsRelatives);
         indice_2=indice_PQ % nbPositionsRelatives;
       
        posR1R2[0]= (int)(indice_1/(2 * maxIndicePosRelatives + 1))-maxIndicePosRelatives;
        posR1R2[1]= indice_1%(2 * maxIndicePosRelatives + 1)-maxIndicePosRelatives;
          myenv.outputsList.add("indice_1=" + indice_1+ "   x1="+posR1R2[0]+"    z1="+posR1R2[1]);
        posR1R2[2]= (int)(indice_2/(2 * maxIndicePosRelatives + 1))-maxIndicePosRelatives;
        posR1R2[3]= indice_2%(2 * maxIndicePosRelatives + 1)-maxIndicePosRelatives;
          myenv.outputsList.add("indice_2=" + indice_2+ "   x2="+posR1R2[2]+"    z2="+posR1R2[3]);
        return posR1R2;
    }
    /***********************************************************/
    // les méthodes compute_pos_R1R2 et compute_indice_PQ avec réduction de |s|*|s|= (n(n+1))/2=(121*122)/2
    //déterminer la ligne équivalente à (pos_Rob1,pos_Rob2) dans la table_P et table_Q
    public int compute_indice_PQ_reduction(int x1, int z1, int x2, int z2,MyEnv myenv) //x et z sont des positions relatives
    {
        int indice_1, indice_2;//déterminer l'équivalence de (x,z) dans [0,120]
        int indice_PQ;//déterminer la ligne équivalente à (indice_1,indice_2) dans la table_P et table_Q
        indice_1 = (x1 + maxIndicePosRelatives) * (2 * maxIndicePosRelatives + 1) + (z1 + maxIndicePosRelatives);
        indice_2 = (x2 + maxIndicePosRelatives) * (2 * maxIndicePosRelatives + 1) + (z2 + maxIndicePosRelatives);
        myenv.outputsList.add("indice_1=" + indice_1);
        myenv.outputsList.add("indice_2=" + indice_2);
        //obligation indice_2>indice_1
        if (indice_1>indice_2)
        {indice_PQ=indice_1;
         indice_1=indice_2;
         indice_2=indice_PQ;}
         
        indice_PQ = ((nbPositionsRelatives*(nbPositionsRelatives+1))-((nbPositionsRelatives-indice_1) *(nbPositionsRelatives-indice_1 +1)))/2 +indice_2; 
        
        myenv.outputsList.add("indice_P=" + indice_PQ);
        return indice_PQ;
    }
    //compute_pos_R1R2 Inverse de compute_indice_PQ: déterminer (pos_Rob1,pos_Rob2) équivalent à indice_PQ
    // les positions de R1 ET R2 peuvent être inversée suite à un inversement lors du calcul de indice_PQ
    public int[] compute_pos_R1R2_reduction(int indice_PQ,MyEnv myenv) 
    {   int[] posR1R2=new int[4];//x1  z1  x2  z2
          myenv.outputsList.add("indice_PQ=" + indice_PQ);
        int indice_1=0, indice_2=indice_PQ,p=nbPositionsRelatives;//déterminer l'équivalence de (x,z) dans [0,120]
       while(indice_2>p)
       {indice_1+=1;
        indice_2-=p;
        p-=1;}
        indice_2+=indice_1;      
        posR1R2[0]= (int)(indice_1/(2 * maxIndicePosRelatives + 1))-maxIndicePosRelatives;
        posR1R2[1]= indice_1%(2 * maxIndicePosRelatives + 1)-maxIndicePosRelatives;
          myenv.outputsList.add("indice_1=" + indice_1+ "   x1="+posR1R2[0]+"    z1="+posR1R2[1]);
        posR1R2[2]= (int)(indice_2/(2 * maxIndicePosRelatives + 1))-maxIndicePosRelatives;
        posR1R2[3]= indice_2%(2 * maxIndicePosRelatives + 1)-maxIndicePosRelatives;
          myenv.outputsList.add("indice_2=" + indice_2+ "   x2="+posR1R2[2]+"    z2="+posR1R2[3]);
        return posR1R2;
    }
    /****************************************************/
// teamate modeling
    public void update_Table_P(int numVoisin, int t, int indice_P, int a2,MyEnv myenv) {
        double betaPow = Math.pow(beta, max_TimeSteps - t + 1);
        double sum = 0;
        double[] anc_P = new double[nbAction];
        //récupérer anciens valeurs de table_p

       myenv.outputsList.add("*********** La matrice Table_P avant mise à jour**************numVoisin="+numVoisin+"       indice=" + indice_P+"  NpasEpisode="+t+"   a2="+a2);
      myenv.outputsList.add(table_P[numVoisin][indice_P][0] + " * "+table_P[numVoisin][indice_P][1] + " * "+table_P[numVoisin][indice_P][2] + " * "+table_P[numVoisin][indice_P][3] + " * "+table_P[numVoisin][indice_P][4] );
       
        for (int j = 0; j < table_P[numVoisin][0].length; j++) {
            anc_P[j] = table_P[numVoisin][indice_P][j];
         //   System.out.print(table_P[indice_P][j] + " * ");
        }
       // System.out.println();
        

        //mise à jour de table_P  
        for (int a = 0; a < nbAction; a++) {
            if (a == a2) {
                for (int ai = 0; ai < nbAction; ai++) {
                    if (ai != a)//ai!=a2
                    {
                        sum = sum + anc_P[ai];
                    }
                }

                table_P[numVoisin][indice_P][a] = anc_P[a] + betaPow * sum;

            } else {
                table_P[numVoisin][indice_P][a] = (1 - betaPow) * anc_P[a];
            }
        }

        //affichage après update
         myenv.outputsList.add("***** La matrice Table_P après mise à jour*** ");
          myenv.outputsList.add(table_P[numVoisin][indice_P][0] + " * "+table_P[numVoisin][indice_P][1] + " * "+table_P[numVoisin][indice_P][2] + " * "+table_P[numVoisin][indice_P][3] + " * "+table_P[numVoisin][indice_P][4] );
       /* for (int j = 0; j < table_P[0].length; j++) {
            System.out.print(table_P[indice_P][j] + " * ");
        }
        System.out.println();*/
    }
 
    /******************************/
        //Update the Qvalue via RL
        //nextState_PQ(indice_PQ,as,a0,myenv) puisque seulement le robot actuelle maj ancx et ancz: LE DEUXIEME PAS ENCORE
      public void update_Table_Q(int numVoisins,int indice_PQ, int as, int a0, double recompense,MyEnv myenv) {
          // Qsa[i][orientation + 2] =0.1 * Qsa[i][orientation + 2] + 0.9 * (recompense + 0.99 * this.maxQsa(new Case(posX, posZ)));
        myenv.outputsList.add("avant maj Q: indicePQ="+ indice_PQ);
         for (int j = 0; j < table_Q[numVoisins][indice_PQ].length; j++) 
                 myenv.outputsList.add(j+" --> " + table_Q[numVoisins][indice_PQ][j]);
         myenv.outputsList.add(" next state (as,a0)=("+as+","+a0+")-->");
          table_Q[numVoisins][indice_PQ][as * nbAction + a0] =(1-alpha) *  table_Q[numVoisins][indice_PQ][as * nbAction + a0] + alpha * (recompense + gamma * this.maxQ_next_action_a0(numVoisins,nextState_PQ(indice_PQ,as,a0,myenv),myenv));
    
      myenv.outputsList.add("après maj Q: indicePQ="+ indice_PQ+"  as * nbAction + a0="+(as * nbAction + a0)+" --> " + table_Q[numVoisins][indice_PQ][as * nbAction + a0]);
      }
      
    /**
     * *******************************
     */
    /* à partir de l'orientation choisie, déterminer l'action réelle*/
    public int RealAction(int dir, int orientation) {

        switch (orientation) {
            case 1:
                return (dir + 3) % 4;
            case 2:
                return (dir + 2) % 4;
            case 3:
                return (dir + 1) % 4;
            default:
                return dir;
        }
        
           }
    
      /*******************/
      public int[] single_nextState(int x, int z, int a)
      { 
        //R1 exécute as
        switch (a)
        {
            case 0:x++;break;
            case 1:z--;break;
            case 2:x--;break;
            case 3:z++;break;
            default:break;
         }
        
    return new int[]{x,z};//x z
    
      }
    //déterminer indice_next_PQ connaissant l'état actuel indice_PQ et les actions as et a0 à effectuer
    public int nextState_PQ(int indice_PQ, int as, int a0,MyEnv myenv)
    {
        int[] posR1R2=compute_pos_R1R2(indice_PQ,myenv);
        //R1 exécute as
        int[] posR1= single_nextState(posR1R2[0],posR1R2[1], as);
        //R2 exécute a0
        int[] posR2= single_nextState(posR1R2[2],posR1R2[3], a0);
        //myenv.outputsList.add("next state-->");
    return this.compute_indice_PQ(posR1[0], posR1[1], posR2[0], posR2[1],myenv);
    }
    /***********************/
          //calculer tt les a0* vérifiant maxP(s,a0)
       public  ArrayList<Integer> compute_MaxP(int numVoisin, int indice_PQ,MyEnv myenv)
       {double maxP;
       ArrayList<Integer> intListP = new ArrayList<Integer>();//contient tt les actions verifiant maxP
 myenv.outputsList.add(  "calcul maxP ("+indice_PQ+")    numVoisin="+numVoisin);     
//déterminer argmax_P(s,a0);
        maxP = table_P[numVoisin][indice_PQ][0];
        for (int a0 = 1; a0 < nbAction; a0++) {
            if (table_P[numVoisin][indice_PQ][a0] > maxP) {
                maxP = table_P[numVoisin][indice_PQ][a0];
            }
        }
 myenv.outputsList.add( "maxP=" + maxP+"-->les actions vérifiant cela:");
        for (int a0 = 0; a0 < nbAction; a0++) {
            if (table_P[numVoisin][indice_PQ][a0] == maxP) {
                intListP.add(a0);
                
                 myenv.outputsList.add("a0(maxP)=" + a0);
            }
        }
        return intListP;}
      
        // //déterminer maxQSA(a's,a'0)
      public double maxQ_next_action_a0(int numVoisin,int indice_PQ,MyEnv myenv)
      {myenv.outputsList.add(" calcul  maxQ_next_action_a0");
          //determiner tt les actions next_a'0 vérifiant a'0=argmax_a0_P(s',a0)
       ArrayList<Integer> intListP_next_a0=compute_MaxP(numVoisin,indice_PQ,myenv);
           double maxQ_nextA0=-10;
           myenv.outputsList.add("selection de  maxQ_nextA0=max_a0_ TableQ(s,as,a0)=v(as) parmi maxP pour chaque as");
             maxQ_nextA0= table_Q[numVoisin][indice_PQ][intListP_next_a0.get(0)];//as=0
            for (int as = 0; as < nbAction; as++)   
                for (int i = 0; i < intListP_next_a0.size(); i++) {
                    if(maxQ_nextA0<table_Q[numVoisin][indice_PQ][as * nbAction + intListP_next_a0.get(i)])
                    maxQ_nextA0=table_Q[numVoisin][indice_PQ][as * nbAction + intListP_next_a0.get(i)];
                myenv.outputsList.add(" as="+as+"   a0=" + intListP_next_a0.get(i) + "        maxQ_nextA0="+maxQ_nextA0+"    table_Q["+numVoisin+"]["+indice_PQ+"]["+(as * nbAction + intListP_next_a0.get(i))+"]="+table_Q[numVoisin][indice_PQ][as * nbAction + intListP_next_a0.get(i)]);
                  
                }
                 
                       
      return maxQ_nextA0;}
    
    
    /***********************/
    
    
    
  
   
    //Action selection strategy
   public int ActionSelection(int[] indice_PQ, boolean episode_Treshold_Is_Depassed, MersenneTwister rndMT,int orientation, MyEnv myenv) {
     double[] Va=null;
        //pour chaque action de l'agent courant calculer sa v(s,a)
        //cas numThisEpisode>Treshold
        if (episode_Treshold_Is_Depassed == true) 
            Va=ValueAsA0_episode_Treshold_Is_Depassed(indice_PQ,myenv);
        else
            Va=ValueAsA0_episode_Treshold_Is_Not_Depassed(indice_PQ,myenv);
        return RealAction(ActionSelection(Va,rndMT,myenv),orientation);
    }

   public int ActionSelection(double[] Va,MersenneTwister rndMT,MyEnv myenv)
    { //calcul maxV
       ArrayList<Integer> intListV = new ArrayList<Integer>();//contient tt les actions verifiant maxV
       double maxV=Va[0];
       for (int a = 1; a < Va.length; a++) 
        if(Va[a]>maxV)
            maxV=Va[a];
       //determiner As vérifiant maxV
      myenv.outputsList.add("maxV=" + maxV);
        for (int as = 0; as < nbAction; as++) {
            if (Va[as] == maxV) {
                intListV.add(as);
               myenv.outputsList.add("as(maxV)=" + as);
            }
        }
           //selection aléatoire d'une action=argmaxV parmi tt les actions possibles
           int actionChosen = rndMT.nextInt(intListV.size());
            actionChosen = (int) (intListV.get(actionChosen));
          myenv.outputsList.add("action choise aléatoire=" + actionChosen);
        
                
        return actionChosen;
    }

    
    //Choix d'action: cas numThisEpisode<=Treshold
    public double[] ValueAsA0_episode_Treshold_Is_Not_Depassed(int[] liste_iPQ,MyEnv myenv)
    { double[] Va = new double[nbAction];
    myenv.outputsList.add("treshold is not depassed***********");
        for (int as = 0; as < nbAction; as++){
             for (int a0 = 0; a0 < nbAction; a0++)  //calcul de maxQ(as,contenu_intList_maxP)  
                for(int k=0;k<table_P.length;k++)
                 Va[as] += table_P[k][liste_iPQ[k]][a0]*table_Q[k][liste_iPQ[k]][as * nbAction + a0];
                
                myenv.outputsList.add("Va["+as+"]=" + Va[as]);}
                
            
        
         return Va;   
    }
      //Choix d'action: cas numThisEpisode>Treshold
   
       

    
       public double[] ValueAsA0_episode_Treshold_Is_Depassed(int[] indice_PQ,MyEnv myenv) {
        //calcul Va(as|ajoint*) pour chaque as du robot actuel
           double[] Va = new double[nbAction];
      
        myenv.outputsList.add("treshold is already depassed");
        //intialiser Va à 0
        for (int a = 0; a < Va.length; a++) {
            Va[a] = 0;
        }
        //déterminer la liste des actions vérifiants argmax_P(s,a0);
        ArrayList <Integer> [] intListP = new ArrayList[nbOtherRobots]; //contient tt les actions verifiant maxP pour chaque robot voisin
        for (int i=0;i<intListP.length;i++)
        intListP[i]=compute_MaxP(i,indice_PQ[i],myenv);
        
        //calcul tableau des actions jointes vérifiant maxP
        int [][] tab_ActionsJointes_maxP;
        
        tab_ActionsJointes_maxP=tableOfJointActions(intListP,myenv);
        
        //calcul SommeQ(as+actions jointes) pour chaque as du robot actuel
         double[][] sommeQ=new double [tab_ActionsJointes_maxP.length][nbAction];
        //calcul les sommeQ correspondant à chaque liste d'actions jointes
        myenv.outputsList.add("la table sommeQ pour chaque action jointe et pur chaque as");
       for (int as=0;as<nbAction;as++)
       {   myenv.outputsList.add("as="+as);
           for (int actionJointe=0;actionJointe<sommeQ.length;actionJointe++)
         {sommeQ[actionJointe][as]=0;
           for (int r=0;r<tab_ActionsJointes_maxP[0].length;r++)
           {
               sommeQ[actionJointe][as]+=table_Q[r][indice_PQ[r]][as*nbAction+tab_ActionsJointes_maxP[actionJointe][r]];
         myenv.outputsList.add("________________ robot="+r);
         myenv.outputsList.add("________________ sonindice_Q="+indice_PQ[r]);
            myenv.outputsList.add("________________ as*nbAction+tab_ActionsJointes_maxP[actionJointe][r]="+(as*nbAction+tab_ActionsJointes_maxP[actionJointe][r]));
           myenv.outputsList.add("________________ table_Q(as*5+a0)="+table_Q[r][indice_PQ[r]][as*nbAction+tab_ActionsJointes_maxP[actionJointe][r]]);
           }
           myenv.outputsList.add("________________---> SommeQ="+sommeQ[actionJointe][as]);
         }
       }
       //affficher table sommeQ
      myenv.outputsList.add("la table sommeQ pour chaque action jointe et pour chaque as");
        for (int actionJointe=0;actionJointe<sommeQ.length;actionJointe++)
        { myenv.outputsList.add("actionJointe="+actionJointe+"-->   ");
            for (int as=0;as<nbAction;as++)
             myenv.outputsList.add(sommeQ[actionJointe][as]+"    *  ");
           // System.out.println();
        
        }
      //remplir table Va pour chaque as::: Va(as|ajoint*)=prod(maxp)*sommeQ---> je peux ne pas multiplier par maxP  car c'est la mem pour tt les as (p ne dépend pas de as)
        for (int as=0;as<nbAction;as++)
        { Va[as]=sommeQ[0][as];
         for (int actionJointe=1;actionJointe<sommeQ.length;actionJointe++)
             if (Va[as]<sommeQ[actionJointe][as])
                 Va[as]=sommeQ[actionJointe][as];
         //Afficher Va
       myenv.outputsList.add("Va["+as+"]=" + Va[as]+"   *   ");
        }
       
        
       

return Va;
       }

    private int[][] tableOfJointActions(ArrayList<Integer>[] intListP, MyEnv myenv) {
        
           
       myenv.outputsList.add("*********Affichage de intListP.length="+intListP.length);
        for (int i=0;i<intListP.length;i++)
        {  for (int k=0;k<intListP[i].size();k++)
                 myenv.outputsList.add(intListP[i].get(k)+"  *  ");
        
        //System.out.println();
        }
        //calcul actions jointes
        int NbActJointes=1;
       for (int i=0;i<intListP.length;i++) 
           NbActJointes*=intListP[i].size();
       myenv.outputsList.add("********* NbActJointes= "+ NbActJointes);
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
        */
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
       myenv.outputsList.add("NbSuccessionSameActions= "+NbSuccessionSameActions);
        for (int rep1=0;rep1<NbActJointes;rep1+=(NbSuccessionSameActions*intListP[robo].size()))
            
        for (int action=0;action<intListP[robo].size();action++)        
        for (int rep2=0;rep2<NbSuccessionSameActions;rep2++) 
             ActionsJointes[action*NbSuccessionSameActions+rep2+rep1][robo]=intListP[robo].get(action);
          
        
       
        }
       
        //affichage tableau des actions jointes
        
        myenv.outputsList.add("********* Affichage Tableau des Actions Jointes  ********");
       for (int a_jointe=0;a_jointe<ActionsJointes.length;a_jointe++) 
        { myenv.outputsList.add("n"+a_jointe+"  *  ");
            for (int rob=0;rob<ActionsJointes[0].length;rob++)
                  myenv.outputsList.add(ActionsJointes[a_jointe][rob]+"  *  ");
        
        // System.out.println();
        }
        
    
    return ActionsJointes;}
}