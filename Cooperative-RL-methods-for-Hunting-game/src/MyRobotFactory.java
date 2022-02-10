/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package myprogram;

import javax.vecmath.Vector3d;
import simbad.sim.Agent;
import simbad.sim.RangeSensorBelt;
import simbad.sim.RobotFactory;

/**
 *
 * @author Wiem
 */
public class MyRobotFactory extends RobotFactory{

   
        
   static public RangeSensorBelt addSonarBeltSensor(Agent agent,int nbSonars) {
        //double agentHeight = agent.getHeight();
        double agentRadius = agent.getRadius();
        RangeSensorBelt sonarBelt = new RangeSensorBelt((float) agentRadius,
                0f, 3, nbSonars, RangeSensorBelt.TYPE_SONAR,0);
        sonarBelt.setUpdatePerSecond(40);
       //sonarBelt.setName("sonars");
        //Vector3d pos = new Vector3d(0, agentHeight / 2, 0.0);
        //agent.addSensorDevice(sonarBelt, pos, 0);
        return sonarBelt;
    }
} 
    