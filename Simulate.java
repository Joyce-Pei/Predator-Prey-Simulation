/*
 * Simulate.java
 */

/**
 * CS5004 Assignment 7: Simulate
 * This class creates and displays a grid world, simulate the prey and predators' behavior,
 * and tests the Organism, Ant, Doodlebug class.
 * 
 * @Yuehan Pei
 * @since 2020-06-28
 */ 

import Critters.Organism;
import Critters.Ant;
import Critters.Doodlebug;
import java.util.Random;
import java.util.Scanner;

public class Simulate {
    /**
     * This function generates and display the world, simulate ant and doodlebug's behavior,
     * based on the methods and parameters of Organism, Ant, and Doodlebug.
     * 
     * @param args (unused)
     */    

    public static void main(String[] args) {
        //Create an 20 x 20 array of critters
        Organism[][] organisms = new Organism[20][20];

        //Initialize the world with 5 doodlebugs and 100 ants
        int num = 0;
        Random rand = new Random();
        while (num < 105) {
                if (num < 100) {
                int i = rand.nextInt(20);
                int j = rand.nextInt(20);
                if (organisms[i][j] == null) {
                    organisms[i][j] = new Ant(0,0,i,j,false);
                    num ++;
                }
            }
            else if (num >= 100) {
                int i = rand.nextInt(20);
                int j = rand.nextInt(20);
                if (organisms[i][j] == null) {
                    organisms[i][j] = new Doodlebug(0,0,0,i,j,false);
                    num ++;
                }
            }
        }

        //Print out the current grid and ask for user input
        printGrid(organisms);
        count(organisms);
        System.out.println("Press enter to move to the next time step. Enter END to exit.");
        Scanner kbd = new Scanner(System.in);
        String s = kbd.nextLine();
        
        while ((!s.equals("")) && (!s.equals("END"))) {
            System.out.println("Error. Press enter to move to the next time step. Enter END to exit.");
            s = kbd.nextLine();
        }
        int c = 0;
        //When the user keep pressing enter, keep running the program
        while (s.equals("")) {
            //First loop Doodlebug, check if there is any ants next to it, if yes, move to that cell and eat the ant
            //otherwise, move according to the same rules as the ant
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    if (organisms[i][j] instanceof Doodlebug) {
                        Doodlebug doodlebug = (Doodlebug) organisms[i][j];
                        if (doodlebug.getMoved() == false) {
                            //if there is ant nearby
                            if (canEat(organisms, doodlebug)){
                                int d = rand.nextInt(4);
                                //keep choosing d until it can move
                                while (!canEatD(d, organisms, doodlebug)) {
                                    d = rand.nextInt(4);
                                }
                                doodlebug.move(d);
                                doodlebug.eat();
                                //reset the grid
                                if (d == 0) {
                                    organisms[i-1][j] = doodlebug;
                                    organisms[i][j] = null;
                                }
                                else if (d == 1) {
                                    organisms[i+1][j] = doodlebug;
                                    organisms[i][j] = null;
                                }
                                else if (d == 2) {
                                    organisms[i][j-1] = doodlebug;
                                    organisms[i][j] = null;
                                }
                                else {
                                    organisms[i][j+1] = doodlebug;
                                    organisms[i][j] = null;
                                }

                            }
                            //if there is no ant nearby but it can move
                            else if (!canEat(organisms, doodlebug) && canMove(organisms, doodlebug)) {
                                //only move, didn't eat, starve +1
                                doodlebug.setStarve(doodlebug.getStarve() + 1);
                                int d = rand.nextInt(4);
                                //keep choosing d until it can move
                                while (!canMoveD(d, organisms, doodlebug)) {
                                    d = rand.nextInt(4);
                                }
                                doodlebug.move(d);
                                //reset the grid
                                if (d == 0) {
                                    organisms[i-1][j] = doodlebug;
                                    organisms[i][j] = null;
                                }
                                else if (d == 1) {
                                    organisms[i+1][j] = doodlebug;
                                    organisms[i][j] = null;
                                }
                                else if (d == 2) {
                                    organisms[i][j-1] = doodlebug;
                                    organisms[i][j] = null;
                                }
                                else {
                                    organisms[i][j+1] = doodlebug;
                                    organisms[i][j] = null;
                                }
                            }

                            //after moved, or moved and breed, or cannot moved, step +1
                            doodlebug.setStep(doodlebug.getStep() + 1);
                        }
                    }
                }
            }

            //Second loop Doodlebug, check if there is any bug can breed
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j ++) {
                    //check if it is Doodlebug
                    if (organisms[i][j] instanceof Doodlebug) {
                        Doodlebug doodlebug = (Doodlebug) organisms[i][j];
                        //check if it has been moved, only the ones has moved have space to breed
                        if (doodlebug.getMoved() == true && doodlebug.getStep() >= 8 & doodlebug.getOffspring() < 10 
                        && canMove(organisms, doodlebug)) {
                            doodlebug.breed();
                            //randomly pick a cell to place offspring
                            int d = rand.nextInt(4);
                            //keep choosing d until it can put child in that direction
                            while (!canMoveD(d, organisms, doodlebug)) {
                                d = rand.nextInt(4);
                            }
                            //reset the grid
                            if (d == 0) {
                                organisms[i-1][j] = new Doodlebug(0,0,0,i-1,j,false);
                            }
                            else if (d == 1) {
                                organisms[i+1][j] = new Doodlebug(0,0,0,i+1,j,false);
                            }
                            else if (d == 2) {
                                organisms[i][j-1] = new Doodlebug(0,0,0,i,j-1,false);
                            }
                            else {
                                organisms[i][j+1] = new Doodlebug(0,0,0,i,j+1,false);
                            }
                        }
                    }
                }
            }

            //Third loop Doodlebug, check if there is any bug will die
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    if (organisms[i][j] instanceof Doodlebug) {
                        Doodlebug doodlebug = (Doodlebug) organisms[i][j];
                        if (doodlebug.getStarve() >= 3) {
                            organisms[i][j] = null;
                        }
                    }
                }
            }

            //Reset all the Doodlebug.moved to false
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j ++) {
                    if (organisms[i][j] instanceof Doodlebug) {
                        Doodlebug doodlebug = (Doodlebug) organisms[i][j];
                        doodlebug.setMoved(false);
                    }
                }
            }

            //First loop Ant, move all the ants that can be moved
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j ++) {
                    //check if it is Ant
                    if (organisms[i][j] instanceof Ant) {
                        Ant ant = (Ant) organisms[i][j];
                        //check if it has been moved
                        if (ant.getMoved() == false) {
                            //check if it has space to move or breed
                            if (canMove(organisms, ant)) {
                                int d = rand.nextInt(4);
                                //keep choosing d until it can move
                                while (!canMoveD(d, organisms, ant)) {
                                    d = rand.nextInt(4);
                                }
                                ant.move(d);
                                //reset the grid
                                if (d == 0) {
                                    organisms[i-1][j] = ant;
                                    organisms[i][j] = null;
                                }
                                else if (d == 1) {
                                    organisms[i+1][j] = ant;
                                    organisms[i][j] = null;
                                }
                                else if (d == 2) {
                                    organisms[i][j-1] = ant;
                                    organisms[i][j] = null;
                                }
                                else {
                                    organisms[i][j+1] = ant;
                                    organisms[i][j] = null;
                                }
                            }
                            //ant step increase no matter it has space to move or not
                            ant.seStep(ant.getStep() + 1);
                        }
                    }
                }
            }

            //Second loop Ant, check if any ant can breed
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j ++) {
                    //check if it is Ant
                    if (organisms[i][j] instanceof Ant) {
                        Ant ant = (Ant) organisms[i][j];
                        //check if it has been moved, only the ones has moved have space to breed
                        if (ant.getMoved() == true && ant.getStep() >= 3 & ant.getOffspring() < 10 && canMove(organisms, ant)) {
                            ant.breed();
                            //randomly pick a cell to place offspring
                            int d = rand.nextInt(4);
                            //keep choosing d until it can put child in that direction
                            while (!canMoveD(d, organisms, ant)) {
                                d = rand.nextInt(4);
                            }
                            //reset the grid
                            if (d == 0) {
                                organisms[i-1][j] = new Ant(0,0,i-1,j,false);
                            }
                            else if (d == 1) {
                                organisms[i+1][j] = new Ant(0,0,i+1,j,false);
                            }
                            else if (d == 2) {
                                organisms[i][j-1] = new Ant(0,0,i,j-1,false);
                            }
                            else {
                                organisms[i][j+1] = new Ant(0,0,i,j+1,false);
                            }
                        }
                    }
                }
            }

            //Reset all the Ant.moved to false
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j ++) {
                    if (organisms[i][j] instanceof Ant) {
                        Ant ant = (Ant) organisms[i][j];
                        ant.setMoved(false);
                    }
                }
            }


            printGrid(organisms);
            count(organisms);
            
            c += 1;
            System.out.printf("%d time steps end.\n", c);

            System.out.println("Press enter to move to the next time step. Enter END to exit.");
            s = kbd.nextLine();
            
            while ((!s.equals("")) && (!s.equals("END"))) {
                System.out.println("Error. Press enter to move to the next time step. Enter END to exit.");
                s = kbd.nextLine();
            }
        }

        if (s.equals("END")) {
            kbd.close();
            System.exit(1);
        }
    }

    
    //The printGrid method prints out the current grid after each step
    private static void printGrid(Organism[][] organisms) {
        System.out.printf("Current grid:\n");
        System.out.printf(" ");
        for (int row = 0; row < 20; row ++) {
            System.out.printf("  %s  ", "-");
        }
        System.out.println();
        for (int i = 0; i < 20; i++) {
            System.out.printf("|");
            for (int j = 0; j < 20; j++) {
                if (organisms[i][j] != null) {
                    System.out.printf("  %s  ", organisms[i][j]);
                }
                if (organisms[i][j] == null) {
                    System.out.printf("  %s  ", " ");
                }
            }
            System.out.printf("|");
            System.out.println();
        }
        System.out.printf(" ");
        for (int row = 0; row < 20; row ++) {
            System.out.printf("  %s  ", "-");
        }
        System.out.println();
    }

    //The canMove method check if a critter can move or have space to breed
    private static boolean canMove(Organism[][] y, Organism x) {
        if (x.getI() == 0) {
            if (x.getJ() == 0) {
                return y[0][1] == null || y[1][0] == null;
            }
            else if (x.getJ() == 19) {
                return y[0][18] == null || y[1][19] == null;
            }
            else {
                return y[x.getI() + 1][x.getJ()] == null || y[x.getI()][x.getJ() + 1] == null ||
                y[x.getI()][x.getJ() - 1] == null;
            }
        }
        else if (x.getI() == 19) {
            if (x.getJ() == 0) {
                return y[19][1] == null || y[18][0] == null;
            }
            else if (x.getJ() == 19) {
                return y[19][18] == null || y[18][19] == null;
            }
            else {
                return y[x.getI() - 1][x.getJ()] == null || y[x.getI()][x.getJ() + 1] == null ||
                y[x.getI()][x.getJ() - 1] == null;
            }
        }
        else if (x.getJ() == 0) {
            return y[x.getI() - 1][x.getJ()] == null || y[x.getI()][x.getJ() + 1] == null ||
            y[x.getI() + 1][x.getJ()] == null;
        }
        else if (x.getJ() == 19) {
            return y[x.getI() - 1][x.getJ()] == null || y[x.getI()][x.getJ() - 1] == null ||
            y[x.getI() + 1][x.getJ()] == null;
        }
        else {
            return y[x.getI() - 1][x.getJ()] == null || y[x.getI()][x.getJ() - 1] == null ||
            y[x.getI() + 1][x.getJ()] == null || y[x.getI()][x.getJ() + 1] == null;
        }
    }
    
    //The canMoveD method check if a critter can move at a certain direction
    private static boolean canMoveD(int d, Organism[][] o, Organism x) {
        //d=0 move up
        if (d == 0 && x.getI() != 0 && o[x.getI() - 1][x.getJ()] == null) {
            return true;
        }
        //d=1 move down
        else if (d == 1 && x.getI() != 19 && o[x.getI() + 1][x.getJ()] == null) {
            return true;
        }
        //d=2 move left
        else if (d == 2 && x.getJ() != 0 && o[x.getI()][x.getJ()-1] == null) {
            return true;
        }
        //d=3 move right
        else if (d == 3 && x.getJ() != 19 && o[x.getI()][x.getJ()+1] == null) {
            return true;
        }
        else {
            return false;
        }
    }
    
    //The count method keeps track of the numbers of two species at the end of every time step
    private static void count(Organism[][] o) {
        int a = 0;
        int b = 0;
        for (int i=0;i<20;i++) {
            for (int j=0;j<20;j++) {
                if (o[i][j] instanceof Ant) {
                    a ++;
                }
                else if (o[i][j] instanceof Doodlebug) {
                    b ++;
                }
            }
            
        }
        System.out.println("Ant: " +a + ", Doodlebug: " +b);
    }

    //The canEat method check if there is an ant next to a doodlebug
    private static boolean canEat(Organism[][] o, Doodlebug d) {
        if (d.getI() == 0) {
            if (d.getJ() == 0) {
                return o[0][1] instanceof Ant || o[1][0] instanceof Ant;
            }
            else if (d.getJ() == 19) {
                return o[0][18] instanceof Ant || o[1][19] instanceof Ant;
            }
            else {
                return o[d.getI() + 1][d.getJ()] instanceof Ant || o[d.getI()][d.getJ() + 1] instanceof Ant ||
                o[d.getI()][d.getJ() - 1] instanceof Ant;
            }
        }
        else if (d.getI() == 19) {
            if (d.getJ() == 0) {
                return o[19][1] instanceof Ant || o[18][0] instanceof Ant;
            }
            else if (d.getJ() == 19) {
                return o[19][18] instanceof Ant || o[18][19] instanceof Ant;
            }
            else {
                return o[d.getI() - 1][d.getJ()] instanceof Ant || o[d.getI()][d.getJ() + 1] instanceof Ant ||
                o[d.getI()][d.getJ() - 1] instanceof Ant;
            }
        }
        else if (d.getJ() == 0) {
            return o[d.getI() - 1][d.getJ()] instanceof Ant || o[d.getI()][d.getJ() + 1] instanceof Ant ||
            o[d.getI() + 1][d.getJ()] instanceof Ant;
        }
        else if (d.getJ() == 19) {
            return o[d.getI() - 1][d.getJ()] instanceof Ant || o[d.getI()][d.getJ() - 1] instanceof Ant ||
            o[d.getI() + 1][d.getJ()] instanceof Ant;
        }
        else {
            return o[d.getI() - 1][d.getJ()] instanceof Ant || o[d.getI()][d.getJ() - 1] instanceof Ant ||
            o[d.getI() + 1][d.getJ()] instanceof Ant || o[d.getI()][d.getJ() + 1] instanceof Ant;
        }

    }

    //The canEatD method check if a doodlebug can eat at a certain direction
    private static boolean canEatD(int d, Organism[][] o, Doodlebug bug) {
        //d=0 move up and eat
        if (d == 0 && bug.getI() != 0 && o[bug.getI() - 1][bug.getJ()] instanceof Ant) {
            return true;
        }
        //d=1 move down
        else if (d == 1 && bug.getI() != 19 && o[bug.getI() + 1][bug.getJ()] instanceof Ant) {
            return true;
        }
        //d=2 move left
        else if (d == 2 && bug.getJ() != 0 && o[bug.getI()][bug.getJ()-1] instanceof Ant) {
            return true;
        }
        //d=3 move right
        else if (d == 3 && bug.getJ() != 19 && o[bug.getI()][bug.getJ()+1] instanceof Ant) {
            return true;
        }
        else {
            return false;
        }
    }

}