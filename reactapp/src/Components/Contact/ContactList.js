import React, { useEffect, useState } from "react";
import ContactCard from "./ContactCard";

import Dexie from "dexie";
import { useLiveQuery } from "dexie-react-hooks";
import { Box, IconButton } from "@mui/material";
import DeleteIcon from '@mui/icons-material/Delete';
import RefreshIcon from '@mui/icons-material/Refresh'
import { ModalComponent } from "../ModalComponent";
import { deleteContactsAPI, updateScoreAPI } from "../../Utils/APIs";
import { useHomeConsumer } from "../../Utils/HomeContext/HomeContext";
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import ContactControlBar from "./ContactControlBar";

export default function ContactList() {

  const { contactsContext } = useHomeConsumer()
  const [contacts, setContacts] = contactsContext
  const [contactsDelete, setContactsDelete] = useState([])
  const [modalOpen, setModalOpen] = useState(false)
  const [loading, setLoading] = useState(false)



  function updateScore(payload) {
    updateScoreAPI(payload)
      .then((response) => {
        if (response.status === 200) {
          console.log("score updated!");
        } else console.log("server error");
      })
      .catch((error) => console.log(error));
  }

  const DeleteAllContacts = () => {
    if (contactsDelete.length === 0) {
      setModalOpen(false)
      return
    }

    deleteContactsAPI({ contactCid: contactsDelete })
      .then(response => {
        if (response.status === 200) {
          setLoading(false)
          setModalOpen(false)
          const contactToDeleteSet = new Set(contactsDelete)
          setContacts(prevContacts => {
            const newContacts = prevContacts.filter(contact => !contactToDeleteSet.has(contact.cid))
            return newContacts
          })

          setContactsDelete([])
        }
        else {
          setLoading(false)
          setModalOpen(false)
          alert("error while deleting contacts")
        }
      })
      .catch(error => {
        alert("there was some error while deleting contacts")
        console.log(error)
      })

  }

  const db = new Dexie("contacts");
  db.version(1).stores({
    contacts: "cid, uid, name, email, phone, address, score",
  });

  const allContacts = useLiveQuery(() => db.contacts.toArray(), []);
  if (!allContacts) return null;

  // console.log("all contacts", allContacts);


  return (
    <ul className="list-unstyled" style = {{marginTop : 0}}>
      <ModalComponent
        open={modalOpen}
        title={"Confirm Delete"}
        text={"Are you sure you want to delete all selected contacts"}
        yesHandler={DeleteAllContacts}
        noHandler={() => setModalOpen(false)}
      />
      <ContactControlBar contactsDelete={contactsDelete} setModalOpen ={setModalOpen}/>
      {contacts.map((contact) => {
        // console.log(contact)
        return <ContactCard
          key={contact.cid}
          contact={contact}
          setContacts={setContacts}
          contactsDelete={contactsDelete}
          setContactsDelete={setContactsDelete}
          updateScore={updateScore}
        />
      })}
    </ul>
  );
}
