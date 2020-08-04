import React from 'react';
import Boards from "./Boards";
import axios from 'axios';

export default class RemoteKey extends React.Component {
    constructor(props) {
        super(props);
        this.state = {value: ''};
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState({value: event.target.value});
    }

    handleSubmit(event) {
        event.preventDefault();

        axios.post('/RemoteKey/', this.state.value
        ).then((response) => {
            console.log(response);
        }, (error) => {
            console.log(error);
        });

        alert('A key was submitted: ' + this.state.value);

        window.location.href = <Boards/>;
    }

    render() {
        return (

            <form onSubmit={this.handleSubmit}>
                <label>
                    <h1>Remote Key:</h1></label>
                <input className="input-group-lg text-danger" type="text" value={this.state.value}
                       onChange={this.handleChange}/>
            </form>


        );
    }
}
