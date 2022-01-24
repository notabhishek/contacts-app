import './App.css';
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link,
  Navigate
} from "react-router-dom";
import LeftPanel from './Components/Home/DrawerPanel';
import Login from './Pages/Login/';
import Register from './Pages/Register';
import ResetPassword from './Pages/ResetPassword';
import Home from './Pages/Home';
import { AppProvider, useAppConsumer } from './Utils/AppContext/AppContext';
import ContactList from './Components/Contact/ContactList';
import CreateContactCard from './Components/Contact/CreateContactCard';
import UpdateProfileComponent from './Components/Profile/UpdateProfileComponent';
import ProtectedRoute from './HOC/ProtectedRoute';
import { HomeProvider } from './Utils/HomeContext/HomeContext';
import ContactDetails from './Components/Contact/ContactDetails';
import FavContactList from './Components/Contact/FavContactLisk';
import {Alert , Snackbar} from '@mui/material'

function SnackComponent(){

  const {alertContext , setAlertPop} = useAppConsumer();
  const [alertData , setAlertData] = alertContext;

  return(<Snackbar
          open={alertData.open}
          autoHideDuration={6000}
          onClose={()=>setAlertPop({open : false , severity : 'info' , errorMessage : ''})}
        >
          <Alert
            onClose={()=>setAlertPop({open : false , severity : 'info' , errorMessage : ''})}
            severity={alertData.severity}
            sx={{ width: "100%" }}
          >
            {alertData.errorMessage}
          </Alert>
        </Snackbar>)
}

function App() {

  return (
    <AppProvider>
      <SnackComponent/>
      <Router>
        <Routes>
          <Route path = '*' element={<Navigate to={'/contacts'}/>}/>
          <Route path='/login' element={<Login />} />
          <Route path='/register' element={<Register />} />
          <Route path='/resetPassword' element={<ResetPassword />} />
          <Route element={<ProtectedRoute />}>
            <Route path='/' element={<HomeProvider><Home /></HomeProvider>} >
              <Route path='/contacts/:cid' element={<ContactDetails />} />
              <Route path='/contacts' element={<ContactList />} />
              <Route path='/newContact' element={<CreateContactCard />} />
              <Route path='/updateProfile' element={<UpdateProfileComponent />} />
              <Route path='/favrouites' element={<FavContactList />} />
            </Route>
          </Route>
        </Routes>
      </Router>
    </AppProvider>
  );
}

export default App;
