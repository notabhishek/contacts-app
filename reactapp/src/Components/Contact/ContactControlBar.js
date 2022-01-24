import React, { useEffect, useState } from "react";
import ContactCard from "./ContactCard";

import Dexie from "dexie";
import { useLiveQuery } from "dexie-react-hooks";
import { Box, Checkbox, IconButton } from "@mui/material";
import DeleteIcon from '@mui/icons-material/Delete';
import RefreshIcon from '@mui/icons-material/Refresh'
import { ModalComponent } from "../ModalComponent";
import { deleteContactsAPI, updateScoreAPI } from "../../Utils/APIs";
import { useHomeConsumer } from "../../Utils/HomeContext/HomeContext";
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';


export default function ContactControlBar({ contactsLength, checked, setChecked, handleCheck, contactsDelete, setModalOpen }) {

    const { orderContext, fetchContacts } = useHomeConsumer()
    const [order, setOrder] = orderContext

    const handlerOrder = (event, type) => {
        setOrder(prev => ({ ...prev, [type]: event.target.value }));
    };

    console.log(contactsLength)
    const isChecked = contactsDelete.length> 0 && contactsDelete.length === contactsLength && Object.values(checked).every(val => val === true)

    const isIndeterminate = contactsDelete.length >0 && contactsDelete.length < contactsLength
    return (
        <Box sx={{ mb: '20px', display: 'flex', alignItems: 'center' }}>
            <Checkbox inputProps={{ 'aria-label': 'controlled' }} checked={isChecked} indeterminate={isIndeterminate} onChange={handleCheck} />
            {contactsDelete.length > 0 && <IconButton aria-label="delete" onClick={() => setModalOpen(true)}>
                <DeleteIcon />
            </IconButton>}
            <IconButton onClick={fetchContacts} aria-label="delete">
                <RefreshIcon />
            </IconButton>
            <FormControl sx={{ m: 1, minWidth: 80 }}>
                <InputLabel id="demo-simple-select-label">Sort By</InputLabel>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={order.orderby}
                    label="Sort By"
                    autoWidth
                    onChange={(e) => handlerOrder(e, 'orderby')}
                >
                    <MenuItem value={'name'}>name</MenuItem>
                    <MenuItem value={'score'}>frequency</MenuItem>
                </Select>
            </FormControl>
            <FormControl sx={{ m: 1, minWidth: 80 }}>
                <InputLabel id="demo-simple-select-label">Order By</InputLabel>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={order.desc}
                    label="Order By"
                    autoWidth
                    onChange={(e) => handlerOrder(e, 'desc')}
                    sx={{ padding: 0 }}
                >
                    <MenuItem value={true}>Desc.</MenuItem>
                    <MenuItem value={false}>Asc.</MenuItem>
                </Select>
            </FormControl>
        </Box>
    )
}