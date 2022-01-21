import React , {useEffect, useState} from 'react'
import { styled } from "@mui/material/styles";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import Collapse from "@mui/material/Collapse";
import IconButton from "@mui/material/IconButton";
import FavoriteIcon from "@mui/icons-material/Favorite";
import ShareIcon from "@mui/icons-material/Share";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import { addContactAPI } from '../../Utils/APIs';
import { useHomeConsumer } from '../../Utils/HomeContext/HomeContext';
import Alert from '@mui/material/Alert';
import Snackbar from '@mui/material/Snackbar';

export default function CreateContactCard(){
  const {contactsContext , searchContext} = useHomeConsumer();
  const [contacts , setContacts] = contactsContext
  const [searchKey , setSearchKey] = searchContext
  const [alertOpen, setAlertOpen] = useState(false);
    const [contactData , setContactData] = useState({
        name : '',
        email : '',
        phone : '',
        address : '',
    })

    function inputHandler(event , type){
        setContactData(prevcontact=>{
            return {...prevcontact , [type] : event.target.value}
        })
    }

    function createContact(){

        setAlertOpen(false);
        addContactAPI(contactData)
            .then(response=>{
              if(response.status===200){
                if(contactData.name.startsWith(searchKey) || contactData.email.startsWith(searchKey))
                  setContacts(prevContacts=>[...prevContacts , contactData])

                setAlertOpen(true);
              }
            })
            .catch(error=>console.log(error))
    }

    useEffect(()=>{
        console.log(contactData)
    },[contactData])

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
              onChange={(e)=>inputHandler(e , 'name')}
            />
            <TextField
              id="outlined-name"
              label="Email"
              fullWidth
              onChange={(e)=>inputHandler(e , 'email')}
            />
            <TextField
              id="outlined-name"
              label="Phone"
              fullWidth
              onChange={(e)=>inputHandler(e , 'phone')}
            />
            <TextField
              id="outlined-name"
              label="Address"
              fullWidth
              onChange={(e)=>inputHandler(e , 'address')}
            />
            <Button variant="contained" color="success" onClick={createContact}>
              Create
            </Button>

            

            <Snackbar open={alertOpen} autoHideDuration={6000} onClose={() => setAlertOpen(false) }>
              <Alert onClose={() => setAlertOpen(false)} severity="success" sx={{ width: '100%' }}>
                New Contact Added!
              </Alert>
            </Snackbar>
          </Box>
    )

}