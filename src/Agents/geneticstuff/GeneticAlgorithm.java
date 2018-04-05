package Agents.geneticstuff;

import java.util.*;

public class GeneticAlgorithm
{
    public void doCrossOver(){}
    public void doSelection(List<Agent> population)
    {
        //TODO Outbreeding
        //TODO uniform crossover
        //TODO Hamming distance
        population.sort(Comparator.comparing(Agent::fitnessFunction));

    }
    public void doMutation(){}

    public static void main(String[] args)
    {
        double[] values = {12.3,34.3,546,65.0,7.45};
        int nbits = 0;
        ArrayList<BitSet> bits = new ArrayList<>();
        ArrayList<String> bitsStrings = new ArrayList<>();
        for (Double i :values)
        {
            long l = Double.doubleToLongBits(i);
            bitsStrings.add(Long.toBinaryString(l));
            bits.add(BitSet.valueOf(new long[]{l}));
        }
        bitsStrings.forEach(System.out::println);
        bits.forEach(bitSet -> bitSet.toByteArray());
        //BitSet.
        //BitSet.valueOf(Arrays.stream(values).asLongStream().toArray());
        //int nbits = ;
        //Integer.toBinaryString(23);
//        Genome govno = new Genome()
//        System.out.println();
    }
    //public static void
}
