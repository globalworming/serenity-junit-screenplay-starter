import React from "react";
import ReactFlow, {Background, Controls} from 'react-flow-renderer';

const ShowNeuralNetModel = ({model}) => {
  if (!model.hasOwnProperty('nodes')) {
    return null
  }
  const elements = [];
  model['nodes'].forEach((n) => elements.push({
    sourcePosition: 'right',
    targetPosition: 'left',
    id: n.uuid,
    type: n.label ? n.type : "default", // input node
    isHidden: false,
    data: n.label ? {label: <>{n.label}<br/><strong>{n.activation.toFixed(2)}</strong></>} : {},
    position: {x: 100 + n.layer * 300, y: n.index * 100},
  }));
  model['edges'].forEach((e) => elements.push({
    id: e.uuid,
    source: e.source,
    target: e.target,
    animated: true
    //label: e.weight.toFixed(2)
  }));

  const onLoad = (reactFlowInstance) => {
    reactFlowInstance.fitView();
  };

  if (elements.length === 0) {
    return null
  }

  return <div style={{height: 400, minWidth: 700}}>
    <ReactFlow elements={elements} nodesConnectable={true}
               onLoad={onLoad}
               snapToGrid={true}
               snapGrid={[15, 15]}
               onlyRenderVisibleElements={false}
    >
      <Background/>
      <Controls/>
    </ReactFlow>
  </div>;
};

export default ShowNeuralNetModel
