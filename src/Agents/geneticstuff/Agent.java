package Agents.geneticstuff;

public interface Agent
{
    Genome gen = null;
    String fitnessFunction();
    Genome getGenome();
}
