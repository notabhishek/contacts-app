import * as React from "react";
import { styled } from "@mui/material/styles";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import Collapse from "@mui/material/Collapse";
import IconButton from "@mui/material/IconButton";
import FavoriteIcon from "@mui/icons-material/Favorite";
import ShareIcon from "@mui/icons-material/Share";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";

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
  const [expanded, setExpanded] = React.useState(false);

  const handleExpandClick = () => {
    console.log(props.contact);
    setExpanded(!expanded);
  };

  return (
    <Card
      sx={
        {
          // maxWidth: 345
          m:1
        }
      }
    >
      <CardActions disableSpacing>
        <IconButton aria-label="add to favorites">
          <FavoriteIcon />
        </IconButton>
        <IconButton aria-label="share">
          <ShareIcon />
        </IconButton>
        <Box sx={{ display: "flex", justifyContent: "space-between", m: 2 }}>
          <div>{props.contact.name}</div>
        </Box>
        <Box sx={{ display: "flex", justifyContent: "space-between", m: 2 }}>
          <div>{props.contact.email}</div>
        </Box>
        <Box sx={{ display: "flex", justifyContent: "space-between", m: 2 }}>
          <div>{props.contact.phone}</div>
        </Box>
        <ExpandMore
          expand={expanded}
          onClick={handleExpandClick}
          aria-expanded={expanded}
          aria-label="show more"
        >
          <ExpandMoreIcon />
        </ExpandMore>
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
              onChange={(e) => console.log(e.target.value)}
            />
            <TextField
              id="outlined-name"
              label="Email"
              fullWidth
              defaultValue={props.contact.email}
              onChange={(e) => console.log(e.target.value)}
            />
            <TextField
              id="outlined-name"
              label="Phone"
              fullWidth
              defaultValue={props.contact.phone}
              onChange={(e) => console.log(e.target.value)}
            />
            <TextField
              id="outlined-name"
              label="Address"
              fullWidth
              defaultValue={props.contact.address}
              onChange={(e) => console.log(e.target.value)}
            />
            <Button variant="contained" color="success">
              Update
            </Button>
          </Box>
        </CardContent>
      </Collapse>
    </Card>
  );
}
