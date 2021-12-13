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
    return fetch('/currentError', {mode: 'no-cors'})
        .then(response => {
          return response.text()
        })
        .then(result => {
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
  useEffect(() => {
    askForCurrentError();
  }, []);

  console.log("results", results, "facts", facts, "error", currentError);
  return <>
    <ActionButton className={"e2e-do-reset"} onClick={doReset}>reset</ActionButton>
    <ColorPicker setHsl={setHsl} currentColor={hsl}/>
    <ShowInferenceResults results={results}/>
    <TrainingInterface remember={doRemember} train={doTrain} askForFacts={askForFacts}
                       currentError={currentError} facts={facts} results={results}/>
    <NeuralNetModel/>
  </>;
};

export default NeuralNetInterface
