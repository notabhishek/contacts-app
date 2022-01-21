import React , {useState} from 'react'
import LoginView from './LoginView'
import { createTheme } from '@mui/material/styles';
import {
    loginUserAPI
  } from "../../Utils/APIs";

import {Navigate} from 'react-router-dom'

const theme = createTheme();
const darkTheme = createTheme({
    palette:{
        mode : 'dark',
    }
}
)

export default function  Login(){

    const [login , setLogin] = useState(false)

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
                setLogin(true)
            } else console.log("server error");
          })
          .catch((error) => console.log(error));
    };

    if(login){
        return <Navigate to='/'/>
    }

    return (
        <LoginView handleSubmit = {handleSubmit} currentTheme={darkTheme}/>
    )
}