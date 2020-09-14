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

    }

    componentDidMount() {
        axios.get('/GetData/GetAllDevices')
            .then(res => {
                this.setState({device: res.data});

            })
    }


    handlePassword(pass) {
        console.log(pass);
    }

    handleSubmit(id) {
        let url = '/CheckDeviceID/' + id;
        console.log(id);
        axios.get(url)
            .then(r => {
                if (r.data === "notDisarm") {
                    this.setState({isOpen: true});
                } else {
                    console.log(r.data);
                }


            }, (er) => {
                console.log(er);
            });
    }

    render() {
        let data = this.state.device;
        console.log("data " + JSON.stringify(data));

        return (
            <Container>
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
                                                <ListGroupItem
                                                    variant={this.state.blueStatus.includes(Object.values(v)[0]) ? 'primary' : 'danger'}
                                                    action={true}
                                                    onClick={() => this.handleSubmit(s.deviceId)}>

                                                    {/*capacity:status*/}
                                                    {Object.keys(v)[0]}: {Object.values(v)[0]}
                                                </ListGroupItem>
                                            </ListGroup>
                                        )
                                    })
                                }

                            </Card>

                        ))}
                    </CardDeck>
                    <Modal show={this.state.isOpen}>
                        <Modal.Header closeButton>
                            <Modal.Title>Password</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <Form onSubmit={this.handlePassword}>
                                <Form.Label>Password</Form.Label>
                                <Form.Control type="number"/>
                            </Form>
                        </Modal.Body>

                    </Modal>
                </>
            </Container>
        );
    }
}