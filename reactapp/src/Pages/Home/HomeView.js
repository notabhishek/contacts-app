import React , {useEffect, useState} from 'react';
import { styled, useTheme , ThemeProvider , createTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import CssBaseline from '@mui/material/CssBaseline';
import AppBarComponent from '../../Components/Home/AppMenuComponent';
import DrawerPanel , {DrawerHeader} from '../../Components/Home/DrawerPanel';
import ContactList from '../../Components/Contact/ContactList';

// const contact1 = {
//     uid: 101,
//     cid: 1,
//     name: 'abhishek',
//     email: 'test@example.com',
//     phone: '780601234',
//     address: 'this is some random address',
//     score: 1,
//   }
//   const contact2 = {
//     uid: 102,
//     cid: 1,
//     name: 'harish',
//     email: 'harish@example.com',
//     phone: '899901234',
//     address: 'new address',
//     score: 10,
//   }

//   const contacts = [contact1, contact2, contact1, contact2, contact1, contact2];

export default function HomeView(){
    const theme = createTheme()
    const darkTheme = createTheme({
        palette : {
            mode : 'dark',
        }
    })
    const [contacts, setContacts] = useState([]);
    const [open , setOpen] = useState(true)
    // contacts
    const handleDrawerOpen = () => {
        setOpen(true);
    };

    const handleDrawerClose = () => {
        setOpen(false);
    };

    function fetchData() {
        let url = "http://localhost:8080/contacts/getAll"
          
          fetch(url)
          .then(res => res.json())
          .then((result) => {
            setContacts(result);
            console.log(contacts);
          }
        ).catch( error => {
          console.log("erorr in fetchData()");
        })
      }

    useEffect(() => {
        fetchData();
    }, []);

    return(
        <ThemeProvider theme = {darkTheme}>
            <Box sx={{ display: 'flex' }}>
                <CssBaseline />
                <AppBarComponent open = {open} handleDrawerOpen = {handleDrawerOpen}/>
                <DrawerPanel theme = {theme} open = {open} handleDrawerClose = {handleDrawerClose}/>
                <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
                    <DrawerHeader />
                    <ContactList contacts = {contacts}/>
                </Box>
            </Box>
        </ThemeProvider>
    )
} 
