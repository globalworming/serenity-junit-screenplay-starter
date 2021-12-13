import React, {useEffect, useState} from "react";
import ColorPicker from "./ColorPicker";
import ShowInferenceResults from "./ShowInferenceResults";
import TrainingInterface from "./TrainingInterface";

function buildInferQueryPart({h, s, l}) {
  return `h=${h}&s=${s}&l=${l}`;
}

function buildRememberQueryPart({h, s, l}, label) {
  return `h=${h}&s=${s}&l=${l}&label=${label}`;
}

const NeuralNetInterface = () => {
  const [hsl, setHsl] = useState({h: 177, s: 33, l: 33});
  const [results, setResults] = useState([]);

  function extracted() {
    fetch('/infer?' +
        buildInferQueryPart(hsl), {mode: 'no-cors'})
        .then(response => response.json())
        .then(results => setResults(results));
  }

  useEffect(() => {
    extracted();
  }, [hsl]);

  const remember = (label) => {
    fetch('/remember?' + buildRememberQueryPart(hsl, label), {mode: 'no-cors'});
  };
  const train = () => {
    fetch('/train', {mode: 'no-cors'}).then(() => fetch('/infer?' + buildInferQueryPart(hsl), {mode: 'no-cors'})
        .then(response => response.json())
        .then(data => setResults(data))
    );
  };
  console.log("results", results);
  return <>
    <ColorPicker setHsl={setHsl} currentColor={hsl}/>
    <ShowInferenceResults results={results}/>
    <TrainingInterface remember={remember} train={train} results={results}/>

  </>;
};

export default NeuralNetInterface
