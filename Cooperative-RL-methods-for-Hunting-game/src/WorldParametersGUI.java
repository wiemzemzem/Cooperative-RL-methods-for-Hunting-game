/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myprogram;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import simbad.sim.Simulator;
import simbad.sim.World;

public class WorldParametersGUI extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JComboBox combo1 = new JComboBox();
    private JComboBox combo2 = new JComboBox();

//EnvironmentDescription ed;
    MyEnv env;

    public WorldParametersGUI(MyEnv envr, World world, Simulator simulator) {
        super("RL Parameters");
        createGui();
        this.env = envr;

        //this.env=env;
    }

    private void createGui() {

        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(new BorderLayout());

        JPanel panel1 = new JPanel();
        panel.add(panel1, BorderLayout.NORTH);

        GridLayout gl = new GridLayout(3, 2, 2, 4);// 0 pixels d'espace entre les colonnes, 5 pixels d'espace entre les lignes
        panel1.setLayout(gl);

        String[] tab1 = {"World 1", "World 2", "World 3", "World 4"};
        combo1 = new JComboBox(tab1);
        combo1.addActionListener(new ItemActionCombo1());
        combo1.setPreferredSize(new Dimension(80, 20));

        String[] tab2 = {"Position 1", "Position 2", "Position 3", "Position 4","Position 5", "Position 6"};
        combo2 = new JComboBox(tab2);
        combo2.addActionListener(new ItemActionCombo2());
        combo2.setPreferredSize(new Dimension(80, 20));

        panel1.add(new JLabel("  WorldChosen"));
        panel1.add(combo1);
        panel1.add(new JLabel("  GoalsPositions"));
        panel1.add(combo2);

        pack();
    }

    class ItemActionCombo1 implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            /*double x,z;


             // world 1
             if (combo2.getSelectedItem()=="World 1")
             {
             z=5.5f;
             for(int i=0;i<16;i++)
             {
             env.obstacleZ[i].moveToPosition(- 0.5f,z);z-=0.4f;}
             z=-3.5f;
             for(int i=0;i<16;i++)
             {
             env.obstacleX[i].moveToPosition(- 0.5f,z);z-=0.4f;}
        
             }
             // world 2
             if (combo2.getSelectedItem()=="World 2")
             {
             z=8.5f;
             for(int i=0;i<16;i++)
             {
             env.obstacleZ[i].moveToPosition(- 0.5f,z);z-=0.4f;}
             z=-0.5f;
             for(int i=0;i<16;i++)
             {
             env.obstacleX[i].moveToPosition(- 0.5f,z);z-=0.4f;}
             }
             // world 3
             if (combo2.getSelectedItem()=="World 3")
             {
             z=5.5f;
             for(int i=0;i<16;i++)
             {
             env.obstacleZ[i].moveToPosition(- 0.5f,z);z-=0.4f;}
             z=-0.5f;
             for(int i=0;i<16;i++)
             {
             env.obstacleX[i].moveToPosition(- 0.5f,z);z-=0.4f;}
             }
   

             // world 3
             if (combo2.getSelectedItem()=="World 4")
             {
             z=5.5f;
             for(int i=0;i<16;i++)
             {
             env.obstacleZ[i].moveToPosition(2.5f,z);z-=0.4f;}
             z=-0.5f;
             for(int i=0;i<16;i++)
             {
             env.obstacleX[i].moveToPosition(- 3.5f,z);z-=0.4f;}
             }
             */
        }

    }

    class ItemActionCombo2 implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            if (combo2.getSelectedItem() == "Position 1") {
                env.but1X = 14.5f;
                env.but1Z = -14.5f;
            }
            if (combo2.getSelectedItem() == "Position 2") {
                env.but1X = 12.5;//13.5f;
                env.but1Z = -8.5;//-13.5f;
            }
            if (combo2.getSelectedItem() == "Position 3") {
                env.but1X = 2.5;//7.5f;
                env.but1Z = -1.5;//-14.5f;
            }

            if (combo2.getSelectedItem() == "Position 4") {
                env.but1X =9.5f;//10.5f;//celui-ci 12.5f;// 14.5f;
                env.but1Z =-2.5f;//1.5f;//celui-ci -8.5f;//-7.5f;
            }
            if (combo2.getSelectedItem() == "Position 5") {
                env.but1X = 6.5f;
                env.but1Z = 10.5f;
            }
if (combo2.getSelectedItem() == "Position 6") {
                env.but1X = -2.5f;
                env.but1Z = -12.5f;
            }

            env.but1.moveToPosition(env.but1X, env.but1Z);

        }
    }

}
