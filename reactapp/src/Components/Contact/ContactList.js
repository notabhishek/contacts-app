import React, { useState } from 'react';
import ContactCard from './ContactCard';
export default function ContactList(props) {
    const [contacts, setContacts] = useState(props.contacts);
    return (    
        <ul className="list-unstyled">
            {contacts.map(contact => (
                <ContactCard key={contact.cid} contact={contact}/>
            ))}
        </ul>
    );
}