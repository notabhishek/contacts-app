import './App.css';
import ContactCard from './Components/Contact/ContactCard';
import ContactList from './Components/Contact/ContactList';


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
    <div className="App">
      {/* <ContactCard contact = {contact1}/> */}
      <ContactList contacts = {contacts}/>
    </div>
  );
}

export default App;
