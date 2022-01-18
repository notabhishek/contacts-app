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
import HomeView from './Pages/Home/HomeView';

function App() {
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
  return (
    <Router>
      <Routes>
        <Route path = '/login' element = {<Login/>}/>
        <Route path = '/register' element = {<Register/>}/>
        <Route path = '/' element = {<HomeView/>}/>
      </Routes>
    </Router>
  );
}

export default App;
