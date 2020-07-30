import React from 'react';
import '../css/App.css';
import RemoteKey from "./RemoteKey";
import Boards from "./Boards";

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {key: null};
        global.Key = null;
    }

    render() {
        const axios = require('axios');

        axios.get('/RemoteKey/')
            .then(res=>{
                console.log(res.data);
                //this.setState({key: res.data});
                global.Key = res.data;

            })


        if(global.Key == null){
            return <RemoteKey />;
        }
        else{
            return <Boards />;
        }


    }
}


export default App;