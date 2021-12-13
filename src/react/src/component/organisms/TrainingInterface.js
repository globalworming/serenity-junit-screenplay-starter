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

const TrainingInterface = ({remember, train, results, askForFacts, facts, currentError}) => {
  console.log("facts", facts)
  return <div className={'e2e-train-network'}>
    <strong>label this color </strong>
    {results.sort(byLabel).map(({label}) =>
        <React.Fragment key={label}>
          <ActionButton className={'e2e-do-reward-for-' + label}
                        onClick={() =>
                            remember(label)
                                .then(() => askForFacts())}>
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
    <hr/>
    <strong>on facts </strong>
    {facts.map((fact, i) => <React.Fragment key={i}>
      <p className={"e2e-show-fact"}>{JSON.stringify(fact, null, 2)}</p>
    </React.Fragment>)}
    <hr/>
    <strong>current error </strong><span className={"e2e-show-current-error"}>{currentError}</span>
  </div>
}

export default TrainingInterface