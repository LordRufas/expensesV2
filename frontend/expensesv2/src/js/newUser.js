import { useState } from "react";
import { createUser } from "./Api";
import { CreateUserButton,ErrorMessage, ReturnToLoginPage } from "./Button";


export function NewUserPage({ setPage,errorMessage, SetErrorMessage}){
       const [password, setPassword] = useState('');
       const [username, setUsername] = useState('');

 return(<>
    <ReturnToLoginPage setPage={setPage} setErrorMessage={SetErrorMessage}></ReturnToLoginPage>
        <div> 
          <input placeholder='Username' onChange={(e) => setUsername(e.target.value)} ></input>
        </div>
        <div>
          <input type="password" onChange={(e) => setPassword(e.target.value)}  placeholder='Password'></input>
          <CreateUserButton setPage={setPage} username={username} password={password} SetErrorMessage={SetErrorMessage}></CreateUserButton>
        </div>
        <ErrorMessage errorMessage={errorMessage} ></ErrorMessage>
        </>)

}
