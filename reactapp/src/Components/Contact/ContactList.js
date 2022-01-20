import React, { useState } from "react";
import ContactCard from "./ContactCard";

import Dexie from "dexie";
import { useLiveQuery } from "dexie-react-hooks";

export default function ContactList({
  contacts,
  setContacts,
  updateScore,
  updateContact,
}) {
  const db = new Dexie("contacts");
  db.version(1).stores({
    contacts: "cid, uid, name, email, phone, address, score",
  });

  const allContacts = useLiveQuery(() => db.contacts.toArray(), []);
  if (!allContacts) return null;

  console.log("all contacts", allContacts);

  return (
    <ul className="list-unstyled">
      {contacts.map((contact) => {
        console.log(contact)
        return <ContactCard
          key={contact.cid}
          contact={contact}
          setContacts={setContacts}
          updateScore={updateScore}
          updateContact={updateContact}
        />
      })}
    </ul>
  );
}
