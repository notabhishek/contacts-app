import React, { useEffect, useState } from "react";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import IconButton from "@mui/material/IconButton";
import FavoriteIcon from "@mui/icons-material/Favorite";
import FavoriteBorderOutlinedIcon from '@mui/icons-material/FavoriteBorderOutlined';// import ShareIcon from "@mui/icons-material/Share";
import DeleteIcon from "@mui/icons-material/Delete";
import Box from "@mui/material/Box";
import { ModalComponent } from "../ModalComponent";
import { deleteContactAPI, updateFavAPI, deleteFromBinAPI } from "../../Utils/APIs";
import { Avatar, Checkbox } from "@mui/material";
import { COLORS } from "../../Utils/themes";
import { hashCode, isNull } from "../../Utils/utilities";
import { useNavigate } from "react-router-dom";
import { useAppConsumer } from "../../Utils/AppContext/AppContext";
import { useHomeConsumer } from "../../Utils/HomeContext/HomeContext";


export default function ContactCard(props) {

  const { setAlertPop } = useAppConsumer();
  const { favContactsContext , contactsContext , delContactsContext } = useHomeConsumer();
  const [favContacts, setFavContacts] = favContactsContext
  const [contactsData , setContactsData] = contactsContext
  const [delContacts , setDelContacts] = delContactsContext

  const PROFILE_COLOR = COLORS[Math.abs(hashCode(props.contact.cid)) % COLORS.length];

  const navigate = useNavigate();
  const [modalOpen, setModalOpen] = useState(false);
  const [contactData, setContactData] = useState(props.contact);
  const [checkBoxVisible, setCheckBoxVisible] = useState(false);
  const checked = isNull(props.checked[props.contact.cid]) ? false : props.checked[props.contact.cid]
  const isDeleted = props.isDeleted === true

  const removeContactFromChecklist = () => {
    const index = props.contactsDelete.indexOf(props.contact.cid);
    if (index > -1) {
      props.setContactsDelete((prevContact) => {
        let newDeleteContact = prevContact.filter(
          (contactId) => contactId !== props.contact.cid
        );
        return newDeleteContact;
      });
    }
  };

  const DeleteContact = () => {
    setModalOpen(false);
    if (isDeleted == false) {
      deleteContactAPI({ cid: props.contact.cid })
        .then((response) => {
          if (response.status === 200) {
            removeContactFromChecklist();
            props.setContacts((prevContact) => {
              let newContacts = prevContact.filter(
                (contact) => contact.cid !== props.contact.cid
              );
              return newContacts;
            });
            setAlertPop({ open: true, severity: 'success', errorMessage: "contact moved to bin" })
          } else {
            console.log("error from server while deleting contact");
          }
        })
        .catch((error) => {
          setAlertPop({ open: true, severity: 'error', errorMessage: error?.response?.data?.message })
        });
    } else {
      deleteFromBinAPI({ cid: props.contact.cid })
        .then((response) => {
          console.log(response)
          if (response.status === 200) {
            removeContactFromChecklist();
            setDelContacts((prevContact) => {
              let newContacts = prevContact.filter(
                (contact) => contact.cid !== props.contact.cid
              );
              console.log(newContacts)
              return newContacts;
            });
            console.log(response);
            setAlertPop({ open: true, severity: 'success', errorMessage: "contact deleted permanently" })
          } else {
            console.log("error from server while deleting contact");
          }
        })
        .catch((error) => {
          console.log(error)
          setAlertPop({ open: true, severity: 'error', errorMessage: error?.response?.data?.message })
        });
    }
  };



  const checkHandler = (event) => {
    props.setChecked(prev => ({ ...prev, [props.contact.cid]: event.target.checked }));
    if (event.target.checked) {
      if (!props.contactsDelete.includes(props.contact.cid)) {
        props.setContactsDelete((prevContact) => [
          ...prevContact,
          props.contact.cid,
        ]);
      }
    } else {
      removeContactFromChecklist();
    }
  };

  const toggleFav = (event) => {
    const updFavPayload = {
      cid: contactData.cid,
      fav: !contactData.fav,
    }
    updateFavAPI(updFavPayload)
      .then((response) => {
        if (response.status === 200) {
          let newContactData = {...contactData , fav : !contactData.fav}
          setContactData(newContactData)
          setFavContacts((prevContacts) => {
            let temp = prevContacts.map((contact) => {
              if (contact.cid === props.contact.cid) {
                return newContactData;
              } else return contact;
            });
            return temp;
          });
          setContactsData((prevContacts) => {
            let temp = prevContacts.map((contact) => {
              if (contact.cid === props.contact.cid) {
                return newContactData;
              } else return contact;
            });
            return temp;
          });

          console.log("fav updated!");
        } else console.log("server error");
      })
      .catch((error) => console.log(error));
  };

  const handleContactOpen = () => {
    props.updateScore({
      cid: props.contact.cid,
    });
    console.log(props.contact);
    console.log("isDeleted " + isDeleted);
    if (isDeleted)
      navigate(`/bin/get/${props.contact.cid}`)
    else
      navigate(`/contacts/${props.contact.cid}`)
  }

  return (
    <Card
      sx={{
        m: 1,
      }}
      onMouseEnter={() => setCheckBoxVisible(true)}
      onMouseLeave={() => setCheckBoxVisible(false)}
    >
      {
        isDeleted ? (<ModalComponent
          open={modalOpen}
          title={"Confirm Delete"}
          text={
            "Do you want to permanently delete this contact?"
          }
          yesHandler={DeleteContact}
          noHandler={() => setModalOpen(false)}
        />) : (<ModalComponent
          open={modalOpen}
          title={"Confirm Delete"}
          text={
            "Delete this contact are you damn sure!!"
          }
          yesHandler={DeleteContact}
          noHandler={() => setModalOpen(false)}
        />)

      }
      <CardActions disableSpacing>
        {props.isCallingFromOtherList || (!checkBoxVisible && !props.checked[props.contact.cid]) ? <Avatar
          sx={{
            bgcolor: PROFILE_COLOR,
            width: "30px",
            height: "30px",
            ml: '10px',
          }}
        >
          {props.contact.name[0].toUpperCase()}
        </Avatar> :
          <Checkbox
            inputProps={{ 'aria-label': 'controlled' }}
            checked={checked}
            onChange={checkHandler}
          />}

        <Box sx={{ display: 'flex', flexDirection: 'row', flex: 1, cursor: 'pointer' }} onClick={handleContactOpen}>

          <Box sx={{ display: "flex", justifyContent: "space-between", m: 2 }}>
            <div>{props.contact.name}</div>
          </Box>
          <Box sx={{ display: "flex", justifyContent: "space-between", m: 2 }}>
            <div>{props.contact.email}</div>
          </Box>
          <Box sx={{ display: "flex", justifyContent: "space-between", m: 2 }}>
            <div>{props.contact.phone}</div>
          </Box>
        </Box>
        <Box sx={{ ml: "auto" }}>
          <IconButton aria-label="add to favorites"
            onClick={() => {
              if (isDeleted == false)
                toggleFav()
            }
            }
          >
            {contactData.fav ? (
              <FavoriteIcon />
            ) : (
              <FavoriteBorderOutlinedIcon />
            )}
          </IconButton>
          <IconButton aria-label="delete" onClick={() => setModalOpen(true)}>
            <DeleteIcon />
          </IconButton>
        </Box>
      </CardActions>
    </Card>
  );
}
