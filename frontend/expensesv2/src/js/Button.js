import * as API from "./Api";
import { useState } from "react";

export function LogoutButton({setPage}){
      function goToPage(){
    setPage('login');
  }
  
    return (<><button  class="headerButton" onClick={goToPage}>logout</button></>)
  }

  export function ReturnToLoginPage({setPage, setErrorMessage}){

       const handleGoBack = async () => {
      setPage('login')
      setErrorMessage("");
  };
    return (<> <button onClick={handleGoBack}>return</button></>)
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


export function NewUserButton({setPage}) {
    
    const handleNewUser = async () => {
      setPage('newUser')
  };
   
    return <button onClick={handleNewUser}>New User</button>;
}



export function LoginButton({ setUsername, setPage, username, password, setPassword, SetErrorMessage, setUserId}) {
    
    const handleLogin = async () => {

     const response = await API.Login(username, password);
   
    if(response.statusCode === 200 && response.statusMessage === "OK"){
      setUserId(response.response.userId)
      setPage('main')
    }
    else if(response.statusCode === 404 && response.statusMessage === "User doesn't exist")
      SetErrorMessage("O user não existe");
    else if (response.statusCode === 401 && response.statusMessage === "Password incorrect")
      SetErrorMessage("Password incorreta");
    else
      SetErrorMessage(`Erro generico: ${response.statusMessage}`); 
  };
   
    return <button onClick={handleLogin}>Login</button>;
}


export function CreateUserButton({  setPage , password, username,SetErrorMessage}) {
    
    const handleCreateUser = async () => {
      if(username === "" || username === undefined || password === "" || password === undefined){
         SetErrorMessage('Os campos estão vazios')
         return;
      }
      const response = await API.createUser(username, password);
      if(response.statusCode === 200 && response.statusMessage === "OK"){
      setPage('login')
      }else if (response.statusCode === 200 && response.statusMessage === "User already exists"){
      SetErrorMessage('O user já existe')
      }else {
      SetErrorMessage(`Erro generico: ${response.statusMessage}`); 
      }
     
    }
    return <button onClick={handleCreateUser}>Criar</button>;
}


export function SaveButton({ totals, date, transactions}) {
    
    const handleLogin = async () => {

     
    }
    return <button onClick={handleLogin}>Gravar</button>;
}


export function Combobox({ info,value, setValue, placeholder }) {

  return (
    <select
      value={value}
      onChange={(e) => setValue(e.target.value)}
      disabled={!info || info.length === 0}
    >
      <option value="">
        {info.length === 0 ? "Loading..." : `${placeholder}`}
      </option>

      {info.map((item) => (
        <option key={item.name} value={item.name}>
          {item.name}
        </option>
      ))}
    </select>
  );
}

export function AddTransactionButton({ userId, date, value, type, total, isRevenue, setErrorMessage, transactions, setTransaction, totals, setTotals}){
  function addNewTransaction(){
  if(date === undefined || value === undefined || type === undefined || total === undefined ||
    date === "" || value === "" || type === "" || total === "")
    setErrorMessage("Os campos não estão preenchidos")
  else {
     setErrorMessage("")
    const newTransaction = {
  date: date,
  typeName: type,
  userId: userId,
  value: value,
  isRevenue:isRevenue ,
  total:total
};

    updateTotal(value, total,  totals, setTotals, isRevenue);
     setTransaction(prev => [...transactions, newTransaction]);
  }
}
  return(<><button onClick={addNewTransaction}>Adicionar</button></>)
}



export function updateTotal(addValue, oldValue, totals, setTotals,isRevenue){

  const oldTotal =  totals.filter(item => {
   if (item.name === oldValue) {
    return item;
    }});

 const filteredInfo = totals.filter(item => {
   if (item.name !== oldValue) {
    return item;
    }});
  let newTotal = [];
let newValue = isRevenue
  ? Number(oldTotal[0].value) + Number(addValue)
  : Number(oldTotal[0].value) - Number(addValue);



   newTotal = {
  date: oldTotal[0].date,
  name: oldTotal[0].name ,
   userId: oldTotal[0].userId,
   value: newValue.toFixed(2)
  
};

const newTotals = [...filteredInfo, newTotal];

     setTotals(newTotals.sort((a, b) => {
  return a.name.localeCompare(b.name);
}))
}


export function AddTotalButton({ userId, date, value,  name, setErrorMessage, totals, setTotals}){
  function addNewTotal(){
  if(date === undefined || value === undefined || name === undefined ||
    date === "" || value === "" || name === ""  )
    setErrorMessage("Os campos não estão preenchidos")
  else {
       const exists = totals.some(
    (t) => t.name.toLowerCase() === name.toLowerCase()
  );

  if (exists) {
    setErrorMessage("Total já existe");
    return;
  }
     setErrorMessage("")
    const newTotal = {
  date: date,
  userId: userId,
  value: value ,
  name: name
};

     setTotals(prev => [...totals, newTotal]);
  }
}
  return(<><button onClick={addNewTotal}>Adicionar</button></>)
}

export function AddTypeButton({ userId, name, setErrorMessage, types, setTypes}){
  function addNewType(){
  if( name === undefined || name === ""  )
    setErrorMessage("Os campos não estão preenchidos")
  else {
     const exists = types.some(
    (t) => t.name.toLowerCase() === name.toLowerCase()
  );

  if (exists) {
    setErrorMessage("Tipo já existe");
    return;
  }
     setErrorMessage("")
    const newType = {
  userId: userId,
  name: name 
};

     setTypes(prev => [...types, newType]);
  }
}
  return(<><button onClick={addNewType}>Adicionar</button></>)
}


export function ErrorMessage({errorMessage}){

  return (<>
  <div>
       <label>{errorMessage}</label>
     </div>
      </>)
}

export function GetTransactionByUserButton({date, userId, setTransaction, setErrorMessage}){

  const fetchUserTransactions = async () => {
    if(date === "")
      setErrorMessage("A data tem que estar preenchida")
    else{
      const response = await API.fetchTransactions(userId);
      if(response.statusCode === 200 && response.statusMessage === "OK" && response.response.transactions.length >0){
      setErrorMessage("");
      const transactions = filterTransactions(date,  response.response.transactions);
      if(transactions.length > 0)
      setTransaction(transactions)
    else 
        setErrorMessage("Não existe transações para esse mes");
      }else {
      setErrorMessage(`Erro generico: ${response.statusMessage}`); 
      }
    }
      

  }

  return (<><button onClick={fetchUserTransactions}>Pesquisar</button></>)

}

function filterTransactions(date, transactions){
  const formattedDate = date.split('-').reverse().join('/');
    return transactions.filter(t => t.date.includes(formattedDate));
}