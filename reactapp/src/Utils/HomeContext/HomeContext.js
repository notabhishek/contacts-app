import React , {useContext , useState , createContext} from 'react'

const HomeContext = createContext()

function HomeProvider({children}){
    const [searchKey , setSearchKey] = useState('')
    const [contacts , setContacts] = useState([])
    
    const globalStateAndMethods = {
        searchContext : [searchKey , setSearchKey],
        contactsContext : [contacts , setContacts],
    }

    return <HomeContext.Provider value = {globalStateAndMethods}>{children}</HomeContext.Provider>
}


function useHomeConsumer(){
    const context = useContext(HomeContext)
    if(context === undefined){
        throw new Error("useHomeConsumer must be used within a HomeProvider")
    }

    return context;
}

export {HomeProvider , useHomeConsumer}
export default HomeContext