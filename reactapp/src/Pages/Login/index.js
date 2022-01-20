import React from 'react'
import LoginView from './LoginView'
import { createTheme } from '@mui/material/styles';
import {
    loginUserAPI
  } from "../../Utils/APIs";

const theme = createTheme();
const darkTheme = createTheme({
    palette:{
        mode : 'dark',
    }
}
)

export default function  Login(){

    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        // eslint-disable-next-line no-console
        const user = {
            email: data.get('email'),
            password: data.get('password'),
        };

        loginUserAPI(user)
        .then((response) => {
            if (response.status === 200) {
                console.log(response.data);
                localStorage.setItem('jwt-token', response.data['jwt-token']);
            } else console.log("server error");
          })
          .catch((error) => console.log(error));
    };

    return (
        <LoginView handleSubmit = {handleSubmit} currentTheme={darkTheme}/>
    )
}