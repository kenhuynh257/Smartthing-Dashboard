import React from 'react';
import axios from 'axios';

export default class Boards extends React.Component {
    state = {
        key: [],
    }

    componentDidMount() {
        axios.get('/GetData/')
            .then(res => {
                console.log(res.data.items);
                this.setState({key: res.data.items});

            })
    }

    render() {
        let data = this.state.key;
        console.log(data);
        return (
            <ul>
                {data.map(s=>(
                    <div className="data" key={s.deviceId}>
                        {s.label}
                    </div>
                ))}
            </ul>


       );

    }
}

//
