import React, { useEffect, useState } from "react";
import {
  fetchContactsAPI,
  updateScoreAPI,
} from "../../Utils/APIs";
import { useHomeConsumer } from "../../Utils/HomeContext/HomeContext";
import HomeView from "./HomeView";

export default function Home() {
  const {contactsContext , searchContext , fetchContacts} = useHomeConsumer();
  const [contacts , setContacts] = contactsContext;
  const [searchKey ,setSearchKey] = searchContext

  useEffect(() => {
    fetchContacts();
  }, [searchKey]);

  return (
    <HomeView/>
  );
}
