package Agents.geneticstuff;

public class Genome
{
    //TODO Double to bits and bits to Double
    private Double[] genes;
    public Genome(Double[] genes)
    {
        this.genes = genes;
    }
    public Genome crossingOver()
    {
        return this;
    }
}
