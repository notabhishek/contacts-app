import React, { useState } from 'react'
import ResetPasswordView from './ResetPasswordView';
import { createTheme } from '@mui/material/styles';
import {
    genResetTokenAPI,
    resetPasswordAPI
} from "../../Utils/APIs";
import { Navigate, useNavigate } from 'react-router-dom';
import { useAppConsumer } from '../../Utils/AppContext/AppContext';
import { loginHandler } from '../../Utils/loginHandler';

const theme = createTheme();
const darkTheme = createTheme({
    palette: {
        mode: 'dark',
    }
}
)

export default function ResetPassword() {
    const navigate = useNavigate();
    const [alertOpen, setAlertOpen] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const { setAlertPop } = useAppConsumer();

    const handleGenResetToken = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        console.log(data.get('email'));
        const payload = {
            email: data.get('email')
        }

        genResetTokenAPI(payload)
            .then((response) => {
                if (response.status === 200) {
                    console.log(response.data);
                    console.log(response.data);
                    setAlertPop({ open: true, severity: 'success', errorMessage: response.data.success })
                } else console.log("server error");
            })
            .catch((error) => {
                setAlertPop({ open: true, severity: 'error', errorMessage: error?.response?.data?.message })
            });
    }
    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const payload = {
            email: data.get('email'),
            password: data.get('password'),
            resetToken: data.get('resetToken')
        };
        console.log(payload);
        resetPasswordAPI(payload)
            .then((response) => {
                if (response.status === 200) {
                    navigate('/login')
                    setAlertPop({ open: true, severity: 'success', errorMessage: 'password changed successfully' })

                } else console.log("server error");
            })
            .catch((error) => {
                setAlertPop({ open: true, severity: 'error', errorMessage: error?.response?.data?.message })
            });
    };

    return (
        <ResetPasswordView handleSubmit={handleSubmit} currentTheme={darkTheme}
            handleGenResetToken={handleGenResetToken}
        />
    )
}