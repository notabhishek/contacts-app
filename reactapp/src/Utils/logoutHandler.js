export function logoutHandler(userContext){
    const [userData , setUserData] = userContext

    setUserData(null)
    localStorage.clear();
}