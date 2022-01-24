import React, { useState } from 'react'
import LoginView from './LoginView'
import { createTheme } from '@mui/material/styles';
import {
    loginUserAPI
} from "../../Utils/APIs";

import { Navigate, useNavigate } from 'react-router-dom'
import { useAppConsumer } from '../../Utils/AppContext/AppContext';
import { loginHandler } from '../../Utils/loginHandler';

const theme = createTheme();
const darkTheme = createTheme({
    palette: {
        mode: 'dark',
    }
}
)

export default function Login() {
    const navigate = useNavigate();

    const { userContext, setAlertPop } = useAppConsumer();

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
                    localStorage.setItem('jwt-token', response.data['jwt-token']);
                    loginHandler(userContext)
                    navigate('/contacts')
                    setAlertPop({ open: true, severity: 'success', errorMessage: 'Logged in successfully' })
                }
                else{
                    console.log(response)
                }
            })
            .catch((error) => {
                setAlertPop({ open: true, severity: 'error', errorMessage: error?.response?.data?.message })
            });
    };

    return (
        <LoginView handleSubmit={handleSubmit} currentTheme={darkTheme} />
    )
}