import React from 'react'
import LoginView from './LoginView'
import { createTheme } from '@mui/material/styles';

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
        console.log({
            email: data.get('email'),
            password: data.get('password'),
        });
    };

    return (
        <LoginView handleSubmit = {handleSubmit} currentTheme={darkTheme}/>
    )
}