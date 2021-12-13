package com.example.neuralnet.domain;

import com.example.neuralnet.component.HslColor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class LabeledHslColor {
  HslColor hslColor;
  String label;
}
