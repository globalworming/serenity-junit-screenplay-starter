import './App.css';
import React from 'react';
import NeuralNetInterface from "./component/organisms/NeuralNetInterface";
import NeuralNetModel from "./component/organisms/NeuralNetModel";

function App() {
  return (
      <div className="App">
        <div className="content">
          <NeuralNetInterface/>
          <NeuralNetModel/>
        </div>
      </div>
  );
}

export default App;
