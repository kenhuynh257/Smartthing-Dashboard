import React from 'react';
import axios from 'axios';
import Card from 'react-bootstrap/Card'
import CardDeck from 'react-bootstrap/CardDeck'
import Container from "react-bootstrap/Container";
import ListGroup from "react-bootstrap/ListGroup";
import ListGroupItem from "react-bootstrap/ListGroupItem";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";


export default class Boards extends React.Component {
    state = {
        device: [],
        blueStatus: ["on", "dry", "closed"],
        isOpen: false,
        pass: ""

    }

    componentDidMount() {
        axios.get('/GetData/GetAllDevices')
            .then(res => {
                this.setState({device: res.data});

            })
    }

    handleChange(event) {
        this.setState({pass: event.target.value});
    }

    handlePassword(deviceID, capability, status, event) {
        event.preventDefault();
        let pass = this.state.pass;
        console.log(pass, deviceID, capability, status);
        axios.post('/SendCommnad/Disarm', [pass, deviceID, capability, status])
            .then(r => {
                console.log('response: ', r)
            }, (er) => {
                console.log(er);
            })
        this.setState({isOpen: false});
    }


    handleSubmit(deviceID, capability, status) {
        let url = '/CheckDeviceID/' + deviceID;
        console.log(deviceID);
        axios.get(url)
            .then(r => {
                if (r.data === "disarm") {
                    this.setState({isOpen: true});
                } else {
                    axios.post('/SendCommnad/Arm', [deviceID, capability, status])
                        .then(r => {
                            console.log('response: ', r)
                        }, (er) => {
                            console.log(er);
                        })
                }

            }, (er) => {
                console.log(er);
            });
    }

    render() {
        let data = this.state.device;
        console.log("data " + JSON.stringify(data));
        //console.log("pass"+this.state.pass)
        return (
            <Container>
                <>
                    <CardDeck>
                        {data.map(s => (

                            <Card style={{minWidth: '18rem', maxWidth: '18rem'}} key={s.deviceId}>
                                <Card.Body>
                                    <Card.Title>{s.label}</Card.Title>
                                </Card.Body>
                                {
                                    s.capabilities.map(v => {
                                        return (
                                            <>
                                                <ListGroup className="list-group-flush">
                                                    <ListGroupItem
                                                        variant={this.state.blueStatus.includes(Object.values(v)[0]) ? 'primary' : 'danger'}
                                                        action={true}
                                                        onClick={() => this.handleSubmit(s.deviceId,Object.keys(v)[0],Object.values(v)[0])}>

                                                        {/*capacity:status*/}
                                                        {Object.keys(v)[0]}: {Object.values(v)[0]}
                                                    </ListGroupItem>
                                                </ListGroup>

                                                <Modal show={this.state.isOpen}>
                                                    <Modal.Header closeButton>
                                                        <Modal.Title>Password</Modal.Title>
                                                    </Modal.Header>
                                                    <Modal.Body>
                                                        <Form
                                                            onSubmit={this.handlePassword.bind(this, s.deviceId, Object.keys(v)[0], Object.values(v)[0])}>
                                                            <Form.Label>Password</Form.Label>
                                                            <Form.Control type="text"
                                                                          onChange={this.handleChange.bind(this)}/>
                                                        </Form>
                                                    </Modal.Body>

                                                </Modal>
                                            </>
                                        )
                                    })
                                }

                            </Card>

                        ))}
                    </CardDeck>

                </>
            </Container>
        );
    }
}