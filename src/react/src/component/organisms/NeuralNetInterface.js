import React, {useEffect, useState} from "react";
import ColorPicker from "./ColorPicker";
import ShowInferenceResults from "./ShowInferenceResults";
import TrainingInterface from "./TrainingInterface";
import NeuralNetModel from "./NeuralNetModel";
import ActionButton from "../atom/ActionButton";

function buildInferQueryPart({h, s, l}) {
  return `h=${h}&s=${s}&l=${l}`;
}

function buildRememberQueryPart({h, s, l}, label) {
  return `h=${h}&s=${s}&l=${l}&label=${label}`;
}

function getButton(onClick, hex) {
  return <button className={`e2e-do-set-color-${hex}`} style={{height: '1px', width: '1px', margin: 0, fontSize: 0}}
                 onClick={onClick}>#{hex}
  </button>;
}

function P1({children}) {
  return <div className={"p-2"}>{children}</div>
}

const NeuralNetInterface = () => {
  const [hsl, setHsl] = useState({h: 177, s: 33, l: 33});
  const [results, setResults] = useState([]);
  const [facts, setFacts] = useState([]);
  const [currentError, setCurrentError] = useState(.0);

  const askForInferenceResults = (hsl) => {
    fetch('/infer?' +
        buildInferQueryPart(hsl), {mode: 'no-cors'})
        .then(response => response.json())
        .then(results => setResults(results));
  };
  const askForFacts = () => {
    return fetch('/facts', {mode: 'no-cors'})
        .then(response => response.json())
        .then(results => setFacts(results))
        .then(askForCurrentError);
  };
  const askForCurrentError = () => {
    fetch('/currentError', {mode: 'no-cors'}).then(response => {
      return response.text()
    }).then(result => {
      setCurrentError(result)
    });
  };
  const doRemember = (label) => {
    return fetch('/remember?' + buildRememberQueryPart(hsl, label), {mode: 'no-cors'});
  };
  const doReset = () => {
    return fetch('/reset', {mode: 'no-cors'}).then(() => window.location.reload());
  };
  const doTrain = () => {
    return fetch('/train', {mode: 'no-cors'}).then(() => fetch('/infer?' + buildInferQueryPart(hsl), {mode: 'no-cors'})
        .then(response => response.json())
        .then(data => setResults(data))
        .then(askForCurrentError)
    );
  };

  useEffect(() => {
    askForInferenceResults(hsl);
  }, [hsl]);

  useEffect(() => {
    askForFacts();
  }, []);

  return <div className={"d-flex flex-row flex-wrap"}>
    <div className={"d-flex flex-row flex-wrap"}>
      <P1>
        <ActionButton className={"e2e-do-reset"} onClick={doReset}>reset
        </ActionButton>
        {getButton(() => setHsl({h: 0, s: 0, l: 0}), "000000")}
        {getButton(() => setHsl({h: 0, s: 0, l: 100}), "111111")}
      </P1>
      <P1>
        <ColorPicker setHsl={setHsl} currentColor={hsl}/>
      </P1>
      <P1>
        <ShowInferenceResults results={results}/>
      </P1>
    </div>
    <P1>
      <div style={{maxWidth: 500}}>
        <TrainingInterface remember={doRemember} train={doTrain} askForFacts={askForFacts}
                           currentError={currentError} facts={facts} results={results}/>
      </div>
    </P1>
    <P1>
      <NeuralNetModel/>
    </P1>
  </div>
      ;
};

export default NeuralNetInterface
