package com.example.neuralnet.component;

import com.example.neuralnet.domain.LabeledNeuron;
import com.example.neuralnet.domain.NeuralNet;
import com.example.neuralnet.domain.NeuralNetModel;
import com.example.neuralnet.domain.Neuron;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.example.neuralnet.component.ModelBuilder.Type.DEFAULT;
import static com.example.neuralnet.component.ModelBuilder.Type.INPUT;
import static com.example.neuralnet.component.ModelBuilder.Type.OUTPUT;

public class ModelBuilder {
  public static NeuralNetModel build(NeuralNet neuralNet) {
    return NeuralNetModel.builder()
        .nodes(buildNodes(neuralNet))
        .edges(buildEdges(neuralNet))
        .build();
  }

  private static List<NeuralNetModel.Node> buildNodes(NeuralNet neuralNet) {
    AtomicInteger layerIndex = new AtomicInteger();
    val nodes = new ArrayList<NeuralNetModel.Node>();
    nodes.addAll(buildNodes(layerIndex.getAndIncrement(), neuralNet.getInputNeurons(), INPUT));
    neuralNet
        .getHiddenLayers()
        .forEach(layer -> nodes.addAll(buildNodes(layerIndex.getAndIncrement(), layer, DEFAULT)));
    nodes.addAll(buildNodes(layerIndex.getAndIncrement(), neuralNet.getOutputNeurons(), OUTPUT));
    return nodes;
  }

  private static List<NeuralNetModel.Edge> buildEdges(NeuralNet neuralNet) {
    return neuralNet.getWires().stream()
        .map(
            wire ->
                NeuralNetModel.Edge.builder()
                    .uuid(wire.getUuid())
                    .source(wire.getSource().getUuid())
                    .target(wire.getTarget().getUuid())
                    .weight(wire.getWeight())
                    .build())
        .collect(Collectors.toList());
  }

  private static List<NeuralNetModel.Node> buildNodes(
      int layerIndex, List<? extends Neuron> inputNeurons, Type type) {
    List<NeuralNetModel.Node> list = new ArrayList<>();
    for (int i = 0; i < inputNeurons.size(); i++) {
      NeuralNetModel.Node node = buildNode(inputNeurons.get(i), i, layerIndex, type);
      list.add(node);
    }
    return list;
  }

  private static NeuralNetModel.Node buildNode(Neuron neuron, int index, int layer, Type type) {
    NeuralNetModel.Node.NodeBuilder builder =
        NeuralNetModel.Node.builder().uuid(neuron.getUuid()).layer(layer).index(index);
    if (neuron instanceof LabeledNeuron) {
      builder
          .type(type.toString().toLowerCase())
          .activation(((LabeledNeuron) neuron).getActivation())
          .label(((LabeledNeuron) neuron).getLabel());
    }
    return builder.build();
  }

  public enum Type {
    DEFAULT,
    INPUT,
    OUTPUT
  }
}
