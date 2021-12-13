import React, {useEffect, useState} from "react";
import InferColors from "../molecules/ColorPicker";
import ShowInferenceResults from "../molecules/ShowInferenceResults";
import TrainingInterface from "../molecules/TrainingInterface";
import ShowNeuralNetModel from "../molecules/ShowNeuralNetModel";
import ActionButton from "../atom/ActionButton";
import Actions from "../../Actions";
import Padding from "../atom/Padding";

const NeuralNetInterface = () => {
  const [loaded, setLoaded] = useState(false);
  const [hsl, setHsl] = useState({h: 177, s: 33, l: 33});
  const [results, setResults] = useState([]);
  const [facts, setFacts] = useState([]);
  const [currentError, setCurrentError] = useState(.0);
  const [model, setModel] = useState({});

  useEffect(() => {
    Actions.askForInferenceResults(hsl, setResults);
  }, [loaded, hsl]);

  useEffect(() => {
    Actions.askForFacts((result) => setFacts(result))
  }, [loaded]);

  useEffect(() => {
    Actions.askForCurrentError((result) => setCurrentError(result))
  }, [loaded]);

  useEffect(() => {
    Actions.askForModel((result) => setModel(result))
  }, [loaded, hsl]);

  useEffect(() => {
    setLoaded(true)
  }, [loaded]);

  const doReload = () => setLoaded(false);
  const doReset = () => Actions.doReset().then(doReload);
  const doEstablishFact = (label) => Actions.doEstablishFact(label, hsl).then(doReload);
  const doTrain = () => Actions.doTrain(hsl).then(doReload);

  return <div className={"d-flex flex-row flex-wrap"}>
    <div className={"d-flex flex-row flex-wrap"}>
      <Padding>
        <ActionButton className={"e2e-do-reset"} onClick={doReset}>reset</ActionButton>
      </Padding>
      <Padding>
        <InferColors setHsl={setHsl} currentColor={hsl}/>
      </Padding>
      <Padding>
        <ShowInferenceResults results={results}/>
      </Padding>
    </div>
    <Padding>
      <div style={{maxWidth: 500}}>
        <TrainingInterface remember={doEstablishFact} train={doTrain}
                           currentError={currentError} facts={facts} results={results}/>
      </div>
    </Padding>
    <Padding>
      <ShowNeuralNetModel model={model}/>
    </Padding>
  </div>;
};

export default NeuralNetInterface
