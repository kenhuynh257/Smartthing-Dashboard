import React from 'react';
import axios from 'axios';
import Card from 'react-bootstrap/Card'
import CardDeck from 'react-bootstrap/CardDeck'
import ListGroup from "react-bootstrap/ListGroup";
import ListGroupItem from "react-bootstrap/ListGroupItem";

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

    handleSubmit(id) {
        console.log(id);
    }

    render() {
        let data = this.state.device;
        let reData = this.state.device.reduce((r,a)=>{
            r[a.room] = [...r[a.room]||[],a];
            return r;
        },{});
        console.log(reData);
        const blueStatus = ["on", "dry", "closed"];
        return (
            <>
                <CardDeck>
                    {data.map(s => (

                        <Card style={{minWidth: '18rem', maxWidth: '18rem'}}>
                            <Card.Body>
                                <Card.Title>{s.label}</Card.Title>
                            </Card.Body>
                            {
                                s.capabilities.map(v => {
                                    return (
                                        <ListGroup className="list-group-flush">
                                            <ListGroupItem variant={blueStatus.includes(Object.values(v)[0])  ? 'primary' : 'danger'}
                                                           action={true}
                                                           onClick={()=>this.handleSubmit(Object.values(v)[0])}>
                                                {Object.keys(v)[0]}: {Object.values(v)[0]}
                                            </ListGroupItem>
                                        </ListGroup>
                                    )
                                })
                            }

                        </Card>

                    ))}
                </CardDeck>


            </>


        );

    }


}

//
