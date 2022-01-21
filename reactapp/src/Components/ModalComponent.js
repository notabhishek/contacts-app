import * as React from 'react';
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Button from '@mui/material/Button';

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
            {/* <Button onClick={handleOpen}>Open Child Modal</Button> */}
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