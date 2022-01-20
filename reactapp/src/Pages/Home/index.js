import React, { useEffect, useState } from 'react'
import { fetchContactsAPI } from '../../Utils/APIs'
import { updateScoreAPI } from '../../Utils/APIs'
import HomeView from './HomeView'

export default function Home(){
    const [contacts , setContacts] = useState([])
    const [searchKey, setSearchKey] = useState("");
    const [orderby, setOrderBy] = useState("name");
    const [desc, setDesc] = useState(false);

    function fetchContacts(payload){
        fetchContactsAPI(payload)
            .then(response=>{
                if(response.status === 200)
                    setContacts(response.data)
                else
                    console.log("server error")
            })
            .catch(error=>console.log(error))
    }

    function updateScore(payload) {
        updateScoreAPI(payload)
            .then(response=>{
                if(response.status === 200) {
                    console.log("score updated!");
                }
                else
                    console.log("server error")
            })
            .catch(error=>console.log(error))
    }

    useEffect(()=>{
        fetchContacts({"prefix" : searchKey, "orderby" : orderby, "desc" : desc})
    },[searchKey]);

    return(
        <HomeView 
            contacts = {contacts} 
            setContacts = {setContacts}
            searchKey = {searchKey} 
            setSearchKey = {setSearchKey}
            updateScore={updateScore}
        />
    )
}