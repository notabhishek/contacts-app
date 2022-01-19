import React , {useEffect, useState} from 'react';
import { styled, useTheme , ThemeProvider , createTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import CssBaseline from '@mui/material/CssBaseline';
import AppBarComponent from '../../Components/Home/AppMenuComponent';
import DrawerPanel , {DrawerHeader} from '../../Components/Home/DrawerPanel';
import ContactList from '../../Components/Contact/ContactList';

export default function HomeView({contacts, searchKey, setSearchKey, updateScore}){
    const theme = createTheme()
    const darkTheme = createTheme({
        palette : {
            mode : 'dark',
        }
    })
    const [open , setOpen] = useState(true)
    // contacts
    const handleDrawerOpen = () => {
        setOpen(true);
    };

    const handleDrawerClose = () => {
        setOpen(false);
    };

    return(
        <ThemeProvider theme = {darkTheme}>
            <Box sx={{ display: 'flex' }}>
                <CssBaseline />
                <AppBarComponent open = {open} handleDrawerOpen = {handleDrawerOpen} 
                searchKey = {searchKey}
                setSearchKey = {setSearchKey}/>
                <DrawerPanel theme = {theme} open = {open} handleDrawerClose = {handleDrawerClose}/>
                <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
                    <DrawerHeader />
                    <ContactList contacts = {contacts} updateScore = {updateScore}/>
                </Box>
            </Box>
        </ThemeProvider>
    )
} 
