import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

public class Incremental_NDS {
	public static void main(String[] args) {
		Helper helper = new Helper();
		Old_Approach oldAppr = new Old_Approach();
		Approach_Improved apprImpr = new Approach_Improved();
		
        int populationSize = 8;
        int noObjectives = 2;
        
		Point[] population = new Point[populationSize];
        for(int i = 0; i < populationSize; i++) {
            population[i] = new Point(noObjectives, populationSize);
        }

		Point p = new Point(noObjectives, populationSize);
		p.setObjective(1, 0);	p.setObjective(2, 1);
		p.setId(0);
		population[0] = new Point(p); 
       
		p = new Point(noObjectives, populationSize);
		p.setObjective(2, 0);	p.setObjective(1, 1);
		p.setId(1);
		population[1] = new Point(p); 
		
		p = new Point(noObjectives, populationSize);
		p.setObjective(3, 0);	p.setObjective(4, 1);
		p.setId(2);
		population[2] = new Point(p); 
		
		p = new Point(noObjectives, populationSize);
		p.setObjective(4, 0);	p.setObjective(3, 1);
		p.setId(3);
		population[3] = new Point(p); 
		
		p = new Point(noObjectives, populationSize);
		p.setObjective(5, 0);	p.setObjective(6, 1);
		p.setId(4);
		population[4] = new Point(p); 
		
		p = new Point(noObjectives, populationSize);
		p.setObjective(6, 0);	p.setObjective(5, 1);
		p.setId(5);
		population[5] = new Point(p); 
		
		p = new Point(noObjectives, populationSize);
		p.setObjective(7, 0);	p.setObjective(8, 1);
		p.setId(6);
		population[6] = new Point(p); 
		
		p = new Point(noObjectives, populationSize);
		p.setObjective(8, 0);	p.setObjective(7, 1);
		p.setId(7);
		population[7] = new Point(p); 
	   
		System.out.println("The population is...");
        helper.Print(population);
        
        LinkedList<LinkedList<Integer>> OldApproachsetF = new LinkedList<>(); // Used to store the set of fronts for old approach
        LinkedList<LinkedList<Integer>> NewApproachsetF = new LinkedList<>(); // Used to store the set of fronts for proposed approach
        
        OldApproachsetF = helper.sort(population);  
		System.out.println("The initial set of fronts is...");
        helper.Print(OldApproachsetF);
        
        NewApproachsetF = helper.sort(population);
		System.out.println("The initial set of fronts is...");
        helper.Print(NewApproachsetF);
        
        /* Insert offspring point in OldApproachsetF */
        Point x = new Point(noObjectives, populationSize);
        x.setId(populationSize);
        x.setObjective(0.5, 0);		x.setObjective(1.5, 1);

        oldAppr.Insert(OldApproachsetF, x, population);
		System.out.println("The set of fronts after insertion using Old Approach is...");
        helper.Print(OldApproachsetF);
        
        
        
        /* Insert offspring point in NewApproachsetF */
        x = new Point(noObjectives, populationSize);
        x.setId(populationSize);
        x.setObjective(0.5, 0);		x.setObjective(1.5, 1);

        apprImpr.Insert(NewApproachsetF, x, population);
		System.out.println("The set of fronts after insertion using New Approach is...");
        helper.Print(NewApproachsetF);
        
	}
}

class Point {
    private int id;
    private double[] objectives;
    private LinkedList<Integer> List_nondominatedPoints = new LinkedList<>();
    private boolean[] Arr_nondominatedPoints;
    private BitSet Bitset_nondominatedPoints;
              
    public Point () {
        
    }
    
    public Point(int noObjectives, int populationSize) {
        this.objectives = new double[noObjectives];
        this.Arr_nondominatedPoints = new boolean[populationSize];
        this.Bitset_nondominatedPoints = new BitSet(populationSize);
    }
    
    public Point(Point p) {
        this.id = p.id;
        this.objectives = new double[p.objectives.length];
        for(int i = 0; i < p.objectives.length; i++) {
            this.objectives[i] = p.objectives[i];
        }
        this.List_nondominatedPoints = new LinkedList<>();
        this.List_nondominatedPoints.addAll(p.List_nondominatedPoints);
        
        this.Arr_nondominatedPoints = new boolean[p.Arr_nondominatedPoints.length];
        for(int i = 0; i < p.Arr_nondominatedPoints.length; i++) {
            this.Arr_nondominatedPoints[i] = p.Arr_nondominatedPoints[i];
        }
        
        this.Bitset_nondominatedPoints = new BitSet(p.Bitset_nondominatedPoints.size());
        this.Bitset_nondominatedPoints = p.Bitset_nondominatedPoints;
    }
        
    public Point(int id, double[] objectives) {
        this.id = id;
        this.objectives = new double[objectives.length];
        for(int i = 0; i < objectives.length; i++) {
            this.objectives[i] = objectives[i];
        }
    }

    public int getId() {
        return id;
    }

    public double[] getObjectives() {
        return objectives;
    }

    public int getNoObjectives() {
        return objectives.length;
    }
    
    public double getObjective(int index) {
        return objectives[index];
    }

    public LinkedList<Integer> getList_nondominatedPoints() {
        return List_nondominatedPoints;
    }

    public boolean[] getArr_nondominatedPoints() {
        return Arr_nondominatedPoints;
    }

    public BitSet getBitset_nondominatedPoints() {
        return Bitset_nondominatedPoints;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setObjectives(double[] objectives) {
        this.objectives = new double[objectives.length];
        for(int i = 0; i < objectives.length; i++) {
            this.objectives[i] = objectives[i];
        }
    }
    
    public void setObjective(double objective, int index) {
        this.objectives[index] = objective;
    }

    public void setList_nondominatedPoints(LinkedList<Integer> List_nondominatedPoints) {
        this.List_nondominatedPoints = new LinkedList<>();
        this.List_nondominatedPoints.addAll(List_nondominatedPoints);
    }

    public void setList_nondominatedPoint(int id) {
        this.List_nondominatedPoints.add(id);
    }
        
    public void setArr_nondominatedPoints(boolean[] Arr_nondominatedPoints) {
        this.Arr_nondominatedPoints = Arr_nondominatedPoints;
    }

    public void setArr_nondominatedPoint(int index, boolean status) {
        this.Arr_nondominatedPoints[index] = status;
    }

    public void setBitset_nondominatedPoints(BitSet Bitset_nondominatedPoints) {
        this.Bitset_nondominatedPoints = Bitset_nondominatedPoints;
    }

    public void setBitset_nondominatedPoint(int index) {
        this.Bitset_nondominatedPoints.set(index);
    } 
    
    public void setBitset_nondominatedPoint(int index, int status) {
        this.Bitset_nondominatedPoints.set(index, status);
    }     
    
    public int noObjectives(){
        return this.objectives.length;
    }
    
    @Override
    public String toString() {
        return "Point{" + "id=" + id + ", objectives=" + Arrays.toString(objectives) + ", List_nondominatedPoints=" + List_nondominatedPoints + ", Arr_nondominatedPoints=" + Arrays.toString(Arr_nondominatedPoints) + ", Bitset_nondominatedPoints=" + Bitset_nondominatedPoints + '}';
    } 

    /* 1: objectives dominates point
    -1: point dominates objectives
    0: objectives and point is non-dominated */
    public int dominanceRelationship(Point p) {
        boolean flag1 = false;
        boolean flag2 = false;
        int noObjectives = p.objectives.length;
        for(int i = 0; i < noObjectives; i++) {
            if(this.objectives[i] < p.objectives[i]) {
                if(flag2) 
                    return 0;
                if(!flag1) 
                    flag1 = true;
            } else if(this.objectives[i] > p.objectives[i]) {
                if(flag1) 
                    return 0;
                if(!flag2) 
                    flag2 = true;
            }
        }
        if(flag1 == true) {
            return 1;
        } else if(flag2 == true) {
            return -1;
        } else {
            /*--  but in this case non-dominting --*/
            return 0;
        }
    } 
}








class Approach_Improved {
    public void Insert(LinkedList<LinkedList<Integer>> setF, Point offspring, Point population[]) {
        ListIterator<LinkedList<Integer>> itsetF = setF.listIterator();
        
        while(itsetF.hasNext()){
            LinkedList<Integer> currFront = (LinkedList<Integer>) itsetF.next();
            int card = currFront.size();
            boolean checkNextFront = false;
            int count = 0;
            LinkedList<Integer> dominatedPoints = new LinkedList<>(); 
            Iterator itF = currFront.iterator();
            
            while(itF.hasNext()){
                int p = (int) itF.next();
                int domRelation = offspring.dominanceRelationship(population[p]);
                if(domRelation == 1) { // offspring dominates p
                    dominatedPoints.add(population[p].getId());
                    itF.remove();
                } else if(domRelation == -1) { // offspring is dominated by p
                    checkNextFront = true;
                    break;
                } else { // offspring and p are non-dominated
                    count++;
                }
            }
            if(count == card) { // offspring is non-dominated with all the points of the current front
                currFront.add(offspring.getId());
                return;
            }
            if(checkNextFront == true) { // offspring is dominated by p
                continue;
            }
            currFront.add(offspring.getId());
            
            if(itsetF.hasNext() == false) {
                /* Make dominatedPoints a new front at the end */
                setF.add(currFront);
                return;
            } else if(currFront.size() == 1) {
                // increase the domination level...
                itsetF.add(dominatedPoints);
                return;
            } else {
                Update(setF, dominatedPoints, population, itsetF);
                return;
            }
        }
        
        /* Make offspring a new front at the end */
        LinkedList<Integer> F = new LinkedList<>(); 
        F.add(offspring.getId());
        setF.add(F);
    }
    
    public void Update(LinkedList<LinkedList<Integer>> setF, LinkedList<Integer> F, Point population[], 
        ListIterator<LinkedList<Integer>> itsetF) {
        if(itsetF.hasNext() == false) {
            /* Make F a new front at the end */
            setF.add(F);
            return;
        } else {
            BitSet bitsF_nondominatedPoints = Intersection(F, population);

            LinkedList<Integer> nextF = new LinkedList<>(); 
            nextF = itsetF.next();
            Iterator itnextF = nextF.iterator();
 
            int cardnextF = nextF.size();
            
            while(itnextF.hasNext()){
                int p = (int) itnextF.next();
                if(bitsF_nondominatedPoints.get(p) == true) {
                    F.add(p);
                    itnextF.remove();
                }
            }
            if(cardnextF == nextF.size()) {
                itsetF.remove();
                itsetF.add(F);
                itsetF.add(nextF);
                return;
            } else if(nextF.isEmpty()) {
                itsetF.remove();
                itsetF.add(F);
                return;
            } else {
                itsetF.remove();
                itsetF.add(F);
                Update(setF, nextF, population, itsetF);
            }
        }
    }
    
    
    
    public BitSet Intersection(LinkedList<Integer> F, Point population[]) {
        BitSet intersectionBits = (BitSet) population[F.get(0)].getBitset_nondominatedPoints().clone();
        for (int p : F) {
            intersectionBits.and(population[p].getBitset_nondominatedPoints());
        }
        return intersectionBits;
    }
}


class Old_Approach {
    public void Insert(LinkedList<LinkedList<Integer>> setF, Point offspring, Point population[]) {
        ListIterator<LinkedList<Integer>> itsetF = setF.listIterator();
        
        while(itsetF.hasNext()){
            LinkedList<Integer> currFront = (LinkedList<Integer>) itsetF.next();
            int card = currFront.size();
            boolean checkNextFront = false;
            int count = 0;
            LinkedList<Integer> dominatedPoints = new LinkedList<>(); 
            Iterator itF = currFront.iterator();
            
            while(itF.hasNext()){
                int p = (int) itF.next();
                int domRelation = offspring.dominanceRelationship(population[p]);
                if(domRelation == 1) { // offspring dominates p
                    dominatedPoints.add(population[p].getId());
                    itF.remove();
                } else if(domRelation == -1) { // offspring is dominated by p
                    checkNextFront = true;
                    break;
                } else { // offspring and p are non-dominated
                    count++;
                }
            }
            if(count == card) { // offspring is non-dominated with all the points of the current front
                currFront.add(offspring.getId());
                return;
            }
            if(checkNextFront == true) { // offspring is dominated by p
                continue;
            }
            currFront.add(offspring.getId());
            if(itsetF.hasNext() == false) {
                /* Make dominatedPoints a new front at the end */
                setF.add(dominatedPoints);
                return;
            } else if(currFront.size() == 1) { // offspring ddominated all the points in current front
                // increase the domination level...
                itsetF.add(dominatedPoints);
                return;
            } else {
                Update(setF, dominatedPoints, population, itsetF);
                return;
            }
        }
        
        /* Make offspring a new front at the end */
        LinkedList<Integer> F = new LinkedList<>(); 
        F.add(offspring.getId());
        setF.add(F);
    }
    
    public void Update(LinkedList<LinkedList<Integer>> setF, LinkedList<Integer> F, Point population[], 
            ListIterator<LinkedList<Integer>> itsetF) {
        if(itsetF.hasNext() == false) {
            /* Make F a new front at the end */
            setF.add(F);
            return;
        } else {
            int cardF = F.size();
            LinkedList<Integer> nextFront = new LinkedList<>(); 
            nextFront = itsetF.next();
            int cardnextF = nextFront.size();
            
            Iterator itnextF = nextFront.iterator();
            while(itnextF.hasNext()){
                int p = (int) itnextF.next();
                if(Non_Dominance(F, population, p, cardF) == true) {
                    F.add(p);
                    itnextF.remove();
                }
            }
            if(cardnextF == nextFront.size()) {
                itsetF.remove();
                itsetF.add(F);
                itsetF.add(nextFront);
                return;
            } else if(nextFront.isEmpty()) {
                itsetF.remove();
                itsetF.add(F);
                return;
            } else {
                itsetF.remove();
                itsetF.add(F);
                Update(setF, nextFront, population, itsetF);
            }
        }
    }
    
    public boolean Non_Dominance(LinkedList<Integer> F, Point population[], int p, int card) {
        int count = 0;
        for (int q : F) {
            if(count >= card) {
                break;
            }
            int domRelation = population[q].dominanceRelationship(population[p]);
            if(domRelation != 0) {
                return false;
            }
            count++;
        }
        return true;
    }
}




class Helper {
    public LinkedList<LinkedList<Integer>> sort(Point population[]) {
        
        /* Sort using Fast Non-dominated Sort */
        int noObjectives = population[0].getNoObjectives();
        int populationSize = population.length;
        
        LinkedList<LinkedList<Integer>> setF = new LinkedList<>(); 
        
        /* Store the id of dominated points by a particular point */
        LinkedList<Integer>[] dominanceSet = new LinkedList[population.length];
        for(int p = 0; p < populationSize; p++) {
            dominanceSet[p] = new LinkedList<>(); 
        }
        
        /* Store the domination count of a particular point */
        int[] dominationCount = new int[populationSize];
        
        LinkedList<Integer> F = new LinkedList<>(); 
        for(int i = 0; i < populationSize; i++) {
            population[i].setList_nondominatedPoint(i);
            population[i].setArr_nondominatedPoint(i, true);
            population[i].setBitset_nondominatedPoint(i);
            for(int j = i+1; j < populationSize; j++) {
                int domRelation = population[i].dominanceRelationship(population[j]);
                if(domRelation == 1) {
                    dominanceSet[i].add(j);
                    dominationCount[j]++;
                } else if(domRelation == -1) {
                    dominanceSet[j].add(i);
                    dominationCount[i]++;
                } else {
                    population[i].setList_nondominatedPoint(j);
                    population[i].setArr_nondominatedPoint(j, true);
                    population[i].setBitset_nondominatedPoint(j);
                    
                    population[j].setList_nondominatedPoint(i);
                    population[j].setArr_nondominatedPoint(i, true);
                    population[j].setBitset_nondominatedPoint(i);
                }
            }
            if(dominationCount[i] == 0) {
                F.add(i);
            }   
        }
        setF.add(F);
        
        int i = 0;
        while(!F.isEmpty()) {
            LinkedList<Integer> Q = new LinkedList<>(); 
            for (int p : F) {
                for (int q : dominanceSet[p]) {
                    dominationCount[q]--;
                    if(dominationCount[q] == 0) {
                        Q.add(q);
                    }
                }
            }
            if(!Q.isEmpty()) {
                setF.add(Q);
            }
            F = Q;
        }
        return setF;
    }
    
    public void Print(Point population[]) {
        for(int i = 0; i < population.length; i++) {
            System.out.println(population[i]);
        }
    }
    
    public void Print(LinkedList<LinkedList<Integer>> setF) {
        for(LinkedList<Integer> Fx: setF) {
            System.out.println(Fx);
        }
    }
    
    
    
    public void Print(BitSet bits) {
        boolean bit;
        for (int i = 0; i < bits.size(); i++) {
            bit = bits.get(i);
            if (bit)
                System.out.print(1);
            else
                System.out.print(0);
        }
        System.out.println();
    }
    
    public Point GeneratePoint(int noObjectives, int id, int populationSize) {
         Point p = new Point(noObjectives, populationSize);
         double[] objectives = new double[noObjectives];
         int c;
         for(int j = 0; j < noObjectives; j++) {
            c = randInt(0, 100);
            objectives[j] = (double)c / (double)100;
         }
         p.setId(id);
         p.setObjectives(objectives);
         return p; 
     }
     
    /*-- Returns the random number between min and max inclusive min and max both --*/
    public int randInt(int min, int max) {
        // Usually this can be a field rather than a method variable
        Random rand = new Random();
        /*-- nextInt(int n) returns a random integer value in the range of 0 (inclusive) and n (exclusive) --*/
        int randomNum = rand.nextInt(max - min + 1) + min;
        return randomNum;
    }
}


