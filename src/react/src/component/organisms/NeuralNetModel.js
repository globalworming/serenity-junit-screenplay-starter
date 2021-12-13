import React, {useEffect, useState} from "react";
import ReactFlow, {Controls} from 'react-flow-renderer';

const NeuralNetModel = ({model}) => {
  const [elements, setElements] = useState([]);


  useEffect(() => {
    if (!model.hasOwnProperty('nodes')) {
      return
    }
    const _elements = [];
    model['nodes'].forEach((n) => _elements.push({
      sourcePosition: 'left',
      targetPosition: 'right',
      id: n.uuid,
      type: 'default', // input node
      data: {label: <>{n.label}<br/><strong>{n.activation.toFixed(2)}</strong></>},
      position: {x: 100 + n.layer * 300, y: n.index * 100},
    }));
    model['edges'].forEach((e) => _elements.push({
      id: e.uuid,
      source: e.source,
      target: e.target,
      animated: true,
      label: e.weight.toFixed(2)
    }));
    setElements(_elements)
  }, [model]);

  return <>
    <div></div>
    <div style={{height: 400, minWidth: 700}}>
      <ReactFlow elements={elements}><Controls/>
      </ReactFlow>
    </div>
  </>;
};

export default NeuralNetModel
