import axios from 'axios'

const BASE_URL = 'http://localhost:8080'

function makePostRequest(endpoint , payload , auth = false){
    const url = BASE_URL + endpoint
    return axios(url , {
        method : "POST",
        headers: {
            'content-type': 'application/json', // whatever you want
          },
          data: payload,
        })

}

function makeGetRequest(endpoint , params , auth = false){
    const url = BASE_URL + endpoint
    return axios.get(url , {
        params : params,
    })
}

export function fetchContactsAPI(){
    return makeGetRequest('/contacts/getAll')
}