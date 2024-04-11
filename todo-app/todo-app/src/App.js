import './App.css';
import {Component} from "react";

function App() {

  return (
      <div className="App">
        My Todo App
        <FirstComponent/>
        <SecondComponent/>
      </div>
  );
}

// function component
function FirstComponent() {
  return (
      <div className="FirstComponent">
        First Component
      </div>
  );
}

// class component
class SecondComponent extends Component {
  render() {
    return (
        <div className="SecondComponent">
          Second Component
        </div>
    );
  }
}

export default App;
