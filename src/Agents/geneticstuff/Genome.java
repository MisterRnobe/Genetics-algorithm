package Agents.geneticstuff;

import java.util.BitSet;

public class Genome
{
    //TODO Double to bits and bits to Double
    Double[] genes;
    public Genome(Double[] genes)
    {
        this.genes = genes;
    }
    public Genome crossingOver()
    {
        return this;
    }
}
