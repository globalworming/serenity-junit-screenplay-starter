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

const TrainingInterface = ({remember, train, results, facts, currentError}) => {
  return <div className={'e2e-train-network'}>
    <strong>label this color </strong>
    {results.sort(byLabel).map(({label}) =>
        <React.Fragment key={label}>
          <ActionButton className={'e2e-do-reward-for-' + label}
                        onClick={() => remember(label)}>
            {label}
          </ActionButton>{' '}
        </React.Fragment>
    )}
    <hr/>
    <strong>train </strong>
    <ActionButton className={"e2e-do-train"}
                  onClick={train}>
      a few rounds
    </ActionButton>
    <strong> on facts:</strong>
    {facts.map((fact, i) => <React.Fragment key={i}>
      <p className={"e2e-show-fact"}>{fact.inputs.map(value => value.toFixed(2)).join(", ")}
        {' --> '}{fact.outputs.map(value => value.toFixed(2)).join(' ')}
      </p>
    </React.Fragment>)}
    <hr/>
    <strong>current error </strong><span className={"e2e-show-current-error"}>{currentError}</span>
  </div>
}

export default TrainingInterface