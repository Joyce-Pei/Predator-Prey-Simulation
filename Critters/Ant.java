/*
 * Ant.java
 */

/**
 * CS5004 Assignment 7: Ant
 * This class is derived from Organism, it can keep track of an ant,
 * its location, numbers of time steps and offspring, whether it has been moved, etc.
 * 
 * @Yuehan Pei
 * @since 2020-06-28
 */ 

package Critters;

public class Ant extends Organism {
    public Ant (int step, int offspring, int i, int j, boolean moved) {
        this.step = step;
        this.offspring = offspring;
        this.i = i;
        this.j = j;
        this.moved = false;
    }

    //default constructor
    public Ant() {
        this.step = 0;
        this.offspring = 0;
        this.i = 0;
        this.j = 0;
        this.moved = false;
    }

    //copy constructor
    public Ant(Ant other) {
        this(other.step, other.offspring, other.i, other.j, other.moved);
    }

    private int step;
    private int offspring;
    private int i;
    private int j;
    private boolean moved;
    public static final int STEPS_TO_BREED = 3;
    public static final int MAXMUM_I = 20;
    public static final int MAXMUM_J = 20;

    //getters
    public int getI() {
        return this.i;
    }

    public int getJ() {
        return this.j;
    }

    public int getStep() {
        return this.step;
    }

    public int getOffspring() {
        return this.offspring;
    }

    public boolean getMoved() {
        return this.moved;
    }

    //setters
    public void setMoved(boolean moved) {
        this.moved = moved;
    } 

    public void seStep(int step) {
        this.step = step;
    }

    @Override
    //This method tells an ant to move to which direction
    public void move(int x) {
        if (x == 0) {
            this.i -= 1;
        }
        else if (x == 1) {
            this.i += 1;
        }
        else if (x == 2) {
            this.j -= 1;
        }
        else {
            this.j += 1;
        }
        this.moved = true;
    }
    
    public void breed() {
        this.offspring += 1;
        this.step = 0;
    }

    public String toString() {
        return "o";
    }

    
}