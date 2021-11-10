package com.example.screenplay;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ColorSet {
  double color;
  String label;
}