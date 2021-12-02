package com.example.neuralnet.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
public class InferenceResults {

  private final List<InferenceResult> inferenceResults;

}
