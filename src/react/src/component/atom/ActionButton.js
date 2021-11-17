import React from "react";

const ActionButton = ({onClick, children}) => {
  return <button
      className={'e2e-do-reward-for-black'}
      onClick={onClick()}>
    action: {children}
  </button>
}

export default ActionButton