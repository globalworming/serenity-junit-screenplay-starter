import ActionButton from "../atom/ActionButton";
import React from "react";


const byLabel = (a, b) => {
  if (a.label < b.label) {
    return -1;
  }
  if (a.label > b.label) {
    return 1;
  }
  return 0;
};

const TrainingInterface = ({onClick, results}) => {
  return <div className={'e2e-train-network'}>
    {results.sort(byLabel).map(({label}) =>
        <React.Fragment key={label}>
          <ActionButton onClick={() => onClick(label)}>reward for {label}</ActionButton>
        </React.Fragment>
    )}
  </div>
}

export default TrainingInterface