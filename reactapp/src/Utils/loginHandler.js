import { getAuthTokenFromLocalStorage, getUserAPI } from "./APIs"

export function loginHandler(userContext){
    const [userData , setUserData] = userContext

    function fetchUser(){
        getUserAPI()
            .then(response=>{
                if(response.status === 200){
                    setUserData(response.data)
                }
                else{
                    console.log('error occured while fetching user')
                }
            })
            .catch(error=>console.log(error))
    }

    const jwt = getAuthTokenFromLocalStorage();
    if(jwt) fetchUser();
    else setUserData(null);
    
}