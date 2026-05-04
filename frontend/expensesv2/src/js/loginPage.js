import { useState } from "react";

import {LoginButton, NewUserButton} from "./Button"

function LoginInfo({page, setPage, setUsername, setPassword}){
    return (<> 
        <div> 
          <input placeholder='Username' onChange={(e) => setUsername(e.target.value)} ></input>
        </div>
        <div>
          <input type="password" onChange={(e) => setPassword(e.target.value)}  placeholder='Password'></input>
        </div></>)
}



function ErrorMessage({errorMessage, SetErrorMessage}){

  return (<>
  <div>
      {errorMessage === "username" ? <label>Username doesn't exist</label>
      :
      errorMessage === "password" ? <label>Incorrect password</label>
    :  errorMessage === "generic" ? <label>Generic error</label>
  : null}
     </div>
      </>)
}

export function LoginPage({ setPage, setUsername, username, setUserId}){
   const [password, setPassword] = useState('');
   const [errorMessage, SetErrorMessage] = useState('');
 return(<>
    <LoginInfo setUsername={setUsername}
    setPassword={setPassword}></LoginInfo>
    <LoginButton setUsername={setUsername} 
    setPage={setPage} 
    username = {username}
    password={password}
    SetErrorMessage={SetErrorMessage}
    setUserId={setUserId}>Login</LoginButton>
    <NewUserButton setPage={setPage}>Novo utilizador</NewUserButton>
    <ErrorMessage errorMessage={errorMessage} SetErrorMessage = {SetErrorMessage}></ErrorMessage>
 </>)
}
