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
      {errorMessage === "404" ? <label>User não existe</label>
      :
      errorMessage === "401" ? <label>Password incorreta</label>
    :  errorMessage === "500" ? <label>Erro generico</label>
    : errorMessage === "200" ? <label>User criado com sucesso</label>
  : null}
     </div>
      </>)
}

export function LoginPage({ setPage, setUsername, username, setUserId, errorMessage, SetErrorMessage}){
   const [password, setPassword] = useState('');
 return(<>
    <LoginInfo setUsername={setUsername}
    setPassword={setPassword}></LoginInfo>
    <LoginButton setUsername={setUsername} 
    setPage={setPage} 
    username = {username}
    password={password}
    SetErrorMessage={SetErrorMessage}
    setUserId={setUserId}>Login</LoginButton>
    <NewUserButton setPage={setPage} errorMessage={errorMessage} SetErrorMessage={SetErrorMessage}>Novo utilizador</NewUserButton>
    <ErrorMessage errorMessage={errorMessage} SetErrorMessage = {SetErrorMessage}></ErrorMessage>
 </>)
}
