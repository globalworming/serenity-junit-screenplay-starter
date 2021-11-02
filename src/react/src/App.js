import './App.css';
import React, {useEffect, useState} from 'react';
import Button from 'react-bootstrap/Button';

import {HslColorPicker} from "react-colorful";

function App() {
  return (
      <div className="App">
        <header className="App-header">
          <ColorPicker/>
        </header>
      </div>
  );
}

export default App;

function buildQueryPart({h, s, l}) {
  return `h=${h}&s=${s}&l=${l}`;
}

const ColorPicker = () => {
  const [hsl, setHsl] = useState({h: 0xFF, s: 0x00, l: 0x00});
  const [result, setResult] = useState([]);

  useEffect(() => {
    fetch('/infer?' +
        buildQueryPart(hsl), {mode: 'no-cors'})
        .then(response => response.json())
        .then(data => setResult(data['inferenceResults']));
  }, [hsl, setResult]);

  const updateHsl = (hsl) => {
    setHsl(hsl)
  }

  const onClick = () => () => {
    fetch('/train?label=black');
    fetch('/infer?' + buildQueryPart(hsl), {mode: 'no-cors'})
        .then(response => response.json())
        .then(data => setResult(data['inferenceResults']));
  }

  return <>
    <HslColorPicker color={hsl} onChange={updateHsl}/>
    <p>
      <span>{JSON.stringify(result, null, 2)}</span>
      <Button onClick={onClick()}>reward for black</Button>
    </p>
  </>;
};
