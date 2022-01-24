import React, { useEffect, useState } from 'react'
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button"
import { useHomeConsumer } from '../../Utils/HomeContext/HomeContext';
import Alert from '@mui/material/Alert';
import Snackbar from '@mui/material/Snackbar';
import { Avatar, Divider, IconButton, Typography } from '@mui/material';
import { useParams, useNavigate } from 'react-router-dom'
import { delContactDetailsAPI, restoreContactAPI } from '../../Utils/APIs';
import KeyboardBackspaceIcon from '@mui/icons-material/KeyboardBackspace';
import { COLORS } from '../../Utils/themes';
import { hashCode } from '../../Utils/utilities';
import { useAppConsumer } from '../../Utils/AppContext/AppContext';

export default function DelContactDetails() {

    let { cid } = useParams();
    console.log("cidddd" + cid);
    const navigate = useNavigate();
    console.log(cid)
    // const 
    const { contactsContext } = useHomeConsumer();
    const [contacts, setContacts] = contactsContext;
    const {setAlertPop} = useAppConsumer();
    
    const [contactData, setContactData] = useState(null)
    const [updateContactData, setUpdateContactData] = useState({
        name: '',
        email: '',
        phone: '',
        address: '',
        fav: false,
    })
    const PROFILE_COLOR = contactData ? COLORS[Math.abs(hashCode(contactData.cid)) % COLORS.length] : null

    const getContactDetails = () => {
        delContactDetailsAPI({ cid: cid })
            .then(response => {
                console.log(response)
                if (response.status === 200) {
                    setContactData(response.data)
                    setUpdateContactData(response.data)
                }
                else {
                    console.log('invalid contact')
                    navigate('/contacts')
                }
            })
            .catch((error) => {
                console.log(error)
                navigate('/contacts')
            })
    }

    const handleRestore = () => {
        const payload = {
            cid : cid
        };
        restoreContactAPI(payload)
            .then((response) => {
                if (response.status === 200) {
                    setContactData(updateContactData)
                    setContacts((prevContacts) => {
                        let temp = prevContacts.map((contact) => {
                            if (contact.cid === contactData.cid) {
                                return updateContactData;
                            } else return contact;
                        });
                        return temp;
                    });

                    setAlertPop({ open: true, severity: 'success', errorMessage: "contact restored successfully" })
                    navigate('/bin')
                } else console.log("server error");
            })
            .catch((error) => {
                setAlertPop({ open: true, severity: 'error', errorMessage: error?.response?.data?.message })
            });
    };

    useEffect(() => {
        getContactDetails()
    }, [])

    return (
        <Box sx={{ m: 4 }}>
            <IconButton sx={{ mb: 3 }} onClick={() => navigate('/contacts')}>
                <KeyboardBackspaceIcon />
            </IconButton>
            <Box sx={{ display: 'flex', flexDirection: 'row', alignItems: 'center' }}>
                <Avatar sx={{ bgcolor : contactData ? PROFILE_COLOR : 'none', width: '100px', height: '100px' }}>
                    <Typography variant='h2'>A</Typography>
                </Avatar>
                {contactData && <Box sx={{ ml: 5 }}>
                    <Typography variant='h4'>{contactData.name}</Typography>
                    <Typography variant='h6'>{contactData.email}</Typography>
                    <Typography variant='h6'>{contactData.phone}</Typography>
                </Box>}
            </Box>
            <Divider sx={{ mt: 5, mb: 5 }} />
            <Box
                component="form"
                sx={{
                    "& > :not(style)": { m: 1 },
                }}
                noValidate
                autoComplete="off"
            >
                <TextField
                    id="standard-basic"
                    label="name"
                    variant="standard"
                    value={updateContactData.name}
                    disabled
                    sx={{ width: '50%' }}
                />
                <TextField
                    id="standard-basic"
                    label="email"
                    variant="standard"
                    value={updateContactData.email}
                    disabled
                    sx={{ width: '50%' }}
                />
                <TextField
                    id="standard-basic"
                    label="phone"
                    variant="standard"
                    value={updateContactData.phone}
                    disabled
                    sx={{ width: '50%' }}
                />
                <TextField
                    id="standard-basic"
                    label="address"
                    variant="standard"
                    value={updateContactData.address}
                    disabled
                    sx={{ width: '50%' }}
                />
            </Box>

            <Button sx={{ mt: 5, ml: 1 }} variant="contained" color="success" onClick={handleRestore}>
                Restore
            </Button>
        </Box>
    )

}