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
    nodes.addAll(buildNodes(layerIndex.getAndIncrement(), neuralNet.getInputNeurons()));
    neuralNet
        .getHiddenLayers()
        .forEach(layer -> nodes.addAll(buildNodes(layerIndex.getAndIncrement(), layer)));
    nodes.addAll(buildNodes(layerIndex.getAndIncrement(), neuralNet.getOutputNeurons()));
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
      int layerIndex, List<? extends Neuron> inputNeurons) {
    List<NeuralNetModel.Node> list = new ArrayList<>();
    for (int i = 0; i < inputNeurons.size(); i++) {
      NeuralNetModel.Node node = buildNode(inputNeurons.get(i), i, layerIndex);
      list.add(node);
    }
    return list;
  }

  private static NeuralNetModel.Node buildNode(Neuron neuron, int index, int layer) {
    NeuralNetModel.Node.NodeBuilder builder =
        NeuralNetModel.Node.builder().uuid(neuron.getUuid()).layer(layer).index(index);
    if (neuron instanceof LabeledNeuron) {
      builder
          .activation(((LabeledNeuron) neuron).getActivation())
          .label(((LabeledNeuron) neuron).getLabel());
    }
    return builder.build();
  }
}
