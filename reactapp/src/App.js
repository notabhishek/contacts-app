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
