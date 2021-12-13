import React, {useEffect, useState} from "react";
import ReactFlow from 'react-flow-renderer';

const NeuralNetModel = () => {
  const [model, setModel] = useState({});
  const [elements, setElements] = useState([]);

  useEffect(() => {
    fetch('/model', {mode: 'no-cors'})
        .then(response => response.json())
        .then(results => {
          return setModel(results);
        });
  }, []);

  useEffect(() => {
    if (!model.hasOwnProperty('nodes')) {
      return
    }
    const _elements = [];
    model['nodes'].forEach((n) => _elements.push({
      id: n.uuid,
      type: 'input', // input node
      data: {label: n.label},
      position: {x: n.layer * 300, y: n.index * 100},
    }));
    model['edges'].forEach((e) => _elements.push({
      id: e.uuid,
      source: e.source,
      target: e.target,
      animated: true
    }));
    setElements(_elements)
  }, [model]);

  return <>
    <div></div>
    <div style={{height: 300, width: 500}}>
      <ReactFlow elements={elements}/>
    </div>
  </>;
};

export default NeuralNetModel
