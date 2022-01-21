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
import Home from './Pages/Home';
import { AppProvider } from './Utils/AppContext/AppContext';
import ContactList from './Components/Contact/ContactList';
import ContactCard from './Components/Contact/ContactCard';
import CreateContactCard from './Components/Contact/CreateContactCard';
import UpdateProfileComponent from './Components/Profile/UpdateProfileComponent';

function App() {
  return (
    <AppProvider>
      <Router>
        <Routes>
          <Route path='/login' element={<Login />} />
          <Route path='/register' element={<Register />} />
          <Route path='/' element={<Home />} >
            <Route path = '/contacts' element = {<ContactList/>}/>
            <Route path = '/newContact' element = {<CreateContactCard/>}/>
            <Route path = '/updateProfile' element = {<UpdateProfileComponent/>}/>
          </Route>
        </Routes>
      </Router>
      </AppProvider>
  );
}

export default App;
