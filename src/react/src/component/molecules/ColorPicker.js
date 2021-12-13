import {HslColorPicker} from "react-colorful";
import React from "react";
import E2eSetColorButton from "../atom/E2eSetColorButton";


const ColorPicker = ({setHsl, currentColor}) => <div className={'e2e-pick-color'}>
  <HslColorPicker
      color={currentColor}
      onChange={(hsl) => {
        setHsl(hsl)
      }}/>
  <E2eSetColorButton hex={"000000"} onClick={() => setHsl({h: 0, s: 0, l: 0})}/>
  <E2eSetColorButton hex={"808080"} onClick={() => setHsl({h: 0, s: 0, l: 50})}/>
  <E2eSetColorButton hex={"FFFFFF"} onClick={() => setHsl({h: 0, s: 0, l: 100})}/>
  <hr/>
  <span>{`h: ${currentColor.h} s: ${currentColor.s} l: ${currentColor.l}`}</span>
</div>;

export default ColorPicker