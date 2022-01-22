import React, { useEffect, useState } from "react";
import { styled } from "@mui/material/styles";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import Collapse from "@mui/material/Collapse";
import IconButton from "@mui/material/IconButton";
import FavoriteIcon from "@mui/icons-material/Favorite";
import DeleteIcon from "@mui/icons-material/Delete";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import { ModalComponent } from "../ModalComponent";
import { deleteContactAPI, updateContactAPI, updateFavAPI } from "../../Utils/APIs";
import { Avatar, Checkbox } from "@mui/material";

const ExpandMore = styled((props) => {
  const { expand, ...other } = props;
  return <IconButton {...other} />;
})(({ theme, expand }) => ({
  transform: !expand ? "rotate(0deg)" : "rotate(180deg)",
  marginLeft: "auto",
  transition: theme.transitions.create("transform", {
    duration: theme.transitions.duration.shortest,
  }),
}));

export default function ContactCard(props) {
  const COLOR_FAV = "red";

  const [expanded, setExpanded] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);
  const [contactData, setContactData] = useState(props.contact);
  const [checkBoxVisible, setCheckBoxVisible] = useState(false);
  const [checked, setChecked] = useState(false);

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
  };

  const handleExpandClick = () => {
    console.log(props.contact);
    if (!expanded) {
      // update score of contact
      props.updateScore({
        cid: props.contact.cid,
      });
    }
    setExpanded(!expanded);
  };

  function inputHandler(event, type) {
    setContactData((prevcontact) => {
      return { ...prevcontact, [type]: event.target.value };
    });
  }

  const handleUpdate = () => {
    updateContactAPI(contactData)
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

          console.log("contact updated!");
        } else console.log("server error");
      })
      .catch((error) => console.log(error));
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
  useEffect(() => {
    console.log(contactData);
  }, [contactData]);

  return (
    <Card
      sx={{
        m: 1,
      }}
      onMouseEnter={() => setCheckBoxVisible(true)}
      onMouseLeave={() => setCheckBoxVisible(false)}
    >
      <ModalComponent
        open={modalOpen}
        title={"Confirm Delete"}
        text={"Delete this contact are you damn sure!!"}
        yesHandler={DeleteContact}
        noHandler={() => setModalOpen(false)}
      />
      <CardActions disableSpacing>
        <Avatar
          sx={{
            width: "30px",
            height: "30px",
            ml : '10px',
            display: !(!checkBoxVisible && !checked) && "none",
          }}
        >
          {props.contact.name[0]}
        </Avatar>
        <Checkbox
          checked={checked}
          onChange={checkHandler}
          sx={{ display: !checkBoxVisible && !checked && "none" }}
        />

        <Box sx={{ display: "flex", justifyContent: "space-between", m: 2 }}>
          <div>{props.contact.name}</div>
        </Box>
        <Box sx={{ display: "flex", justifyContent: "space-between", m: 2 }}>
          <div>{props.contact.email}</div>
        </Box>
        <Box sx={{ display: "flex", justifyContent: "space-between", m: 2 }}>
          <div>{props.contact.phone}</div>
        </Box>
        <Box sx={{ ml: "auto" }}>
          <IconButton aria-label="add to favorites" onClick={() => toggleFav()}>
            {contactData.fav ? (
              <FavoriteIcon style={{ color: COLOR_FAV }} />
            ) : (
              <FavoriteIcon />
            )}
          </IconButton>
          <IconButton aria-label="delete" onClick={() => setModalOpen(true)}>
            <DeleteIcon />
          </IconButton>
          <ExpandMore
            expand={expanded}
            onClick={handleExpandClick}
            aria-expanded={expanded}
            aria-label="show more"
          >
            <ExpandMoreIcon />
          </ExpandMore>
        </Box>
      </CardActions>
      <Collapse in={expanded} timeout="auto" unmountOnExit>
        <CardContent>
          <Box
            component="form"
            sx={{
              "& > :not(style)": { m: 1 },
            }}
            noValidate
            autoComplete="off"
          >
            <TextField
              id="outlined-name"
              label="Name"
              fullWidth
              defaultValue={props.contact.name}
              onChange={(e) => inputHandler(e, "name")}
            />
            <TextField
              id="outlined-name"
              label="Email"
              fullWidth
              defaultValue={props.contact.email}
              onChange={(e) => inputHandler(e, "email")}
            />
            <TextField
              id="outlined-name"
              label="Phone"
              fullWidth
              defaultValue={props.contact.phone}
              onChange={(e) => inputHandler(e, "phone")}
            />
            <TextField
              id="outlined-name"
              label="Address"
              fullWidth
              defaultValue={props.contact.address}
              onChange={(e) => inputHandler(e, "address")}
            />
            <Button variant="contained" color="success" onClick={handleUpdate}>
              Update
            </Button>
          </Box>
        </CardContent>
      </Collapse>
    </Card>
  );
}
