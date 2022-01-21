import React, { useEffect, useState } from "react";
import {
  fetchContactsAPI,
  updateScoreAPI,
} from "../../Utils/APIs";
import { useHomeConsumer } from "../../Utils/HomeContext/HomeContext";
import HomeView from "./HomeView";

export default function Home() {
  const {contactsContext , searchContext} = useHomeConsumer();
  const [contacts , setContacts] = contactsContext;
  const [searchKey ,setSearchKey] = searchContext
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
    <HomeView/>
  );
}
