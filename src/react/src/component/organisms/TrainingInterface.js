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

const TrainingInterface = ({remember, train, results}) => {
  return <div className={'e2e-train-network'}>
    <strong>label this color </strong>
    {results.sort(byLabel).map(({label}) =>
        <React.Fragment key={label}>
          <ActionButton onClick={() => remember(label)}>{label}</ActionButton>
        </React.Fragment>
    )}
    <hr/>
    <strong>train </strong> <ActionButton onClick={train}>a few rounds</ActionButton>
  </div>
}

export default TrainingInterface