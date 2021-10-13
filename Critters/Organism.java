/*
 * Organism.java
 */

/**
 * CS5004 Assignment 7: Organism
 * This abstract class is the base class of Ant and Doodlebug
 * 
 * @Yuehan Pei
 * @since 2020-06-28
 */ 

package Critters;

public abstract class Organism {
    
    public abstract int getI();
    public abstract int getJ();
    public abstract int getStep();
    public abstract int getOffspring();

    public abstract void move(int x);
    public abstract void breed();


}