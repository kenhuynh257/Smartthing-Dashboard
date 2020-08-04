import React from 'react';
import '../css/App.css';
import RemoteKey from "./RemoteKey";
import Boards from "./Boards";
import axios from 'axios';

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {key: ""};
    }

    componentDidMount() {
        axios.get('/RemoteKey/')
            .then(res => {
                console.log(res.data);
                this.setState({key: res.data});
                //global.Key = res.data;

            })
    }

    render() {

        if (this.state.key === "") {
            return <RemoteKey/>;
        } else {
            return <Boards/>;
        }


    }
}


export default App;