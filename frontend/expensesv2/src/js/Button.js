import { Login, createUser } from "./Api";

export function LogoutButton({setPage, SetErrorMessage}){
      function goToPage(){
    setPage('login')
  }
  
    return (<><button  class="headerButton" onClick={goToPage}>logout</button></>)
  }

export function HistoryButton({setPage}){
      function goToPage(){
    setPage('history')
  }
  
    return (<><button  class="headerButton" onClick={goToPage}>transações passadas</button></>)
}


export function MainPageButton({setPage}){
      function goToPage(){
    setPage('main')
  }
  
    return (<><button class="headerButton" on onClick={goToPage}>Voltar à pagina principal</button></>)
}

export function Title ({username}){
  return (<h1 id="welcomeUsername">Bem vind@ {username} </h1>)
}


export function NewUserButton({  setPage}) {
    
    const handleNewUser = async () => {
      setPage('newUser')
  };
   
    return <button onClick={handleNewUser}>New User</button>;
}


export function LoginButton({ setUsername, setPage, username, password, setPassword, SetErrorMessage, setUserId}) {
    
    const handleLogin = async () => {

     const response = await Login(username, password);
   
    if(response.statusCode === 200 && response.statusMessage === "OK"){
      setUserId(response.response.userId)
     setPage('main')
    }
    else if(response.statusCode === 404 && response.statusMessage === "User doesn't exist")
      SetErrorMessage("404");
    else if (response.statusCode === 401 && response.statusMessage === "Password incorrect")
      SetErrorMessage("401");
    else
      SetErrorMessage("500"); 
  };
   
    return <button onClick={handleLogin}>Login</button>;
}


export function CreateUserButton({  setPage , password, username,SetErrorMessage}) {
    
    const handleCreateUser = async () => {
      const response = await createUser(username, password);
      if(response.statusCode === 200 && response.statusMessage === "OK"){
      setPage('login')
      SetErrorMessage('200')
      }else if (response.statusCode === 200 && response.statusMessage === "User already exists"){
      SetErrorMessage('400')
      }else {
      SetErrorMessage('500')
      }
     
    }
    return <button onClick={handleCreateUser}>Criar</button>;
}

export function DeleteTransactionButton({ userId, date, typeName, value, isRevenue}) {
    
    const handleLogin = async () => {

     const response = await Login();
    }
    return <button onClick={handleLogin}>Login</button>;
}

export function SaveButton({ totals, date, transactions}) {
    
    const handleLogin = async () => {

     
    }
    return <button onClick={handleLogin}>Gravar</button>;
}
