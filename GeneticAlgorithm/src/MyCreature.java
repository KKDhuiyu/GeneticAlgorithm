
import cosc343.assig2.Creature;
import java.util.Random;

/**
 * The MyCreate extends the cosc343 assignment 2 Creature. Here you implement
 * creatures chromosome and the agent function that maps creature percepts to
 * actions.
 *
 * @author
 * @version 1.0
 * @since 2017-04-05
 */
public class MyCreature extends Creature {

    // Random number generator
    Random rand = new Random();
    private int[] chromosome;

    /* Empty constructor - might be a good idea here to put the code that 
   initialises the chromosome to some random state   
  
   Input: numPercept - number of percepts that creature will be receiving
          numAction - number of action output vector that creature will need
                      to produce on every turn
     */
    public MyCreature(int numPercepts, int numActions) {
        chromosome = new int[100];
        for (int i = 0; i < 100; i++) {
            chromosome[i] = rand.nextInt(60) - 10;
        }
    }

    /* This function must be overridden by MyCreature, because it implements
     the AgentFunction which controls creature behavoiur.  This behaviour
     should be governed by a model (that you need to come up with) that is
     parameterise by the chromosome.  
  
     Input: percepts - an array of percepts
            numPercepts - the size of the array of percepts depend on the percept
                          chosen
            numExpectedAction - this number tells you what the expected size
                                of the returned array of percepts should bes
     Returns: an array of actions 
     */
    @Override
    public float[] AgentFunction(int[] percepts, int numPercepts, int numExpectedActions) {

        // This is where your chromosome gives rise to the model that maps
        // percepts to actions.  This function governs your creature's behaviour.
        // You need to figure out what model you want to use, and how you're going
        // to encode its parameters in a chromosome.
        // At the moment, the actions are chosen completely at random, ignoring
        // the percepts.  You need to replace this code.
        float actions[] = new float[numExpectedActions];

        for (int i = 0; i < numExpectedActions; i++) {
            for (int j = 0; j < 9; j++) {
                actions[i] += chromosome[j + 4 * (i) + percepts[j]] * percepts[j];

            }

        }

        actions[9] += Math.pow(percepts[4], 9.0) * chromosome[98] * chromosome[97];
        actions[10] = 9*chromosome[96];
        for (int i = 0; i < numExpectedActions - 1; i++) {
            actions[10] -= actions[i];
        }

        return actions;
    }

    public int size() {
        return chromosome.length;
    }

    public int getGene(int i) {
        return chromosome[i];
    }

    public void setGene(int index, int gene) {
        chromosome[index] = gene;
    }

}
