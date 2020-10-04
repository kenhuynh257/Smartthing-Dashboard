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
        pass: "",
        disarmID: "",
        disarmStatus: ""

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

    async handlePassword(event) {
        event.preventDefault();
        let pass = this.state.pass;

        await axios.post('/SendCommand/Disarm', [pass, this.state.disarmID, "switch", this.state.disarmStatus])
            .then(r => {
                console.log('response: ', r)
            }, (er) => {
                console.log(er);
            })
        this.setState({isOpen: false});

        //setTimeout(window.location.reload(), 3000);
    }


    async handleSubmit(deviceID, capability, status) {
        let url = '/CheckDeviceID/' + deviceID;
        console.log(deviceID);

        await axios.get(url)
            .then(r => {
                if (r.data === "disarm") {
                    this.setState({isOpen: true, disarmID: deviceID, disarmStatus: status});

                } else {

                    axios.post('/SendCommand/Arm', [deviceID, capability, status])
                        .then(r => {
                            console.log('response: ', r)
                        }, (er) => {
                            console.log(er);
                        });
                    window.location.reload();
                }


            }, (er) => {
                console.log(er);
            });
        //setTimeout(window.location.reload(), 3000);

    }


    render() {
        let data = this.state.device;
        console.log("data " + JSON.stringify(data));
        //console.log("pass"+this.state.pass)
        // let reData = this.state.device.reduce((r,a)=>{
        //     r[a.room] = [...r[a.room]||[],a];
        //     return r;
        // },{})
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
                                                        onClick={() => this.handleSubmit(s.deviceId, Object.keys(v)[0], Object.values(v)[0])}>

                                                        {/*capacity:status*/}
                                                        {Object.keys(v)[0]}: {Object.values(v)[0]}
                                                    </ListGroupItem>
                                                </ListGroup>

                                            </>
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
                            <Form
                                onSubmit={this.handlePassword.bind(this)}>
                                <Form.Label>Enter Password</Form.Label>
                                <Form.Control type="text"
                                              onChange={this.handleChange.bind(this)}/>
                            </Form>
                        </Modal.Body>

                    </Modal>

                </>
            </Container>
        );
    }
}