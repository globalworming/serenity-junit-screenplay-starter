import React from "react";


const byConfidenceDec = (a, b) => b.confidence - a.confidence;

const ShowInferenceResultsByConfidence = ({results}) =>
    <dl className={"e2e-inference-results"}>
      {results.sort(byConfidenceDec).map((result) => <React.Fragment key={result.label}>
            <dt className={'e2e-show-confidence-label'}>{result.label}</dt>
            <dd className={'e2e-inference-confidence e2e-inference-confidence-for-label-' + result.label}>
              {result.confidence.toFixed(2)}
            </dd>
          </React.Fragment>
      )}
    </dl>;

export default ShowInferenceResultsByConfidence