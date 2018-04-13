package Agents;

import Agents.utils.NeuralNetwork;
import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStart;
import io.jenetics.util.Factory;
import io.jenetics.util.ISeq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class GeneticAlgorithm
{
    public static int min = -10;
    public static int max = 10;
    public static int generationsLimit = 5000;
    private  static Double fitnessFunc(Genotype<DoubleGene> genotype)
    {

        return Simulation.getInstance().getAgent(genotypeToDouble(genotype)).fitnessFunction();
    }
    private static double[] genotypeToDouble(Genotype<DoubleGene> genotype)
    {
        return genotype.stream().map(doubleGenes ->
                doubleGenes.stream()
                        .map(gene-> gene.getAllele())).
                flatMap(doubleStream -> doubleStream).mapToDouble(value -> value).toArray();
    }
    public static void doEngine(int populationSize, int... neurons)
    {
        Engine<DoubleGene,Double> engine = Engine.builder(GeneticAlgorithm::fitnessFunc,getFactory(neurons))
                .optimize(Optimize.MINIMUM)
                .build();
        EvolutionStart<DoubleGene,Double> start =
                EvolutionStart.of(createPopulation(
                        populationSize,
                        getFactory(neurons)),
                        1);
        for(int i = 0; i < generationsLimit; i++)
        {
            start.getPopulation()
                    .map(Phenotype::getGenotype)
                    .map(GeneticAlgorithm::genotypeToDouble)
                    .forEach(geneArray->Simulation.getInstance().addAgent(geneArray,neurons));
            //TODO simulation
            EvolutionResult<DoubleGene,Double> result = engine.evolve(start);
            start = result.toEvolutionStart();

            Simulation.getInstance().clearAgents();
        }
        start.getPopulation().forEach(System.out::println);
    }
    public static Factory<Genotype<DoubleGene>> getFactory(int... neurons)
    {
        try
        {
            if (neurons.length < 2)
            {
                throw new Exception("Ty durak ili qaq?");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        ArrayList<DoubleChromosome> list = new ArrayList<>(neurons.length);
        for (int i = 0; i < neurons.length - 1; i++)
        {
            int layerOne = neurons[i] + 1;
            int layerTwo = neurons[i+1];
            list.add(DoubleChromosome.of(min,max,layerOne*layerTwo));
        }
        return Genotype.of(list);
    }
    public static void main(String[] args)
    {
        NeuralNetwork nn = new NeuralNetwork(genotypeToDouble(getFactory(2,2,2).newInstance()),2,2,2);
        System.out.println(toString(nn.getThetas(0)));
        System.out.println(toString(nn.getThetas(1  )));
    }
    private static ISeq<Phenotype<DoubleGene,Double>> createPopulation(int quantity,Factory<Genotype<DoubleGene>> factory)
    {
        ArrayList<Phenotype<DoubleGene,Double>> population = new ArrayList<>();
        for (int i = 0; i < quantity; i++)
        {
            population.add(Phenotype.of(factory.newInstance(),1,GeneticAlgorithm::fitnessFunc));
        }
        //population.stream().
        return ISeq.of(population);
    }

    private static String toString(double[] d)
    {
        return Arrays.stream(d).boxed().map(Object::toString).collect(Collectors.joining(", "));
    }
}
