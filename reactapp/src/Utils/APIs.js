import axios from 'axios'

const BASE_URL = 'http://localhost:8080'

const storageKey = 'jwt-token';

export function getAuthTokenFromLocalStorage(){
    const jwt = localStorage.getItem(storageKey)
    if(jwt) return jwt;
    return null;
}


function makePostRequest(endpoint , payload , auth = false){
    let headers = {
        'content-type': 'application/json', // whatever you want
      }
    if(auth){
        const jwt = getAuthTokenFromLocalStorage()
        if(jwt) headers['Authorization'] =   `Bearer ${jwt}`
    }
    const url = BASE_URL + endpoint
    return axios(url , {
        method : "POST",
        headers : headers,
          data: payload,
        })
}

function makeGetRequest(endpoint , params , auth = false){
    let headers = {
        'content-type': 'application/json', // whatever you want
      }
    if(auth){
        const jwt = getAuthTokenFromLocalStorage()
        if(jwt) headers['Authorization'] =   `Bearer ${jwt}`
    }
    console.log(headers)
    const url = BASE_URL + endpoint
    return axios.get(url , {
        headers : headers,
        params : params,
    })
}

export function fetchContactsAPI(payload){
    return makePostRequest('/contacts/search', payload , true)
}

export function addContactAPI(payload){
    return makePostRequest('/contacts/add' , payload , true)
}

export function updateScoreAPI(payload){
    return makePostRequest('/contacts/updateScore', payload , true)
}

export function deleteContactAPI(payload){
    return makePostRequest('/contacts/deleteContact', payload , true)
}

export function updateFavAPI(payload) {
    return makePostRequest('/contacts/updateFav', payload, true)
}

export function updateContactAPI(payload) {
    return makePostRequest('/contacts/update', payload , true)
}


export function deleteContactsAPI(payload){
    return makePostRequest('/contacts/deleteContactList', payload, true)
}

export function registerUserAPI(payload) {
    return makePostRequest('/auth/register', payload);
}

export function loginUserAPI(payload) {
    return makePostRequest('/auth/login', payload);
}

export function getUserAPI(){
    return makeGetRequest('/contacts/getUser' , {} , true)
}

export function updateUserAPI(payload){
    return makePostRequest('/contacts/updateUser', payload , true)
}