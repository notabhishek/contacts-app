import React, { useState } from "react";
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
import { hashCode } from "../../Utils/utilities";
import { useNavigate } from "react-router-dom";


export default function ContactCard(props) {
  
  const PROFILE_COLOR = COLORS[Math.abs(hashCode(props.contact.cid)) % COLORS.length];

  const navigate = useNavigate();
  const [modalOpen, setModalOpen] = useState(false);
  const [contactData, setContactData] = useState(props.contact);
  const [checkBoxVisible, setCheckBoxVisible] = useState(false);
  const [checked, setChecked] = useState(false);
  const [isDeleted, setIsDeleted] = useState(props.isDeleted === true);
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
    if(isDeleted == false) {
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
          console.log(response);
        } else {
          console.log("error from server while deleting contact");
        }
      })
      .catch((error) => console.log(error));
    } else {
      deleteFromBinAPI({ cid: props.contact.cid })
      .then((response) => {
        if (response.status === 200) {
          removeContactFromChecklist();
          props.setContacts((prevContact) => {
            let newContacts = prevContact.filter(
              (contact) => contact.cid !== props.contact.cid
            );
            return newContacts;
          });
          console.log(response);
        } else {
          console.log("error from server while deleting contact");
        }
      })
      .catch((error) => console.log(error));
    }
  };



  const checkHandler = (event) => {
    setChecked(event.target.checked);
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
      cid : contactData.cid,
      fav : contactData.fav,
    }
    setContactData((prevcontact) => {
      updFavPayload.fav = !prevcontact.fav;
      return { ...prevcontact, fav: !prevcontact.fav };
    });

  
    updateFavAPI(updFavPayload)
      .then((response) => {
        if (response.status === 200) {
          props.setContacts((prevContacts) => {
            let temp = prevContacts.map((contact) => {
              if (contact.cid === props.contact.cid) {
                return contactData;
              } else return contact;
            });
            return temp;
          });

          console.log("fav updated!");
        } else console.log("server error");
      })
      .catch((error) => console.log(error));
  };

  const handleContactOpen = () =>{
    props.updateScore({
      cid: props.contact.cid,
    });
    console.log(props.contact);
    console.log("isDeleted " + isDeleted);
    if(isDeleted)
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
        <Avatar
          sx={{
            bgcolor : PROFILE_COLOR,
            width: "30px",
            height: "30px",
            ml : '10px',
            display: !(!checkBoxVisible && !checked) && "none",
          }}
        >
          {props.contact.name[0].toUpperCase()}
        </Avatar>
        <Checkbox
          checked={checked}
          onChange={checkHandler}
          sx={{ display: !checkBoxVisible && !checked && "none" }}
        />

        <Box sx = {{display : 'flex' , flexDirection : 'row' , flex : 1 , cursor : 'pointer'}} onClick = {handleContactOpen}>

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
            if(isDeleted == false)
              toggleFav()
          }
        }
          >
            {contactData.fav ? (
              <FavoriteIcon/>
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
