import React, {useEffect, useState} from "react";
import ReactFlow from 'react-flow-renderer';

const elements = [
  {
    id: '1',
    type: 'input', // input node
    data: {label: 'Input Node'},
    position: {x: 250, y: 25},
  },
  // default node
  {
    id: '2',
    // you can also pass a React component as a label
    data: {label: <div>Default Node</div>},
    position: {x: 100, y: 125},
  },
  {
    id: '3',
    type: 'output', // output node
    data: {label: 'Output Node'},
    position: {x: 250, y: 250},
  },
  // animated edge
  {id: 'e1-2', source: '1', target: '2', animated: true},
  {id: 'e2-3', source: '2', target: '3'},
];

const NeuralNetModel = () => {
  const [model, setModel] = useState({});
  const [elements, setElements] = useState([]);

  useEffect(() => {
    fetch('/model', {mode: 'no-cors'})
        .then(response => response.json())
        .then(results => {
          console.log(JSON.stringify(results, null, 2))
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
    <div style={{height: 300, width: '100%'}}>
      <ReactFlow elements={elements}/>
    </div>
  </>;
};

export default NeuralNetModel
