package com.example.neuralnet.component;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class HslColor {
  public static final HslColor WHITE = new HslColor(0, 0, 1d);
  public static final HslColor BLACK = new HslColor(0, 0, 0);
  public static final HslColor GRAY = new HslColor(0, 0, .5);
  private final int hue;
  private final double saturation;
  private final double lightness;

  public HslColor(int hue, double saturation, double lightness) {
    if (hue < 0 || hue > 255) {
      throw new IllegalArgumentException();
    }
    if (saturation < 0. || saturation > 1.) {
      throw new IllegalArgumentException();
    }
    if (lightness < 0. || lightness > 1.) {
      throw new IllegalArgumentException();
    }

    this.hue = hue;
    this.saturation = saturation;
    this.lightness = lightness;
  }
}
