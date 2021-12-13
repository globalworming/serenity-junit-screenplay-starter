import {HslColorPicker} from "react-colorful";
import React from "react";

const ColorPicker = ({setHsl, currentColor}) => <div className={'e2e-pick-color'}>
  <button className="e2e-do-set-color-000000" style={{height: '1px', width: '1px', margin: 0, fontSize: 0}}
          onClick={() => setHsl({h: 0, s: 0, l: 0})}>#000000
  </button>
  <HslColorPicker

      color={currentColor}
      onChange={(hsl) => {
        setHsl(hsl)
      }}/>
</div>;

export default ColorPicker