import * as React from 'react';
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Button from '@mui/material/Button';
import { uploadCSVContactsAPI } from '../Utils/APIs';
import { Typography } from '@mui/material';
import { CircularProgress } from '@mui/material';
import { useHomeConsumer } from '../Utils/HomeContext/HomeContext';

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 800,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    pt: 2,
    px: 4,
    pb: 3,
};

export function ModalComponent({ open, setOpen, title, text, yesHandler, noHandler }) {

    return (
        <React.Fragment>
            <Modal
                hideBackdrop
                open={open}
                onClose={() => setOpen(false)}
                aria-labelledby="child-modal-title"
                aria-describedby="child-modal-description"
            >
                <Box sx={{ ...style, width: 400 }}>
                    <h2 id="child-modal-title">{title}</h2>
                    <p id="child-modal-description">
                        {text}
                    </p>
                    <Box display={'inline-block'}>
                        <Button onClick={yesHandler}>Yes</Button>
                        <Button onClick={noHandler}>No</Button>
                    </Box>
                </Box>
            </Modal>
        </React.Fragment>
    );
}

export function UploadContactComponent({ open, setOpen}) {

    const [csv , setCsv] = React.useState('')
    const [loading , setLoading] = React.useState(false)
    const handleFileSelect=(e)=>{
        setCsv(e.target.files[0])
    }

    const {fetchContacts} = useHomeConsumer();

    const fileUpload = () =>{
        setLoading(true)
        let formData = new FormData()
        formData.append('file' , csv)
        uploadCSVContactsAPI(formData).then(response=>{
            console.log(response)
            if(response.status===200){
                setCsv('')
                setOpen(false)
                fetchContacts()
            }
            else{

            }
            setLoading(false)
        })
        .catch(error=>{
            console.log(error)
            setLoading(false)
        })
    }

    return (
        <React.Fragment>
            <Modal
                hideBackdrop
                open={open}
                onClose={() => setOpen(false)}
                aria-labelledby="child-modal-title"
                aria-describedby="child-modal-description"
            >
                <Box sx={{ ...style, width: 400 , minHeight : 200 }} >
                
                    <h2 id="child-modal-title">Import contacts</h2>
                    <p id="child-modal-description">
                        Upload CSV contacts file (.csv)
                    </p>
                    {loading ? <CircularProgress/> :<Box>
                    <Box display={'flex'} flexDirection={'row'} alignItems={'center'}>
                        <label htmlFor="upload-csv">
                            <input
                                style={{ display: 'none' }}
                                id="upload-csv"
                                name="upload-csv"
                                type="file"
                                onChange={handleFileSelect}
                            />

                            <Button sx = {{minWidth : '125px'}} color="primary" variant="contained" component="span">
                                Select File
                            </Button>
                        </label>
                        {csv.name && <Typography sx = {{ml : 2}}>{csv.name}</Typography>}
                    </Box>
                    <Box sx = {{display : 'flex' , justifyContent : 'flex-end'}}>
                        <Button onClick = {()=>{setCsv(''); setOpen(false)}}>cancel</Button>
                        <Button onClick = {fileUpload}>import</Button>
                    </Box>
                    </Box>}
                </Box>
            </Modal>
        </React.Fragment>
    )
}