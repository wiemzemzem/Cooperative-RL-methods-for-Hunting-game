/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package myprogram;

import static java.lang.Math.abs;
import static myprogram.MyDiscretRobot.DOWN;
import static myprogram.MyDiscretRobot.RIGHT;
import static myprogram.MyDiscretRobot.UP;
import static myprogram.MyDiscretRobot.LEFT;


/**
 *
 * @author Wiem
 */
public class Case {
    double caseX,caseZ;
    //MyEnv caseEnv;
    
    public Case (MyEnv env,double x,double z)
    {
        caseX=x;
        caseZ=z;
      //  caseEnv=env;
    }

    public Case(double x, double z) {
        caseX=x;
        caseZ=z;
    }
    
    Case nextCase (int orientation)
    {
    Case nextC=null;
    switch (orientation)
            {
                case UP:nextC=new Case(caseX+1,caseZ);break;
                case RIGHT:nextC=new Case(caseX,caseZ-1);break;
                case DOWN:nextC=new Case(caseX-1,caseZ);break;
                case LEFT:nextC=new Case(caseX,caseZ+1);break;
            }   
    return nextC;
    
    }
    
}