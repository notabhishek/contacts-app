import React from 'react'
import RegisterView from './RegisterView';
import { createTheme } from '@mui/material/styles';
import {
    registerUserAPI
  } from "../../Utils/APIs";

const theme = createTheme();
const darkTheme = createTheme({
    palette:{
        mode : 'dark',
    }
}
)

export default function Register(){

    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        // eslint-disable-next-line no-console
        const user = {
            name: data.get('name'),
            email: data.get('email'),
            password: data.get('password'),
            phone: data.get('phone'),
        };

        registerUserAPI(user)
        .then((response) => console.log(response));
    };

    return (
        <RegisterView handleSubmit = {handleSubmit} currentTheme={darkTheme}/>
    )
}