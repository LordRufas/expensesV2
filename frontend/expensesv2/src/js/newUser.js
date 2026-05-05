import { useState } from "react";
import { createUser } from "./Api";
import { CreateUserButton } from "./Button";

function ReturnToLoginPage({setPage}){

       const handleGoBack = async () => {
      setPage('login')
  };
    return (<> <button onClick={handleGoBack}>return</button></>)
}

function ErrorMessage({errorMessage}){

  return (<>
  <div>
      {errorMessage === "404" ? <label>User não existe</label>
      :
      errorMessage === "401" ? <label>Password incorreta</label>:
      errorMessage === "400" ? <label>User já existe</label>
    :  errorMessage === "500" ? <label>Generic error</label>
  : null}
     </div>
      </>)
}

export function NewUserPage({ setPage,errorMessage, SetErrorMessage}){
       const [password, setPassword] = useState('');
       const [username, setUsername] = useState('');

 return(<>
    <ReturnToLoginPage setPage={setPage}></ReturnToLoginPage>
        <div> 
          <input placeholder='Username' onChange={(e) => setUsername(e.target.value)} ></input>
        </div>
        <div>
          <input type="password" onChange={(e) => setPassword(e.target.value)}  placeholder='Password'></input>
          <CreateUserButton setPage={setPage} username={username} password={password} SetErrorMessage={SetErrorMessage}></CreateUserButton>
        </div>
        <ErrorMessage errorMessage={errorMessage}></ErrorMessage>
        </>)

}
