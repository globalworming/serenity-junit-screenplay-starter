function buildInferQueryPart({h, s, l}) {
  return `h=${h}&s=${s}&l=${l}`;
}

function buildRememberQueryPart({h, s, l}, label) {
  return `h=${h}&s=${s}&l=${l}&label=${label}`;
}


const Actions = {
  askForInferenceResults: (hsl, callback) => {
    return fetch('/infer?' +
        buildInferQueryPart(hsl), {mode: 'no-cors'})
        .then(response => response.json())
        .then(callback);
  },

  askForModel: (callback) => fetch('/model', {mode: 'no-cors'})
      .then(response => response.json())
      .then(callback),

  askForCurrentError: (callback) => fetch('/currentError', {mode: 'no-cors'})
      .then(response => response.text())
      .then(result => callback(result))
  ,

  askForFacts: (callback) => fetch('/facts', {mode: 'no-cors'})
      .then(response => response.json())
      .then(callback),


  doEstablishFact: (label, hsl) => fetch('/remember?' + buildRememberQueryPart(hsl, label), {mode: 'no-cors'}),
  doReset: () => fetch('/reset', {mode: 'no-cors'}),
  doTrain: () => fetch('/train', {mode: 'no-cors'})

};
export default Actions