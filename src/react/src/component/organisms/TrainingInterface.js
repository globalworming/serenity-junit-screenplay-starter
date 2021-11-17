import ActionButton from "../atom/ActionButton";
import React from "react";

const TrainingInterface = ({onClick}) => {
  return <div className={'e2e-train-network'}>
    <ActionButton onClick={onClick}>reward for black</ActionButton>
  </div>
}

export default TrainingInterface