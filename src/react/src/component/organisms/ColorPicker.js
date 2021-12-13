import {HslColorPicker} from "react-colorful";
import React from "react";


const ColorPicker = ({setHsl, currentColor}) => <div className={'e2e-pick-color'}>
  <HslColorPicker

      color={currentColor}
      onChange={(hsl) => {
        setHsl(hsl)
      }}/>
  <span>{`h: ${currentColor.h} s: ${currentColor.s} l: ${currentColor.l}`}</span>
</div>;

export default ColorPicker