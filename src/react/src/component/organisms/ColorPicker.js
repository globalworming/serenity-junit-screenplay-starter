import {HslColorPicker} from "react-colorful";
import React from "react";

function getButton(onClick, hex) {
  return <button className={`e2e-do-set-color-${hex}`} style={{height: '1px', width: '1px', margin: 0, fontSize: 0}}
                 onClick={onClick}>#{hex}
  </button>;
}

const ColorPicker = ({setHsl, currentColor}) => <div className={'e2e-pick-color'}>
  {getButton(() => setHsl({h: 0, s: 0, l: 0}), "000000")}
  {getButton(() => setHsl({h: 0, s: 0, l: 100}), "111111")}
  <HslColorPicker

      color={currentColor}
      onChange={(hsl) => {
        setHsl(hsl)
      }}/>
</div>;

export default ColorPicker