import React, { useEffect, useState } from "react";
import ContactCard from "./ContactCard";

import Dexie from "dexie";
import { useLiveQuery } from "dexie-react-hooks";
import { Box, IconButton } from "@mui/material";
import DeleteIcon from '@mui/icons-material/Delete';
import RefreshIcon from '@mui/icons-material/Refresh'
import { ModalComponent } from "../ModalComponent";
import { deleteContactsAPI } from "../../Utils/APIs";

export default function ContactList({
  contacts,
  setContacts,
  updateScore,
  updateContact,
}) {

  const [contactsDelete, setContactsDelete] = useState([])
  const [modalOpen , setModalOpen] = useState(false)
  const [loading , setLoading] = useState(false)

  useEffect(() => {
    console.log(contactsDelete)
  }, [contactsDelete])

  const DeleteAllContacts = () =>{
    if(contactsDelete.length === 0){
      setModalOpen(false)
      return
    }

    deleteContactsAPI({contactCid : contactsDelete})
      .then(response=>{
        if(response.status === 200){
          setLoading(false)
          setModalOpen(false)
          const contactToDeleteSet = new Set(contactsDelete)
          setContacts(prevContacts =>{
            const newContacts = prevContacts.filter(contact=> !contactToDeleteSet.has(contact.cid))
            return newContacts
          })

          setContactsDelete([])
        }
        else{
          setLoading(false)
          setModalOpen(false)
          alert("error while deleting contacts")
        }
      })
      .catch(error=>{
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
    <ul className="list-unstyled">
      <ModalComponent
        open = {modalOpen}
        title={"Confirm Delete"}
        text ={"Are you sure you want to delete all selected contacts"}
        yesHandler={DeleteAllContacts}
        noHandler={()=>setModalOpen(false)}
      />
      <Box sx={{ mb: '20px' }}>
        {contactsDelete.length > 0 && <IconButton aria-label="delete" onClick={()=>setModalOpen(true)}>
            <DeleteIcon />
          </IconButton>}
        <IconButton aria-label="delete">
          <RefreshIcon />
        </IconButton>
      </Box>
      {contacts.map((contact) => {
        // console.log(contact)
        return <ContactCard
          key={contact.cid}
          contact={contact}
          setContacts={setContacts}
          contactsDelete={contactsDelete}
          setContactsDelete={setContactsDelete}
          updateScore={updateScore}
          updateContact={updateContact}
        />
      })}
    </ul>
  );
}
