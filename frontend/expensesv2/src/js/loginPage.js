import { useState } from "react";


function LoginButton({ setUsername, setPage, username }) {
    
    const handleLogin = async () => {
   
      setPage('main')
      // 1. Make the REST call
     /*  try {const response = await fetch('https://api.example.com/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username: username })
      });

      if (response.ok) {
        const data = await response.json();
        console.log("Login successful:", data);
        
        // 2. Trigger the redirect/state change in App.js
        setPage('main');
        setUsername(username)
      } else {
        alert("Login failed!");
      }
    } catch (error) {
      console.error("Network error:", error);
    }*/
  };
   
    return <button onClick={handleLogin}>Login</button>;
}

function LoginInfo({page, setPage, setUsername}){

    return (<> <div> 
        <input placeholder='Username' onChange={(e) => setUsername(e.target.value)} ></input></div>
        <div><input placeholder='Password'></input></div></>)
}

function NewUserButton({  setPage }) {
    
    const handleNewUser = async () => {
      setPage('newUser')
  };
   
    return <button onClick={handleNewUser}>New User</button>;
}

export function LoginPage({ setPage, setUsername, username}){
 return(<>
    <LoginInfo setUsername={setUsername}></LoginInfo>
    <LoginButton setUsername={setUsername} 
    setPage={setPage} 
    username = {username}>Login</LoginButton>
    <NewUserButton setPage={setPage}>Novo utilizador</NewUserButton>
 </>)
}
