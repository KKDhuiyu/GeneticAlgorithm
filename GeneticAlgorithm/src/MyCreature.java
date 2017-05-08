import cosc343.assig2.Creature;
import java.util.Random;

/**
* The MyCreate extends the cosc343 assignment 2 Creature.  Here you implement
* creatures chromosome and the agent function that maps creature percepts to
* actions.  
*
* @author  
* @version 1.0
* @since   2017-04-05 
*/
public class MyCreature extends Creature {

  // Random number generator
  Random rand = new Random();
private int [] chromosome;
  /* Empty constructor - might be a good idea here to put the code that 
   initialises the chromosome to some random state   
  
   Input: numPercept - number of percepts that creature will be receiving
          numAction - number of action output vector that creature will need
                      to produce on every turn
  */
  public MyCreature(int numPercepts, int numActions) {
      chromosome = new int [2];
      for(int i=0;i<2;i++){
          chromosome[i]=rand.nextInt(10);
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
    float y1,y2,y3,y4,y5,y6,y7,y8,y9,y10,y11;
    int x1,x2,x3,x4,x5,x6,x7,x8,x9;
    int c1,c2,c3,c4,c5,c6,c7,c8,c9;
    x1=percepts[0]; x2=percepts[1]; x3=percepts[2];
     x4=percepts[3]; x5=percepts[4]; x6=percepts[5];
      x7=percepts[6]; x8=percepts[7]; x9=percepts[8];
      c1=chromosome[0]; c2=chromosome[1]; 
//      c3=chromosome[2];
//     c4=chromosome[3]; c5=chromosome[4]; c6=chromosome[5];
//      c7=chromosome[6]; c8=chromosome[7]; c9=chromosome[8];
//      // c1 monster, c2 ripe food, c3 nonripe food, c4 other creatures
//      actions[0]= (c1+c4)*(x1+x2+x3)+c2*x2+c3*x2;
//       actions[1]= (c1+c4)*(x7+x8+x9)+c2*x8+c3*x8;
//       actions[2]=(c1+c4)*(x1+x4+x7)+c2*x4+c3*x4;
//       actions[3]=(c1+c4)*(x3+x6+x8)+c2*x6+c3*x6;
//       actions[4]=(c1+c4)*(x1+x2+x4)+c2*x1+c3*x1;
//       actions[5]=(c1+c4)*(x2+x6+x3)+c2*x3+c3*x3;
//       actions[6]=(c1+c4)*(x4+x7+x8)+c2*x7+c3*x7;
//       actions[7]=(c1+c4)*(x8+x6+x9)+c2*x9+c3*x9;
//       actions[8]=-10000;
//       actions[9]= x5*c2*1000+x5*c5*(100-getEnergy());
//       actions[10]=rand.nextFloat();
//      for (int i = 0; i <= 8; i++) {
//          float temp = 0;
//          for (int j = 0; j < 9; j++) {
//              temp += chromosome[j] * percepts[j]+chromosome[9+j] * percepts[j]+
//                      chromosome[18+j] * percepts[j]+chromosome[27+j] * percepts[j]
//                      +chromosome[36+j] * percepts[j];
//          }
//          // 0-8 monster, 9-17 creatures, 18-26 ripe, 27-35 nonripe,36-44 empty
//          temp -= chromosome[4] * percepts[5]; 
//          temp -= chromosome[13] * percepts[5];
//           temp -= chromosome[22] * percepts[5];
//            temp -= chromosome[31] * percepts[5];
//            temp -= chromosome[40] * percepts[5];
//            actions[i]=temp;
//      }
//    
//actions[9]= 100*chromosome[22]* percepts[5]+chromosome[31] * percepts[5];
//actions[10]=-10;
  for(int i=0;i<numExpectedActions;i++) {
         actions[i]=rand.nextFloat();
      } 
 if(c1>7){
     if(x1==3){
         actions[0]=24;
     }else
     if(x2==3){
         actions[1]=24;
     }else
     if(x3==3){
         actions[2]=24;
     }else
     if(x4==3){
         actions[3]=24;
     }else
     if(x6==3){
         actions[5]=24;
     }else
     if(x7==3){
         actions[6]=24;
     }else
     if(x8==3){
         actions[7]=24;
     }else
     if(x9==3){
         actions[8]=24;
     }
 }
 if(c2>7&& x5>0){
     actions[9]=100;
 }

     
      return actions;
  }
  public int size(){
      return chromosome.length;
  }
  public int getGene(int i){
      return chromosome[i];
  }
  public void setGene(int index, int gene){
      chromosome[index]=gene;
  }
  
}