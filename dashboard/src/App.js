import React from 'react';
import './App.css';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {value: ''};
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {    this.setState({value: event.target.value});  }
  handleSubmit(event) {
    alert('A name was submitted: ' + this.state.value);
    event.preventDefault();
  }

  render() {
    return (

  <form onSubmit={this.handleSubmit}>
    <label>
      <h1>Remote Key:</h1> </label>
      <input className="input-group-lg text-danger"   type="text" value={this.state.value} onChange={this.handleChange} />
  </form>


    );
  }
}




export default App;
