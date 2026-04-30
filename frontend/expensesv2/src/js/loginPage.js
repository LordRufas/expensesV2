import { useState } from "react";

 const data = [{"username": "a"}, {"password": "a"}];

function LoginButton({ setUsername, setPage, username, password, setPassword, SetErrorMessage }) {
    
    const handleLogin = async () => {
   
      if(username === data[0].username && password === data[1].password)
      setPage('main')
    else if(username !== data[0].username)
      SetErrorMessage("username");
    else if (password !== data[1].password)
     SetErrorMessage("password");
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

function LoginInfo({page, setPage, setUsername, setPassword}){
    return (<> 
        <div> 
          <input placeholder='Username' onChange={(e) => setUsername(e.target.value)} ></input>
        </div>
        <div>
          <input type="password" onChange={(e) => setPassword(e.target.value)}  placeholder='Password'></input>
        </div></>)
}

function NewUserButton({  setPage }) {
    
    const handleNewUser = async () => {
      setPage('newUser')
  };
   
    return <button onClick={handleNewUser}>New User</button>;
}

function ErrorMessage({errorMessage, SetErrorMessage}){

  return (<>
  <div>
      {errorMessage === "username" ? <label>Username doesn't exist</label>
      :
      errorMessage === "password" ? <label>Incorrect password</label>
    : null}
     </div>
      </>)
}

export function LoginPage({ setPage, setUsername, username}){
   const [password, setPassword] = useState('');
   const [errorMessage, SetErrorMessage] = useState('');
 return(<>
    <LoginInfo setUsername={setUsername}
    setPassword={setPassword}></LoginInfo>
    <LoginButton setUsername={setUsername} 
    setPage={setPage} 
    username = {username}
    password={password}
    SetErrorMessage={SetErrorMessage}>Login</LoginButton>
    <NewUserButton setPage={setPage}>Novo utilizador</NewUserButton>
    <ErrorMessage errorMessage={errorMessage} SetErrorMessage = {SetErrorMessage}></ErrorMessage>
 </>)
}
