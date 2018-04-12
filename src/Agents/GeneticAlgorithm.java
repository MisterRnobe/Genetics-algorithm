package Agents;

import io.jenetics.*;
import io.jenetics.engine.*;
import io.jenetics.util.Factory;
import io.jenetics.util.ISeq;

import java.util.ArrayList;

public class GeneticAlgorithm
{
    private  static Double fitnessFunc(Genotype<DoubleGene> genotype)
    {
        return 0.0;
    }
    public static double[] genotypeToDouble(Genotype<DoubleGene> genotype)
    {
        return genotype.stream().map(doubleGenes ->
        {
            return doubleGenes.stream()
                    .map(gene->
                    {
                        return gene.getAllele();
                    });
        }).flatMap(doubleStream -> doubleStream).mapToDouble(value -> value).toArray();
    }
    public static void doEngine()
    {
        Factory<Genotype<DoubleGene>> factory = Genotype.of(
                DoubleChromosome.of(-1,1,1)
        );
        Phenotype.of(factory.newInstance(),1,GeneticAlgorithm::fitnessFunc);
        Engine<DoubleGene,Double> engine = Engine.builder(GeneticAlgorithm::fitnessFunc,factory).optimize(Optimize.MINIMUM).build();
        EvolutionStart<DoubleGene,Double> start = EvolutionStart.of(createPopulation(20,factory),1); //TODO inititalize
        for(int i = 0; i < 50000; i++)
        {
            //TODO simulation
            start = engine.evolve(start).toEvolutionStart();
        }
        start.getPopulation().forEach(System.out::println);

    }
    private static ISeq<Phenotype<DoubleGene,Double>> createPopulation(int quantity,Factory<Genotype<DoubleGene>> factory)
    {
        //ISeq<Phenotype<DoubleGene,Double>> pupulation = new ArrayISeq<>();
        //ISeq.of()
        ArrayList<Phenotype<DoubleGene,Double>> population = new ArrayList<>();
        for (int i = 0; i < quantity; i++)
        {
            population.add(Phenotype.of(factory.newInstance(),1,GeneticAlgorithm::fitnessFunc));
        }
        return ISeq.of(population);
    }
}
