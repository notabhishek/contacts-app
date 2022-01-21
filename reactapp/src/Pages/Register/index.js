import React, { useState } from 'react'
import RegisterView from './RegisterView';
import { createTheme } from '@mui/material/styles';
import {
    registerUserAPI
  } from "../../Utils/APIs";
import { Navigate, useNavigate } from 'react-router-dom';

const theme = createTheme();
const darkTheme = createTheme({
    palette:{
        mode : 'dark',
    }
}
)

export default function Register(){
    const navigate = useNavigate();

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
        .then((response) => {
            if (response.status === 200) {
                console.log(response.data);
                localStorage.setItem('jwt-token', response.data['jwt-token']);
                navigate('/contacts')
            } else console.log("server error");
          })
          .catch((error) => console.log(error));
    };

    return (
        <RegisterView handleSubmit = {handleSubmit} currentTheme={darkTheme}/>
    )
}