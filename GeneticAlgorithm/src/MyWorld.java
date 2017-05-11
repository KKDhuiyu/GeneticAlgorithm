import cosc343.assig2.World;
import cosc343.assig2.Creature;
import java.util.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
/**
* The MyWorld extends the cosc343 assignment 2 World.  Here you can set 
* some variables that control the simulations and override functions that
* generate populations of creatures that the World requires for its
* simulations.
*
* @author  
* @version 1.0
* @since   2017-04-05 
*/
public class MyWorld extends World {
 
  /* Here you can specify the number of turns in each simulation
   * and the number of generations that the genetic algorithm will 
   * execute.
  */
    private final int _numTurns = 100;
    private final int _numGenerations = 2000;
    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.0015;
private static int generationsCount=2000;
private static ArrayList<Integer> averageFitnessList= new ArrayList<>();
 Random rand = new Random();
  
  /* Constructor.  
   
     Input: griSize - the size of the world
            windowWidth - the width (in pixels) of the visualisation window
            windowHeight - the height (in pixels) of the visualisation window
            repeatableMode - if set to true, every simulation in each
                             generation will start from the same state
            perceptFormat - format of the percepts to use: choice of 1, 2, or 3
  */
  public MyWorld(int gridSize, int windowWidth, int windowHeight, boolean repeatableMode, int perceptFormat) {   
      // Initialise the parent class - don't remove this
      super(gridSize, windowWidth,  windowHeight, repeatableMode, perceptFormat);

      // Set the number of turns and generations
      this.setNumTurns(_numTurns);
      this.setNumGenerations(_numGenerations);
      
      
  }
 
  /* The main function for the MyWorld application

  */
  public static void main(String[] args) {
     // Here you can specify the grid size, window size and whether torun
     // in repeatable mode or not
     int gridSize = 24;
     int windowWidth =  1600;
     int windowHeight = 900;
     boolean repeatableMode = false;
     
      /* Here you can specify percept format to use - there are three to
         chose from: 1, 2, 3.  Refer to the Assignment2 instructions for
         explanation of the three percept formats.
      */
     int perceptFormat = 3;     
     
     // Instantiate MyWorld object.  The rest of the application is driven
     // from the window that will be displayed.
     MyWorld sim = new MyWorld(gridSize, windowWidth, windowHeight, repeatableMode, perceptFormat);
  }
  

  /* The MyWorld class must override this function, which is
     used to fetch a population of creatures at the beginning of the
     first simulation.  This is the place where you need to  generate
     a set of creatures with random behaviours.
  
     Input: numCreatures - this variable will tell you how many creatures
                           the world is expecting
                            
     Returns: An array of MyCreature objects - the World will expect numCreatures
              elements in that array     
  */  
  @Override
  public MyCreature[] firstGeneration(int numCreatures) {

    int numPercepts = this.expectedNumberofPercepts();
    int numActions = this.expectedNumberofActions();
      
    // This is just an example code.  You may replace this code with
    // your own that initialises an array of size numCreatures and creates
    // a population of your creatures
    MyCreature[] population = new MyCreature[numCreatures];
    for(int i=0;i<numCreatures;i++) {
        population[i] = new MyCreature(numPercepts, numActions);     
    }
    return population;
  }
  
  /* The MyWorld class must override this function, which is
     used to fetch the next generation of the creatures.  This World will
     proivde you with the old_generation of creatures, from which you can
     extract information relating to how they did in the previous simulation...
     and use them as parents for the new generation.
  
     Input: old_population_btc - the generation of old creatures before type casting. 
                              The World doesn't know about MyCreature type, only
                              its parent type Creature, so you will have to
                              typecast to MyCreatures.  These creatures 
                              have been simulated over and their state
                              can be queried to compute their fitness
            numCreatures - the number of elements in the old_population_btc
                           array
                        
                            
  Returns: An array of MyCreature objects - the World will expect numCreatures
           elements in that array.  This is the new population that will be
           use for the next simulation.  
  */  
  @Override
  public MyCreature[] nextGeneration(Creature[] old_population_btc, int numCreatures) {
    // Typcast old_population of Creatures to array of MyCreatures
     MyCreature[] old_population = (MyCreature[]) old_population_btc;
     // Create a new array for the new population
     MyCreature[] new_population = new MyCreature[numCreatures];
     
     // Here is how you can get information about old creatures and how
     // well they did in the simulation
     float avgLifeTime=0f;
     int nSurvivors = 0;
     for(MyCreature creature : old_population) {
        // The energy of the creature.  This is zero if creature starved to
        // death, non-negative oterhwise.  If this number is zero, but the 
        // creature is dead, then this number gives the enrgy of the creature
        // at the time of death.
        int energy = creature.getEnergy();

        // This querry can tell you if the creature died during simulation
        // or not.  
        boolean dead = creature.isDead();
        
        if(dead) {
           // If the creature died during simulation, you can determine
           // its time of death (in turns)
           int timeOfDeath = creature.timeOfDeath();
           avgLifeTime += (float) timeOfDeath;
        } else {
           nSurvivors += 1;
           avgLifeTime += (float) _numTurns;
        }
     }

     // Right now the information is used to print some stats...but you should
     // use this information to access creatures fitness.  It's up to you how
     // you define your fitness function.  You should add a print out or
     // some visual display of average fitness over generations.
     avgLifeTime /= (float) numCreatures;
     System.out.println("Simulation stats:");
     System.out.println("  Survivors    : " + nSurvivors + " out of " + numCreatures);
     System.out.println("  Avg life time: " + avgLifeTime + " turns");


     
     // Having some way of measuring the fitness, you should implement a proper
     // parent selection method here and create a set of new creatures.  You need
     // to create numCreatures of the new creatures.  If you'd like to have
     // some elitism, you can use old creatures in the next generation.  This
     // example code uses all the creatures from the old generation in the
     // new generation.
     int [] populationFitScore= calculateFitness(old_population);
     int averageScore= getAverageFitness(populationFitScore);
     int medianScore= median(populationFitScore);
     averageFitnessList.add(averageScore);
     System.out.println("  Avg fitness: " + averageScore );
     generationsCount--;
     if(generationsCount==0){ // draw the graph ate the end
         LineChart_AWT chart = new LineChart_AWT(
         "Average Fitness" ,
         "Average Fitness vs Generations");

      chart.pack( );
      RefineryUtilities.centerFrameOnScreen( chart );
      chart.setVisible( true );
     }
     ArrayList<MyCreature> copy = new ArrayList<>();
     
     for(int i=numCreatures-1;i>=0; i--) {
         if(populationFitScore[i]>=medianScore){ // elitism
            copy.add(old_population[i]);
         }
     }
     
     for(int i=0;i<numCreatures; i++) {
         MyCreature indiv1=null;
         MyCreature indiv2=null;
         do{
             if(indiv1==null){
             indiv1 = copy.get(rand.nextInt(copy.size()));
             }
             
             if(indiv2==null){
             indiv2 = copy.get(rand.nextInt(copy.size()));
             }
         }while(indiv1==null || indiv2==null);
         new_population[i] = crossover(indiv1, indiv2);
       
     }
     for(int i=0;i<numCreatures; i++) {
         mutate(new_population[i]);
     }
     
     // Return new population of cratures.
     return new_population;
  }
   public static int[] calculateFitness(MyCreature[] population) {
       int[] fitness;
        fitness = new int[population.length];

        for (int i = 0; i < fitness.length; i++) {
            int score=0;
            MyCreature individual = population[i];
            if(individual.isDead()){
                score-=20*(100-individual.timeOfDeath());
                if(individual.isSick()){
                    score-=20*(100-individual.timeOfDeath());
                }
                score+= individual.getEnergy()*(individual.timeOfDeath()-5);
            }else{
                 score+= (5+individual.getEnergy())*100;
            }
            fitness[i]=score;
        }
        return fitness;
    }
  private MyCreature crossover(MyCreature indiv1, MyCreature indiv2) {
      int numPercepts = this.expectedNumberofPercepts();
    int numActions = this.expectedNumberofActions();
        MyCreature child = new MyCreature(numPercepts,numActions);
        // Loop through genes
        for (int i = 0; i < indiv1.size(); i++) {
            // Crossover
            if (Math.random() <= uniformRate) {
                child.setGene(i, indiv1.getGene(i));
            } else {
                child.setGene(i, indiv2.getGene(i));
            }
        }
        return child;
    }
   private  void mutate(MyCreature indiv) {
        // Loop through genes
        for (int i = 0; i < indiv.size(); i++) {
            if (Math.random() <= mutationRate) {
                // Create random gene
                int gene =  rand.nextInt(60)-10;
                indiv.setGene(i, gene);
            }
        }
    }

    public static int getAverageFitness(int[] fitness) {
        int score = 0;
         for (int i = 0; i < fitness.length; i++) {
             score+=fitness[i];
         }
        return score/fitness.length;
    }
   
    public static int median(int[] m) {
    int middle = m.length/2;
    if (m.length%2 == 1) {
        return m[middle];
    } else {
        return (m[middle-1] + m[middle]) / 2;
    }
}
    public class LineChart_AWT extends ApplicationFrame {

   public LineChart_AWT( String applicationTitle , String chartTitle ) {
      super(applicationTitle);
      JFreeChart lineChart = ChartFactory.createLineChart(
         chartTitle,
         "Generations","Average fitness",
         createDataset(),
         PlotOrientation.VERTICAL,
         true,true,false);
         
      ChartPanel chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      setContentPane( chartPanel );
   }

   private DefaultCategoryDataset createDataset( ) {
      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      for(int i=0;i<averageFitnessList.size();i++){
          dataset.addValue(averageFitnessList.get(i), "fitness", ""+i);
      }
      
      return dataset;
   }
   
}
}