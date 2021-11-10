package com.example.screenplay.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ColorSet {
  String color;
  String label;
}
