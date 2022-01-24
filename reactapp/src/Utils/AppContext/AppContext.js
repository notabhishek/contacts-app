import React , {useContext , useState , createContext, useEffect} from 'react'
import { getUserAPI , getAuthTokenFromLocalStorage} from '../APIs'

const AppContext = createContext()

function AppProvider({children}){
    const [userData , setUserData] = useState(null)
    const [theme , setTheme] = useState('lightTheme')
    const [alertData , setAlertData] = useState({open : false , severity : '' , errorMessage : ''})

    const changeTheme = () =>{
        setTheme(prevTheme=>{
            if(prevTheme === 'lightTheme'){ 
                localStorage.setItem('theme' , 'darkTheme')
                return 'darkTheme'
            }
            else{ 
                localStorage.setItem('theme' , 'lightTheme')
                return 'lightTheme'
            }
        })
    }

    const fetchUser = () =>{
        getUserAPI()
            .then(response=>{
                if(response.status === 200){
                    setUserData(response.data)
                }
                else{
                    console.log('error in fetching user')
                }
            })
            .catch(error=>console.log(error))
    }

    const setAlertPop = (data) =>{
        setAlertData(data)
    }

    useEffect(()=>{
        const jwt = getAuthTokenFromLocalStorage();
        if(jwt) fetchUser()

        const theme = localStorage.getItem('theme')
        if(theme) setTheme(theme)
    },[])
    
    const globalStateAndMethods = {
        userContext : [userData , setUserData],
        themeContext : [theme , setTheme],
        alertContext : [alertData , setAlertData],
        setAlertPop : setAlertPop,
        changeTheme : changeTheme
    }

    return <AppContext.Provider value = {globalStateAndMethods}>{children}</AppContext.Provider>
}


function useAppConsumer(){
    const context = useContext(AppContext)
    if(context === undefined){
        throw new Error("useAppConsumer must be used within a AppProvider")
    }

    return context;
}

export {AppProvider , useAppConsumer}
export default AppContext