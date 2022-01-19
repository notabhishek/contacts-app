import React , {useEffect, useState} from 'react';
import { styled, useTheme , ThemeProvider , createTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import CssBaseline from '@mui/material/CssBaseline';
import AppBarComponent from '../../Components/Home/AppMenuComponent';
import DrawerPanel , {DrawerHeader} from '../../Components/Home/DrawerPanel';
import ContactList from '../../Components/Contact/ContactList';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';
import CreateContactCard from '../../Components/Contact/CreateContactCard';

export default function HomeView({contacts ,setContacts}){
    const theme = createTheme()
    const darkTheme = createTheme({
        palette : {
            mode : 'dark',
        }
    })
    const [open , setOpen] = useState(true)
    const [currentTab , setCurrentTab] = useState('1')
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
                <AppBarComponent open = {open} handleDrawerOpen = {handleDrawerOpen}/>
                <DrawerPanel theme = {theme} open = {open} handleDrawerClose = {handleDrawerClose}/>
                <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
                    <DrawerHeader />
                    <Box sx={{ width: '100%', typography: 'body1' }}>
                        <TabContext value={currentTab}>
                            <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                            <TabList onChange={(event , newvalue)=>setCurrentTab(newvalue)} aria-label="lab API tabs example">
                                <Tab label="Contacts" value="1" />
                                <Tab label="New Contact" value="2" />
                            </TabList>
                            </Box>
                            <TabPanel value="1">
                                <ContactList contacts = {contacts}/>
                            </TabPanel>
                            <TabPanel value="2">
                                <CreateContactCard contacts={contacts} setContacts={setContacts}/>    
                            </TabPanel>
                        </TabContext>
                    </Box>
                </Box>
            </Box>
        </ThemeProvider>
    )
} 
