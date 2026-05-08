import { useState } from "react";
import { createUser } from "./Api";
import { CreateUserButton, ErrorMessage, ReturnToLoginPage } from "./Button";


export function NewUserPage({ setPage, errorMessage, SetErrorMessage }) {
  const [password, setPassword] = useState('');
  const [username, setUsername] = useState('');

  return (<>
  <div class="parent">
    <h1>Novo utilizador</h1>
    <div>
      <input class="loginInput" placeholder='Username' onChange={(e) => setUsername(e.target.value)} ></input>
    </div>
    <div>
      <input class="loginInput" type="password" onChange={(e) => setPassword(e.target.value)} placeholder='Password'></input>
    </div>
    <div class="container">
      <CreateUserButton setPage={setPage} username={username} password={password} SetErrorMessage={SetErrorMessage}></CreateUserButton>
      <ReturnToLoginPage setPage={setPage} setErrorMessage={SetErrorMessage}></ReturnToLoginPage>
    </div>
    <ErrorMessage errorMessage={errorMessage} ></ErrorMessage>
</div>
  </>)

}
