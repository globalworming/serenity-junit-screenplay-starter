package com.example.screenplay;

import com.example.neuralnet.component.HslColor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class LabeledColor {
  HslColor hslColor;
  String label;
}
