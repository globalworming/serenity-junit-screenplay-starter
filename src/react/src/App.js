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
        .then(data => setResults(data['inferenceResults']));
  }, [hsl, setResults]);

  const updateHsl = (hsl) => {
    setHsl(hsl)
  }

  const onClick = () => () => {
    fetch('/train?label=black');
    fetch('/infer?' + buildQueryPart(hsl), {mode: 'no-cors'})
        .then(response => response.json())
        .then(data => setResults(data['inferenceResults']));
  }

  let byConfidenceDec = (a, b) => b.confidence - a.confidence;
  return <>
    <HslColorPicker color={hsl} onChange={updateHsl}/>
    <dl>
      {results.sort(byConfidenceDec).map((result) => <React.Fragment key={result.label}>
            <dt>{result.label}</dt>
            <dd>
              {result.confidence}
            </dd>
          </React.Fragment>
      )}
    </dl>
    <Button onClick={onClick()}>reward for black</Button>
  </>;
};
