import React, { useEffect, useState } from "react";
import ContactCard from "./ContactCard";
import { ModalComponent } from "../ModalComponent";
import { deleteContactsAPI, updateScoreAPI } from "../../Utils/APIs";
import { useHomeConsumer } from "../../Utils/HomeContext/HomeContext";
import ContactControlBar from "./ContactControlBar";
import AddCircleOutlinedIcon from "@mui/icons-material/AddCircleOutline";
import { IconButton, Typography } from "@mui/material";
import {Box} from '@mui/material'

function EmptyContact(){
  return(
    <Box display={'flex'} sx={{mt : 10, alignItems : 'center' , justifyContent : 'center'}}>
      <Typography>Such a void, Create some contacts to fill it</Typography>
    </Box>
  )
}

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
      {contacts.length >0 ? contacts.map((contact) => {
        // console.log(contact)
        return <ContactCard
          key={contact.cid}
          contact={contact}
          setContacts={setContacts}
          contactsDelete={contactsDelete}
          setContactsDelete={setContactsDelete}
          updateScore={updateScore}
        />
      }) : <EmptyContact/>}
    </ul>
  );
}
