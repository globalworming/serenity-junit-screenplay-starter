package com.example.screenplay;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ColorSet {
  double color;
  String label;
}
