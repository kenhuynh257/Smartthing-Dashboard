import React from 'react';
import Boards from "./Boards";
import axios from 'axios';
import Form from 'react-bootstrap/Form';

export  default class PasswordInput extends React.Component{
    state={

    }

    handleSubmit(event){
        event.preventDefault();
    }
    render(){
        return (
            <Form onSubmit={this.handleSubmit}>
                <Form.Label>Password</Form.Label>
                <Form.Control type="number"/>
            </Form>
        )
    }

}