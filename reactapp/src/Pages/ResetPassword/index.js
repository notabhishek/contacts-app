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
    const [genTokenLoading , setGenTokenLoading] = useState(false)
    const { setAlertPop } = useAppConsumer();

    const handleGenResetToken = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        console.log(data.get('email'));
        const payload = {
            email: data.get('email')
        }

        setGenTokenLoading(true)
        genResetTokenAPI(payload)
            .then((response) => {
                if (response.status === 200) {
                    setAlertPop({ open: true, severity: 'success', errorMessage: response.data.Success })
                } else console.log("server error");
                setGenTokenLoading(false)
            })
            .catch((error) => {
                setGenTokenLoading(false)
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
            handleGenResetToken={handleGenResetToken} genTokenLoading={genTokenLoading}
        />
    )
}