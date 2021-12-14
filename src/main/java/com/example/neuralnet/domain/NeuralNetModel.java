package com.example.neuralnet.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class NeuralNetModel {
  private final List<Node> nodes;
  private final List<Edge> edges;

  @Builder
  @Getter
  public static class Node {
    private final UUID uuid;
    private final String label;
    private final double activation;
    private final int layer;
    private final int index;
    private String type;
  }

  @Builder
  @Getter
  public static class Edge {
    private final UUID uuid;
    private final UUID source;
    private final UUID target;
    private final double weight;
  }
}
