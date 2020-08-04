import React from 'react';
import axios from 'axios';

export default class Boards extends React.Component {
    state = {
        key: [],
    }

    componentDidMount() {
        axios.get('/RemoteKey/')
            .then(res => {
                this.setState({key: res.data});

            })
    }

    render() {
        let theKey = this.state.key.toString();
        return <h1>{theKey}</h1>;

    }
}

