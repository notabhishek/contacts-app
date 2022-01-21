import React , {useEffect, useState} from 'react'
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import { updateUserAPI } from '../../Utils/APIs';
import { useAppConsumer } from '../../Utils/AppContext/AppContext';

export default function UpdateProfileComponent(){
    const {userContext} = useAppConsumer();
    const [userData , setUserData] = userContext;

    const [tempUserData , setTempUserData] = useState({
        name : '',
        email : '',
        phone : '',
        address : '',
    })


    useEffect(()=>{
        if(userData)
        setTempUserData(userData)
    },[userData])

    console.log(tempUserData)

    function inputHandler(event , type){
        setTempUserData(prevUserData=>{
            return {...prevUserData , [type] : event.target.value}
        })
    }

    function UpdateUserData(){
        let payload = {...tempUserData}
        delete payload.email
        updateUserAPI(payload)
            .then(response=>{
                if(response.status === 200){
                    setUserData(tempUserData)
                }
            })
            .catch(error=>console.log(error))
    }


    return(
        <Box
            component="form"
            sx={{
              "& > :not(style)": { m: 1 },
            }}
            noValidate
            autoComplete="off"
          >
            <TextField
              id="outlined-name"
              label="Name"
              fullWidth
              value = {tempUserData.name}
              onChange={(e)=>inputHandler(e , 'name')}
            />
            <TextField
              id="outlined-name"
              label="Email"
              fullWidth
              value = {tempUserData.email}
              disabled
              onChange={(e)=>inputHandler(e , 'email')}
            />
            <TextField
              id="outlined-name"
              label="Phone"
              fullWidth
              value = {tempUserData.phone}
              onChange={(e)=>inputHandler(e , 'phone')}
            />
            <TextField
              id="outlined-name"
              label="Address"
              fullWidth
              value = {tempUserData.address}
              onChange={(e)=>inputHandler(e , 'address')}
            />
            <Button variant="contained" color="success" onClick={UpdateUserData}>
              Update
            </Button>
          </Box>
    )

}