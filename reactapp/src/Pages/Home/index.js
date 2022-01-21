import React, { useEffect, useState } from "react";
import {
  fetchContactsAPI,
  updateScoreAPI,
} from "../../Utils/APIs";
import { useAppConsumer } from "../../Utils/AppContext/AppContext";
import HomeView from "./HomeView";

export default function Home() {
  const {contactsContext} = useAppConsumer();
  const [contacts , setContacts] = contactsContext;
  const [searchKey ,setSearchKey] = useState('')
  const [orderby, setOrderBy] = useState("name");
  const [desc, setDesc] = useState(false);

  function fetchContacts() {
    const payload= { prefix: searchKey, orderby: orderby, desc: desc }
    fetchContactsAPI(payload)
      .then((response) => {
        if (response.status === 200) setContacts(response.data);
        else console.log("server error");
      })
      .catch((error) => console.log(error));
  }


  useEffect(() => {
    fetchContacts();
  }, [searchKey]);

  return (
    <HomeView
      searchKey={searchKey}
      setSearchKey={setSearchKey}
    />
  );
}
