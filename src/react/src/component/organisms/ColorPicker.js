import {HslColorPicker} from "react-colorful";
import React from "react";


const ColorPicker = ({setHsl, currentColor}) => <div className={'e2e-pick-color'}>
  <HslColorPicker

      color={currentColor}
      onChange={(hsl) => {
        setHsl(hsl)
      }}/>
</div>;

export default ColorPicker