package Agents.geneticstuff;

import io.jenetics.*;
import io.jenetics.engine.*;
import io.jenetics.internal.collection.ArrayISeq;
import io.jenetics.internal.collection.ArraySeq;
import io.jenetics.util.DoubleRange;
import io.jenetics.util.Factory;
import io.jenetics.util.ISeq;

import java.util.ArrayList;

import static java.lang.Math.PI;
import static java.lang.Math.cos;

public class Test
{
    private static final double A = 10;
    private static final double R = 5.12;
    private static final int N = 2;
    private  static Double fitnessFunc(Genotype<DoubleGene> genotype)
    {
        return rastrFunc(genotype.getChromosome().getGene().getAllele());//genotype.stream().map(doubleGenes -> doubleGenes.getGene().getAllele())
                //.mapToDouble(Test::rastrFunc).sum();
    }
    private static double rastrFunc(double x)
    {
        return x*x - A*cos(2.0*PI*x);
    }
    public static void main(String[] args)
    {
        Factory<Genotype<DoubleGene>> factory = Genotype.of(
                DoubleChromosome.of(-R,R,1)
        );
        Phenotype.of(factory.newInstance(),1,Test::fitnessFunc);
        Engine engine = Engine.builder(Test::fitnessFunc,factory).optimize(Optimize.MINIMUM).build();
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
            population.add(Phenotype.of(factory.newInstance(),1,Test::fitnessFunc));
        }
        return ISeq.of(population);
    }
}
