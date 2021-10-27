import './App.css';
import React, {useEffect, useState} from 'react';
import Button from 'react-bootstrap/Button';

import {HexColorPicker} from "react-colorful";

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

function approximateBlackness(color) {
  console.log(color.toString(16), (color % 0x100), (color % 0x100) / 0xFF);
  return ((color % 0x100) / 0xFF);
}

const ColorPicker = () => {
  const [color, setColor] = useState(0xaabbcc);
  const [result, setResult] = useState([]);

  useEffect(() => {
    fetch('/infer?color=' + approximateBlackness(color), {mode: 'no-cors'})
        .then(response => response.json())
        .then(data => setResult(data['inferenceResults']));
  }, [color, setResult]);

  const updateColor = (color) => {
    setColor(parseInt(color.substring(1), 16))
  }

  let colorToString = "#" + color.toString(16);

  const onClick = () => () => {
    fetch('/train?label=black');
    fetch('/infer?color=' + approximateBlackness(color), {mode: 'no-cors'})
        .then(response => response.json())
        .then(data => setResult(data['inferenceResults']));
  }

  return <>
    <HexColorPicker color={colorToString} onChange={updateColor}/>
    <p>
      <span>{JSON.stringify(result[0], null, 2)}</span>
      <Button onClick={onClick()}>reward for black</Button>
    </p>
  </>;
};
