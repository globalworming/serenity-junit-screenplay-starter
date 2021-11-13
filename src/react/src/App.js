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
  const [hsl, setHsl] = useState({h: 177, s: 33, l: 33});
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


  const colorPicker = () => <div className={'e2e-pick-color'}>
    <Button className="e2e-do-set-color-000000"
            onClick={() => setHsl({h: 0, s: 0, l: 0})}>#000000</Button>
    <HslColorPicker

        color={hsl}
        onChange={(hsl) => {
          setHsl(hsl)
        }}/>
  </div>;

  return <>
    {colorPicker()}
    <ShowInferenceResultsByConfidence
        results={results}/>
    <Button
        className={'e2e-do-reward-for-black'}
        onClick={onClick()}>
      reward for black
    </Button>
  </>;
};


const byConfidenceDec = (a, b) => b.confidence - a.confidence;

const ShowInferenceResultsByConfidence = ({results}) =>
    <dl className={"e2e-inference-results"}>
      {results.sort(byConfidenceDec).map((result) => <React.Fragment key={result.label}>
            <dt className={'e2e-show-confidence-label'}>{result.label}</dt>
            <dd className={'e2e-inference-confidence e2e-inference-confidence-for-label-' + result.label}>
              {result.confidence.toFixed(2)}
            </dd>
          </React.Fragment>
      )}
    </dl>;