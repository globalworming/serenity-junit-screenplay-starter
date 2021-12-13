function buildInferQuery({h, s, l}) {
  return `h=${h}&s=${s}&l=${l}`;
}

function buildRememberQuery({h, s, l}, label) {
  return `h=${h}&s=${s}&l=${l}&label=${label}`;
}

const Actions = {
  askForInferenceResults: (hsl, callback) => fetch('/infer?' + buildInferQuery(hsl))
      .then(response => response.json())
      .then(callback),

  askForModel: (callback) => fetch('/model')
      .then(response => response.json())
      .then(callback),

  askForCurrentError: (callback) => fetch('/currentError')
      .then(response => response.text())
      .then(result => callback(result))
  ,

  askForFacts: (callback) => fetch('/facts')
      .then(response => response.json())
      .then(callback),


  doEstablishFact: (label, hsl) => fetch('/remember?' + buildRememberQuery(hsl, label)),

  doReset: () => fetch('/reset'),

  doTrain: () => fetch('/train')

};
export default Actions