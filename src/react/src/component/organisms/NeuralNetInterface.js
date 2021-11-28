import React, {useEffect, useState} from "react";
import ColorPicker from "./ColorPicker";
import ShowInferenceResults from "./ShowInferenceResults";
import TrainingInterface from "./TrainingInterface";

function buildQueryPart({h, s, l}) {
  return `h=${h}&s=${s}&l=${l}`;
}

const NeuralNetInterface = () => {
  const [hsl, setHsl] = useState({h: 177, s: 33, l: 33});
  const [results, setResults] = useState([]);

  useEffect(() => {
    fetch('/infer?' +
        buildQueryPart(hsl), {mode: 'no-cors'})
        .then(response => response.json())
        .then(results => setResults(results));
  }, [hsl, setResults]);

  const onClick = (l) => {
    fetch('/train?label=' + l);
    fetch('/infer?' + buildQueryPart(hsl), {mode: 'no-cors'})
        .then(response => response.json())
        .then(data => setResults(data));
  };
  console.log("results", results);
  return <>
    <ColorPicker setHsl={setHsl} currentColor={hsl}/>
    <ShowInferenceResults results={results}/>
    <TrainingInterface onClick={onClick} results={results}/>

  </>;
};

export default NeuralNetInterface
