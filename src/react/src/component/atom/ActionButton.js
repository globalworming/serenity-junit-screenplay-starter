import React from "react";

const ActionButton = ({onClick, children}) => {
  return <button
      className={'e2e-do-reward-for-black'}
      onClick={onClick}>
    {children}
  </button>
}

export default ActionButton