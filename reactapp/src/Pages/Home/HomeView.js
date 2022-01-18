import React , {useState} from 'react';
import { styled, useTheme , ThemeProvider , createTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import MuiDrawer from '@mui/material/Drawer';
import MuiAppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import CssBaseline from '@mui/material/CssBaseline';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import MailIcon from '@mui/icons-material/Mail';
import AppBarComponent from '../../Components/Home/AppMenuComponent';
import DrawerPanel , {DrawerHeader} from '../../Components/Home/DrawerPanel';
import ContactList from '../../Components/Contact/ContactList';

const contact1 = {
    uid: 101,
    cid: 1,
    name: 'abhishek',
    email: 'test@example.com',
    phone: '780601234',
    address: 'this is some random address',
    score: 1,
  }
  const contact2 = {
    uid: 102,
    cid: 1,
    name: 'harish',
    email: 'harish@example.com',
    phone: '899901234',
    address: 'new address',
    score: 10,
  }
  const contacts = [contact1, contact2, contact1, contact2, contact1, contact2];

export default function HomeView(){
    const theme = createTheme()
    const darkTheme = createTheme({
        palette : {
            mode : 'dark',
        }
    })

    const [open , setOpen] = useState(true)
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
                    <ContactList contacts = {contacts}/>
                </Box>
            </Box>
        </ThemeProvider>
    )
} 
