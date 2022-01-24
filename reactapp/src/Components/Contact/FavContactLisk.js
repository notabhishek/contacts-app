import React, { useEffect, useState } from "react";
import ContactCard from "./ContactCard";
import { ModalComponent } from "../ModalComponent";
import { fetchFavContactsAPI, updateScoreAPI } from "../../Utils/APIs";
import { useHomeConsumer } from "../../Utils/HomeContext/HomeContext";
import ContactControlBar from "./ContactControlBar";
import { Box, Typography } from '@mui/material'

function EmptyFav() {
  return (
    <Box display={'flex'} sx={{ mt: 10, alignItems: 'center', justifyContent: 'center' }}>
      <Typography>Such a void, add some contacts to favrouites to fill it</Typography>
    </Box>
  )
}

export default function FavContactList() {

  const [favContacts, setFavContacts] = useState([])
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

  function fetchFavContacts() {
    fetchFavContactsAPI()
      .then((response) => {
        if (response.status === 200) setFavContacts(response.data);
        else console.log("server error");
      })
      .catch((error) => console.log(error));
  }

  useEffect(() => {
    fetchFavContacts()
  }, [])

  const DeleteAllContacts = () => {
  }


  return (
    <ul className="list-unstyled" style={{ marginTop: 0 }}>
      <ModalComponent
        open={modalOpen}
        title={"Confirm Delete"}
        text={"Are you sure you want to delete all selected contacts"}
        yesHandler={DeleteAllContacts}
        noHandler={() => setModalOpen(false)}
      />
      {/* <ContactControlBar contactsDelete={contactsDelete} setModalOpen ={setModalOpen}/> */}
      {favContacts.length > 0 ? favContacts.map((contact) => {
        // console.log(contact)
        return <ContactCard
          checked={{}}
          key={contact.cid}
          contact={contact}
          setFavContacts={setFavContacts}
          contactsDelete={contactsDelete}
          setContactsDelete={setContactsDelete}
          updateScore={updateScore}
          isCallingFromOtherList={true}
        />
      }) : <EmptyFav />}
    </ul>
  );
}
