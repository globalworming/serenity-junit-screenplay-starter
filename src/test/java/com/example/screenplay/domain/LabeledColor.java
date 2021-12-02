package com.example.screenplay.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class LabeledColor {
  String color;
  String label;
}
