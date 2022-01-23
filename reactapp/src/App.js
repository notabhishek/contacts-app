import './App.css';
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link
} from "react-router-dom";
import LeftPanel from './Components/Home/DrawerPanel';
import Login from './Pages/Login/';
import Register from './Pages/Register';
import ResetPassword from './Pages/ResetPassword';
import Home from './Pages/Home';
import { AppProvider } from './Utils/AppContext/AppContext';
import ContactList from './Components/Contact/ContactList';
import CreateContactCard from './Components/Contact/CreateContactCard';
import UpdateProfileComponent from './Components/Profile/UpdateProfileComponent';
import ProtectedRoute from './HOC/ProtectedRoute';
import { HomeProvider } from './Utils/HomeContext/HomeContext';
import ContactDetails from './Components/Contact/ContactDetails';

function App() {
  return (
    <AppProvider>
      <Router>
        <Routes>
          <Route path='/login' element={<Login />} />
          <Route path='/register' element={<Register />} />
          <Route path='/resetPassword' element={<ResetPassword />} />
          <Route element = {<ProtectedRoute/>}>
          <Route path='/' element={<HomeProvider><Home /></HomeProvider>} >
            <Route path = '/contact/:cid' element={<ContactDetails/>}/>
            <Route path = '/contacts' element = {<ContactList/>}/>
            <Route path = '/newContact' element = {<CreateContactCard/>}/>
            <Route path = '/updateProfile' element = {<UpdateProfileComponent/>}/>
          </Route>
          </Route>
        </Routes>
      </Router>
      </AppProvider>
  );
}

export default App;
