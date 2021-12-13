import React from "react";


const byConfidence = (v1, v2) => v2.confidence - v1.confidence;
const ShowInferenceResults = ({results}) => <>
  <strong>could be</strong>
  <ol className={"e2e-inference-results"}>
    {results.sort(byConfidence).map((result) => <li key={result.label}>
      <strong className={'e2e-show-confidence-label'}>{result.label}</strong> <span
        className={'e2e-inference-confidence e2e-inference-confidence-for-label-' + result.label}>
            {result.confidence.toFixed(2)}</span></li>
    )}
  </ol>
</>;

export default ShowInferenceResults