import React from "react";

const E2eSetColorButton = ({onClick, hex}) => {
  return <button className={`e2e-do-set-color-${hex}`}
                 onClick={onClick}
                 style={{height: '20px', width: '20px', margin: 0, fontSize: 0, background: `#${hex}`}}>#{hex}</button>

}

export default E2eSetColorButton