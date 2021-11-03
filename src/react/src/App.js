import './App.css';
import React, {useEffect, useState} from 'react';
import Button from 'react-bootstrap/Button';

import {HslColorPicker} from "react-colorful";

function App() {
  return (
      <div className="App">
        <div className="content">
          <ColorPicker/>
        </div>
      </div>
  );
}

export default App;

function buildQueryPart({h, s, l}) {
  return `h=${h}&s=${s}&l=${l}`;
}

const ColorPicker = () => {
  const [hsl, setHsl] = useState({h: 0xFF, s: 0x00, l: 0x00});
  const [results, setResults] = useState([]);

  useEffect(() => {
    fetch('/infer?' +
        buildQueryPart(hsl), {mode: 'no-cors'})
        .then(response => response.json())
        .then(data => data['inferenceResults'])
        .then(results => setResults(results));
  }, [hsl, setResults]);

  const onClick = () => () => {
    fetch('/train?label=black');
    fetch('/infer?' + buildQueryPart(hsl), {mode: 'no-cors'})
        .then(response => response.json())
        .then(data => setResults(data['inferenceResults']));
  }


  return <>
    <HslColorPicker
        className={'e2e-pick-color'}
        color={hsl}
        onChange={(hsl) => {
          setHsl(hsl)
        }}/>
    <ShowInferenceResultsByConfidence
        results={results}/>
    <Button
        custom-attribute="some-value"
        onClick={onClick()}>
      reward for black
    </Button>
  </>;
};


const byConfidenceDec = (a, b) => b.confidence - a.confidence;

const ShowInferenceResultsByConfidence = ({results}) =>
    <dl className={"e2e-inference-results"}>
      {results.sort(byConfidenceDec).map((result) => <React.Fragment key={result.label}>
            <dt>{result.label}</dt>
            <dd className={'e2e-inference-confidence e2e-inference-confidence-for-label-' + result.label}>
              {result.confidence}
            </dd>
          </React.Fragment>
      )}
    </dl>;