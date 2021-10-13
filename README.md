# Predator-Prey-Simulation

The goal for this project is to create a simple 2D predator–preysimulation. In this simulation, the prey is ants, and the predators are doodlebugs. Thesecritters (i.e. both species) live in a world composed of a20×20grid of cells. Only one crittermay occupy a cell at a time. The grid is enclosed, so a critter is not allowed to move off theedges of the grid. Time is simulated in time steps. Each critter performs some action everytime step.


The ants behave according to the following model

• Move. Every time step, randomly try to move up, down, left, or right. If the cell in theselected direction is occupied or would move the ant off the grid, then the ant stays inthe current cell.

• Breed. If an ant survives for three time steps, then at the end of the third time step(i.e., after moving), the ant will breed. This is simulated by creating a new ant in anadjacent (up, down, left, or right) cell that is empty. If there is no empty cell available,no breeding occurs. Once an offspring is produced, the ant cannot produce an offspringuntil three more time steps have elapsed. The maximum number of spawned ants is10 per ant.


The doodlebugs behave according to the following model:

• Move. Every time step, if there is an adjacent cell (up, down, left, or right) occupiedby an ant, then the doodlebug will move to that cell and eat the ant. Otherwise, thedoodlebug moves according to the same rules as the ant. Note that a doodlebug cannoteat other doodlebugs.

• Breed. If a doodlebug survives for eight time steps, then at the end of the time step, itwill spawn off a new doodlebug in the same manner as the ant. The maximum numberof spawned doodlebugs is 10 per doodlebug.

• Starve. If a doodlebug has not eaten an ant within the last three time steps, then atthe end of the third time step, it will starve and die. The doodlebug should then beremoved from the grid of cells.During one turn, all the doodlebugs should move before the ants. Write a program toimplement this simulation and draw the world using ASCII characters of “o” for an ant and“X” for a doodlebug. Below is an example of an8×8grid with 3 ants and 5 doodlebugs.


![image](https://user-images.githubusercontent.com/77823772/137099069-05576a23-8a59-4af2-aef8-574bb0c8ca4e.png)


Create a class namedOrganismthat encapsulates basic data common to both ants anddoodlebugs. This class should have an abstract method namedmovethat is defined (overridden)in the derived classesAntandDoodlebug. You probably need additional data structuresto keep track of which critters have moved. Initialize the world with 5 doodlebugs and 100ants (at random). After each time step, prompt the user to press Enter to move to the nexttime step. You should see a cyclical pattern between the population of predators and prey,although random perturbations may lead to the elimination of one or both species. Notethat the simulation end only if the user enters END instead of just pressing enter.
