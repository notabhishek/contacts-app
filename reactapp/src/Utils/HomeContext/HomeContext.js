import React, { useContext, useState, createContext } from 'react'
import { fetchContactsAPI } from '../APIs'

const HomeContext = createContext()

function HomeProvider({ children }) {
    const [searchKey, setSearchKey] = useState('')
    const [contacts, setContacts] = useState([])
    const [order, setOrder] = useState({ orderby: 'name', desc: false });

    function fetchContacts() {
        const payload = { prefix: searchKey, orderby: order.orderby, desc: order.desc }
        fetchContactsAPI(payload)
            .then((response) => {
                if (response.status === 200) setContacts(response.data);
                else console.log("server error");
            })
            .catch((error) => console.log(error));
    }

    const globalStateAndMethods = {
        searchContext: [searchKey, setSearchKey],
        contactsContext: [contacts, setContacts],
        orderContext: [order, setOrder],
        fetchContacts: fetchContacts,
    }

    return <HomeContext.Provider value={globalStateAndMethods}>{children}</HomeContext.Provider>
}


function useHomeConsumer() {
    const context = useContext(HomeContext)
    if (context === undefined) {
        throw new Error("useHomeConsumer must be used within a HomeProvider")
    }

    return context;
}

export { HomeProvider, useHomeConsumer }
export default HomeContext