import React, { useState } from "react";
import { styled } from "@mui/material/styles";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import Collapse from "@mui/material/Collapse";
import IconButton from "@mui/material/IconButton";
import FavoriteIcon from "@mui/icons-material/Favorite";
// import ShareIcon from "@mui/icons-material/Share";
import DeleteIcon from '@mui/icons-material/Delete';
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import { ModalComponent } from "../ModalComponent";
import { deleteContactAPI, updateContactAPI } from "../../Utils/APIs";

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
  const [expanded, setExpanded] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);
  const [contactData , setContactData] = useState(props.contact)

  const DeleteContact = () => {
    setModalOpen(false)
    deleteContactAPI({contactId : props.contact.cid})
      .then(response=>{
        if(response.status===200){
          console.log(response)
        }
        else{
          console.log('error from server while deleting contact')
        }
      })
      .catch(error=>console.log(error))
  }

  const handleExpandClick = () => {
    console.log(props.contact);
    if (!expanded) { // update score of contact
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
    // fetcj
    updateContactAPI(contactData)
      .then((response) => {
        if (response.status === 200) {

          props.setContacts((prevContacts) => {
            let temp = prevContacts.map((contact) => {
              if ((contact.cid === props.contact.cid)) {
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

  return (
    <Card
      sx={{
        m: 1,
      }}
    >
      <ModalComponent 
          open={modalOpen} 
          title={"Confirm Delete"} 
          text={"Delete this contact are you damn sure!!"} 
          yesHandler={DeleteContact}
          noHandler={()=>setModalOpen(false)}
        />
      <CardActions disableSpacing>
        

        <Box sx={{ display: "flex", justifyContent: "space-between", m: 2 }}>
          <div>{props.contact.name}</div>
        </Box>
        <Box sx={{ display: "flex", justifyContent: "space-between", m: 2 }}>
          <div>{props.contact.email}</div>
        </Box>
        <Box sx={{ display: "flex", justifyContent: "space-between", m: 2 }}>
          <div>{props.contact.phone}</div>
        </Box>
        <Box sx={{ ml: 'auto' }}>
          <IconButton aria-label="add to favorites">
            <FavoriteIcon />
          </IconButton>
          <IconButton aria-label="share" onClick={()=>setModalOpen(true)}>
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
