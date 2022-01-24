import React, { useEffect, useState } from "react";
import ContactCard from "./ContactCard";
import { ModalComponent } from "../ModalComponent";
import {fetchDelContactsAPI, updateScoreAPI } from "../../Utils/APIs";
import { useHomeConsumer } from "../../Utils/HomeContext/HomeContext";
import ContactControlBar from "./ContactControlBar";
import {Box , Typography} from '@mui/material'

function EmptyDel(){
    return(
        <Box display={'flex'} sx={{mt : 10, alignItems : 'center' , justifyContent : 'center'}}>
          <Typography>Such a void, add some contacts to favrouites to fill it</Typography>
        </Box>
      )
}

export default function DelContactList() {

  const [delContacts, setDelContacts] = useState([])
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

  function fetchDelContacts() {
    fetchDelContactsAPI()
        .then((response) => {
            if (response.status === 200) setDelContacts(response.data);
            else console.log("server error");
        })
        .catch((error) => console.log(error));
}

  useEffect(()=>{
    fetchDelContacts()
  },[])

  const DeleteAllContacts = () => {
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
      {/* <ContactControlBar contactsDelete={contactsDelete} setModalOpen ={setModalOpen}/> */}
      {delContacts.length>0 ? delContacts.map((contact) => {
        // console.log(contact)
        return <ContactCard
          key={contact.cid}
          contact={contact}
          setFavContacts={setDelContacts}
          contactsDelete={contactsDelete}
          setContactsDelete={setContactsDelete}
          updateScore={updateScore}
          isDeleted = {true}
        />
      }) : <EmptyDel/>}
    </ul>
  );
}
