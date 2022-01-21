import React from 'react'
import { getAuthTokenFromLocalStorage } from '../Utils/APIs';
import { Navigate , Outlet , location } from 'react-router-dom';

export default function ProtectedRoute() {
    const jwt = getAuthTokenFromLocalStorage();
  
    if (!jwt) {
      // Redirect them to the /login page, but save the current location they were
      // trying to go to when they were redirected. This allows us to send them
      // along to that page after they login, which is a nicer user experience
      // than dropping them off on the home page.
      return <Navigate to="/login" />;
    }
  
    return <Outlet />;
  }