import React, { useEffect, useState } from 'react'
import { fetchContactsAPI } from '../../Utils/APIs'
import HomeView from './HomeView'

export default function Home(){
    const [contacts , setContacts] = useState([])

    function fetchContacts(){
        fetchContactsAPI()
            .then(response=>{
                if(response.status === 200)
                    setContacts(response.data)
                else
                    console.log("server error")
            })
            .catch(error=>console.log(error))
    }

    useEffect(()=>{
        fetchContacts()
    },[])

    return(
        <HomeView contacts = {contacts} />
    )
}