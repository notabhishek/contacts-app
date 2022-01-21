import React, { useState } from 'react'
import RegisterView from './RegisterView';
import { createTheme } from '@mui/material/styles';
import {
    registerUserAPI
  } from "../../Utils/APIs";
import { Navigate, useNavigate } from 'react-router-dom';
import { useAppConsumer } from '../../Utils/AppContext/AppContext';
import { loginHandler } from '../../Utils/loginHandler';

const theme = createTheme();
const darkTheme = createTheme({
    palette:{
        mode : 'dark',
    }
}
)

export default function Register(){
    const navigate = useNavigate();
    const [alertOpen, setAlertOpen] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const {userContext} = useAppConsumer();

    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        // eslint-disable-next-line no-console
        const user = {
            name: data.get('name'),
            email: data.get('email'),
            password: data.get('password'),
            phone: data.get('phone'),
            address : data.get('address')
        };

        registerUserAPI(user)
        .then((response) => {
            if (response.status === 200) {
                console.log(response.data);
                if(response.data['Error'] === undefined) {
                    localStorage.setItem('jwt-token', response.data['jwt-token']);
                    loginHandler(userContext)
                    navigate('/contacts')
                } else {
                    setErrorMessage(response.data['Error']);
                    setAlertOpen(true);
                }

            } else console.log("server error");
          })
          .catch((error) => console.log(error));
    };

    return (
        <RegisterView handleSubmit = {handleSubmit} currentTheme={darkTheme}
        alertOpen={alertOpen}
        setAlertOpen={setAlertOpen}
        errorMessage={errorMessage}/>
    )
}