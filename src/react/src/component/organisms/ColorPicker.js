import Button from "react-bootstrap/Button";
import {HslColorPicker} from "react-colorful";
import React from "react";

const ColorPicker = ({setHsl, currentColor}) => <div className={'e2e-pick-color'}>
  <Button className="e2e-do-set-color-000000"
          onClick={() => setHsl({h: 0, s: 0, l: 0})}>#000000</Button>
  <HslColorPicker

      color={currentColor}
      onChange={(hsl) => {
        setHsl(hsl)
      }}/>
</div>;

export default ColorPicker