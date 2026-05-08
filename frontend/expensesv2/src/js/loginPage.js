import { useState } from "react";

import { LoginButton, NewUserButton, ErrorMessage } from "./Button"
import '../css/style.css';

function LoginInfo({ page, setPage, setUsername, setPassword }) {
  return (<>

      <div>
        <input class="loginInput" placeholder='Username' onChange={(e) => setUsername(e.target.value)} ></input>
      </div>
      <div>
        <input class="loginInput" type="password" onChange={(e) => setPassword(e.target.value)} placeholder='Password'></input>
      </div>
    </>)
}


export function LoginPage({ setPage, setUsername, username, setUserId, errorMessage, SetErrorMessage }) {
  const [password, setPassword] = useState('');
  return (<>
   <div class="parent"  >
    <h1>Despesas</h1>
    <div>
    <LoginInfo setUsername={setUsername}
      setPassword={setPassword}></LoginInfo>
      </div>
      <div class="container">
    <LoginButton setUsername={setUsername}
      setPage={setPage}
      username={username}
      password={password}
      SetErrorMessage={SetErrorMessage}
      setUserId={setUserId}>Login</LoginButton>
    <NewUserButton setPage={setPage} errorMessage={errorMessage} SetErrorMessage={SetErrorMessage}>Novo utilizador</NewUserButton>
   </div>
    <ErrorMessage errorMessage={errorMessage} SetErrorMessage={SetErrorMessage}></ErrorMessage>
    </div>
  </>)
}
