import React from "react";

const maxWidth = 400;

const ShowErrorStatistics = ({errors}) => <div style={{maxWidth: maxWidth}}>
  <p>
    <strong>after <span className={"e2e-number-of-training-rounds"}>{errors.length}</span> trainings</strong>
  </p>
  {errors.length > 0 && <p>from {errors[0].toFixed(2)} to {errors[errors.length - 1].toFixed(2)}</p>}

  <p>
    {errors.map((it, i) => <span
        style={{display: 'inline-block', width: maxWidth / errors.length, height: it * 50, background: '#FFF'}}
        key={i}/>)}
  </p>

</div>;

export default ShowErrorStatistics