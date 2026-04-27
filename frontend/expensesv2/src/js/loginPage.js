import { useState } from "react";


function LoginButton({ setUsername, setPage, username }) {
      const handleLogin = () => {
        setPage('main');
        setUsername(username)
    };
   
    return <button onClick={handleLogin}>Login</button>;
}

function LoginInfo({page, setPage, setUsername}){

    return (<> <div> 
        <input placeholder='Username' onChange={(e) => setUsername(e.target.value)} ></input></div>
        <div><input placeholder='Password'></input></div></>)
}

export function LoginPage({ setPage, setUsername, username}){
 return(<>
    <LoginInfo setUsername={setUsername}></LoginInfo>
    <LoginButton setUsername={setUsername} 
    setPage={setPage} 
    username = {username}>Login</LoginButton>
 </>)
}
