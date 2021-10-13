/*
 * Doodlebug.java
 */

/**
 * CS5004 Assignment 7: Doodlebug
 * This class is derived from Organism, it can keep track of a doodlebug,
 * its location, numbers of time steps and offspring, how long it has starved,
 * whether it has been moved, etc.
 * 
 * @Yuehan Pei
 * @since 2020-06-28
 */ 

package Critters;

public class Doodlebug extends Organism {
    public Doodlebug (int step, int offspring, int starve, int i, int j, boolean moved) {
        this.step = step;
        this.offspring = offspring;
        this.i = i;
        this.j = j;
        this.starve = starve;
        this.moved = moved;
    }

    //default constructor
    public Doodlebug() {
        this.step = 0;
        this.offspring = 0;
        this.starve = 0;
        this.i = 0;
        this.j = 0;
        this.moved = false;
    }

    //copy constructor
    public Doodlebug(Doodlebug other) {
        this(other.step, other.offspring, other.starve, other.i, other.j, other.moved);
    }

    private int step;
    private int offspring;
    private int i;
    private int j;
    private int starve;
    private boolean moved;
    public static final int STEPS_TO_BREED = 8;
    public static final int STEPS_TO_STARVE = 3;

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

    public int getStarve() {
        return this.starve;
    }
    
    //setters
    public void setStep(int step) {
        this.step = step;
    }

    public void setStarve(int starve) {
        this.starve = starve;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    } 

    @Override
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

    public void eat() {
        this.starve = 0;        
    }

    public String toString() {
        return "X";
    }
    
}