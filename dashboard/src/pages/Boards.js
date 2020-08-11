import React from 'react';
import axios from 'axios';

export default class Boards extends React.Component {
    state = {
        device: [],
    }

    componentDidMount() {
        axios.get('/GetData/GetAllDevices')
            .then(res => {
                console.log(res.data.items);
                this.setState({device: res.data});

            })
    }

    render() {
        let data = this.state.device;
        console.log(data);
        return (
            <div>
                {data.map(s => (
                    //if s.label is the thing I want
                    <div className="card-deck" key={s.deviceId}>
                        <div className="card p-2">
                            <h4 className="card-title">{s.label}</h4>
                            <h6 className="card-text">{s.deviceId}</h6>
                            {
                                s.capabilities.map((v) => {
                                        return (
                                            <h6 className="card-text">
                                                {Object.keys(v)[0]}:{Object.values(v)[0]}
                                            </h6>
                                        );

                                    }
                                )
                            }
                        </div>

                    </div>
                ))}
            </div>


        );

    }

    //
    // renderCard(s) {
    //     return <div class="card p-2">
    //
    //
    //         <h6 className="card-text">{
    //             s.capabilities
    //         }</h6>
    //
    //     </div>;
    // }
}

//
