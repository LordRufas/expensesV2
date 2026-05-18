import { useState } from "react";
import * as API from "./Api";

export function LogoutButton({ setPage, setErrorMessage }) {
  function goToPage() {
    setPage('login');
    setErrorMessage("");
  }

  return (<><button class="headerButton" onClick={goToPage}>logout</button></>)
}

export function ReturnToLoginPage({ setPage, setErrorMessage }) {

  const handleGoBack = async () => {
    setPage('login')
    setErrorMessage("");
  };
  return (<> <button onClick={handleGoBack}>Voltar</button></>)
}


export function HistoryButton({ setPage }) {
  function goToPage() {
    setPage('history')
  }

  return (<><button class="headerButton" onClick={goToPage}>transações passadas</button></>)
}


export function MainPageButton({ setPage }) {
  function goToPage() {
    setPage('main')
  }

  return (<><button onClick={goToPage}>Voltar à pagina principal</button></>)
}

export function Title({ username }) {
  return (<h1 id="welcomeUsername">Bem vind@ {username} </h1>)
}


export function NewUserButton({ setPage }) {

  const handleNewUser = async () => {
    setPage('newUser')
  };

  return <button onClick={handleNewUser}>Novo utilizador</button>;
}



export function LoginButton({ setUsername, setPage, username, password, setPassword, SetErrorMessage, setUserId }) {

  const handleLogin = async () => {

    const response = await API.Login(username, password);

    if (response.statusCode === 200 && response.statusMessage === "OK") {
      setUserId(response.response.userId)
      setPage('main')
    }
    else if (response.statusCode === 404 && response.statusMessage === "User doesn't exist")
      SetErrorMessage("O user não existe");
    else if (response.statusCode === 401 && response.statusMessage === "Password incorrect")
      SetErrorMessage("Password incorreta");
    else
      SetErrorMessage(`Erro generico: ${response.statusMessage}`);
  };

  return <button onClick={handleLogin}>Login</button>;
}


export function CreateUserButton({ setPage, password, username, SetErrorMessage }) {

  const handleCreateUser = async () => {
    if (username === "" || username === undefined || password === "" || password === undefined) {
      SetErrorMessage('Os campos estão vazios')
      return;
    }
    const response = await API.createUser(username, password);
    if (response.statusCode === 200 && response.statusMessage === "OK") {
      setPage('login')
    } else if (response.statusCode === 200 && response.statusMessage === "User already exists") {
      SetErrorMessage('O user já existe')
    } else {
      SetErrorMessage(`Erro generico: ${response.statusMessage}`);
    }

  }
  return <button onClick={handleCreateUser}>Criar</button>;
}

export function Combobox({ info, value, setValue, placeholder }) {

  return (<>
    {info !== undefined ?
      <select
        class="calendar"
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
      </select> :
      <select
        value={value}
        onChange={(e) => setValue(e.target.value)}
        disabled={!info || info.length === 0}
      >
        <option value="">
          {`${placeholder}`}
        </option>
      </select>
    }
  </>
  );
}


export function AddTransactionButton({ userId, date, value, type, total, isRevenue, setErrorMessage, transactions, setTransaction, totals, setTotals }) {
  const addNewTransaction = async () => {
    if (date === undefined || value === undefined || type === undefined || total === undefined ||
      date === "" || value === "" || type === "" || total === "")
      setErrorMessage("Os campos não estão preenchidos")
    else {
      if (date === "")
        setErrorMessage("A data tem que estar preenchida")
      else {
        const response = await API.addTransaction(userId, type, date, value, isRevenue);
        if (response.statusCode === 200 && response.statusMessage === "Transaction added with success") {
          setErrorMessage("")
          const newTransaction = {
            date: date,
            typeName: type,
            userId: userId,
            value: value,
            isRevenue: isRevenue,
            total: total
          };

          const response = await changeTotal(value, total, totals, setTotals, isRevenue, userId, setErrorMessage);
          if (response === "ok")
            setTransaction(prev => [...transactions, newTransaction]);
        } else {
          setErrorMessage(`Erro generico: ${response.statusMessage}`);
        }
      }
    }
  }
  return (<><button class="secundaryButtons" onClick={addNewTransaction}>Adicionar</button></>)
}





export async function changeTotal(value, total, totals, setTotals, isRevenue, userId, setErrorMessage) {

  const filteredInfo = totals.filter(item => item.name !== total);
  const oldTotal = totals.filter(item => item.name === total);
  let newValue = 0;
  if (isRevenue) {
    newValue = Number(oldTotal[0].value) + Number(value);
  } else {
    newValue = Number(oldTotal[0].value) - Number(value);
  }

  newValue = parseFloat(newValue.toFixed(2));
  let newTotal = {
    date: oldTotal[0].date,
    name: oldTotal[0].name,
    userId: userId,
    value: newValue

  };




  const response = await API.updateTotals(newTotal.userId, oldTotal[0].name, newTotal.name, oldTotal[0].date, newTotal.date, Number(oldTotal[0].value), Number(newTotal.value).toFixed(2))
  if (response.statusCode === 200 && response.statusMessage === "Total updated with success") {
    const newTotals = [...filteredInfo, newTotal];
    setTotals(newTotals.sort((a, b) => {
      return a.name.localeCompare(b.name);
    }))
    return "ok";
  }
  else
    setErrorMessage(response.statusMessage)

}



export async function undoTransaction(value, total, totals, setTotals, isRevenue, userId, setErrorMessage) {

  const filteredInfo = totals.filter(item => item.name !== total);
  const oldTotal = totals.filter(item => item.name === total);
  let newValue = 0;
  if (isRevenue) {
    newValue = Number(oldTotal[0].value) - Number(value);
  } else {
    newValue = Number(oldTotal[0].value) + Number(value);
  }

  // If you need to ensure it's limited to 2 decimal places before sending to the API:
  newValue = parseFloat(newValue.toFixed(2));
  let newTotal = {
    date: oldTotal[0].date,
    name: oldTotal[0].name,
    userId: userId,
    value: newValue

  };




  const response = await API.updateTotals(newTotal.userId, oldTotal[0].name, newTotal.name, oldTotal[0].date, newTotal.date, Number(oldTotal[0].value), Number(newTotal.value).toFixed(2))
  if (response.statusCode === 200 && response.statusMessage === "Total updated with success") {
    const newTotals = [...filteredInfo, newTotal];
    setTotals(newTotals.sort((a, b) => {
      return a.name.localeCompare(b.name);
    }))
    return "ok";
  }
  else
    setErrorMessage(response.statusMessage)

}




export function AddTotalButton({ userId, date, value, name, setErrorMessage, totals, setTotals }) {

  const addNewTotal = async () => {
    if (date === undefined || value === undefined || name === undefined ||
      date === "" || value === "" || name === "")
      setErrorMessage("Os campos não estão preenchidos")
    else {
      if (totals !== undefined && totals.length > 0) {
        const exists = totals.some(
          (t) => t.name.toLowerCase() === name.toLowerCase()
        );

        if (exists) {
          setErrorMessage("Total já existe");
          return;
        }

      }

      const response = await API.addTotals(userId, name, getCurrentDate(), value);

      if (response.statusCode === 200 && response.statusMessage === "Total added with success") {

        const newTotal = {
          date: getCurrentDate(),
          userId: userId,
          value: value,
          name: name
        };

        setTotals(prev => {
          const newArray = [...(prev || []), newTotal];
          return newArray.sort((a, b) => a.name.localeCompare(b.name));
        })
        setErrorMessage("");
      }
      else
        setErrorMessage(`Erro generico: ${response.statusMessage}`);


    }
  }
  return (<><button class="secundaryButtons" onClick={addNewTotal}>Adicionar</button></>)
}

export function AddTypeButton({ userId, name, setErrorMessage, types, setTypes }) {
  const addNewType = async () => {
    if (name === undefined || name === "")
      setErrorMessage("Os campos não estão preenchidos")
    else {
      if (types !== undefined && types.length > 0) {
        const exists = types.some(
          (t) => t.name.toLowerCase() === name.toLowerCase()
        );

        if (exists) {
          setErrorMessage("Tipo já existe");
          return;
        }

      }
      const response = await API.addTypes(userId, name);

      if (response.statusCode === 200 && response.statusMessage === "Type added with success") {


        const newType = {
          userId: userId,
          name: name
        };

        setTypes(prev => {
          const newArray = [...(prev || []), newType];
          return newArray.sort((a, b) => a.name.localeCompare(b.name));
        });
        setErrorMessage("")
      }
      else
        setErrorMessage(`Erro generico: ${response.statusMessage}`);

    }
  }
  return (<><button class="secundaryButtons" onClick={addNewType}>Adicionar</button></>)
}


export function ErrorMessage({ errorMessage }) {

  return (<>
    <div>
      <label id="negativeValue">{errorMessage}</label>
    </div>
  </>)
}

export function GetTransactionByUserButton({ date, userId, setTransaction, setResumes, setErrorMessage }) {

  const fetchUserTransactions = async () => {
    if (date === "")
      setErrorMessage("A data tem que estar preenchida")
    else {
      const response = await API.fetchTransactions(userId);
      if (response.statusCode === 200 && response.statusMessage === "OK") {
        setErrorMessage("");
        if (Object.entries(response.response).length > 0 && response.response.transactions.length > 0) {
          const transactions = filterTransactions(date, response.response.transactions);
          if (transactions.length > 0) {
            setTransaction(transactions.sort((a, b) => a.typeName.localeCompare(b.typeName)))
            updateResumes(transactions, setResumes)
          }
          else{
            setErrorMessage("Não existe transações para esse mes");
            setResumes([])
            setTransaction([])
          }
        }
        else
          setErrorMessage("Não existe transações para esse mes");
      } else {
        setErrorMessage(`Erro generico: ${response.statusMessage}`);
      }
    }


  }

  return (<><button class="secundaryButtons" onClick={fetchUserTransactions}>Pesquisar</button></>)

}



export function DownloadButton({ date, userId, transactions, setErrorMessage }) {

  const downloadFile = async () => {
   const response = await API.downloadFile(userId,date);
   if(response==="error")
      setErrorMessage("Ocorreu um erro a gerar o ficheiro")
  }

  return (<>{transactions.length > 0 && <button class="secundaryButtons" onClick={downloadFile}>Download</button>}</>)

}

function filterTransactions(date, transactions) {
  return transactions.filter(t => t.date.includes(date));
}


function getCurrentDate() {
  const today = new Date();

  const month = String(today.getMonth() + 1).padStart(2, '0');
  const year = today.getFullYear();

  const formattedDate = `${year}-${month}`;

  return formattedDate;
}



export function DeleteTotalButton({ userId, data, info, setInfo, setErrorMessage }) {


  const [confirm, setConfirm] = useState(false);

  function changeButton() {
    setConfirm(true);
  }
  const deleteTotal = async () => {

    const response = await API.deleteTotal(userId, data.name, data.date, data.value);
    if (response.statusCode === 200 && response.statusMessage === "Total deleted with success") {
      setErrorMessage("");
      deleteValue(data, info, setInfo)
      setConfirm(false);
    } else {
      setErrorMessage(`Erro generico: ${response.statusMessage}`);
    }

  }
  return (<>
    {
      confirm ?
        <button class="secundaryButtons" onClick={deleteTotal}>confirmar</button> :
        <button class="secundaryButtons" onClick={changeButton}>apagar</button>

    }</>)

}

export function DeleteTypeButton({ userId, data, info, setInfo, setErrorMessage }) {

  const [confirm, setConfirm] = useState(false);

  function changeButton() {
    setConfirm(true);
  }

  const deleteType = async () => {

    const response = await API.deleteType(userId, data.name);
    if (response.statusCode === 200 && response.statusMessage === "Type deleted with success") {
      setErrorMessage("");
      deleteValue(data, info, setInfo)
      setConfirm(false);
    } else {
      setErrorMessage(`Erro generico: ${response.statusMessage}`);
    }

  }
  return (<>
    {
      confirm ?
        <button class="secundaryButtons" onClick={deleteType}>confirmar</button> :
        <button class="secundaryButtons" onClick={changeButton}>apagar</button>

    }</>)

}


export function DeleteTransactionButton({ totals, setTotals, userId, data, info, setInfo, setErrorMessage }) {

  const [confirm, setConfirm] = useState(false);

  function changeButton() {
    setConfirm(true);
  }

  const deleteTransaction = async () => {

    const response = await API.deleteTransaction(userId, data.date, data.typeName, data.value, data.isRevenue);
    if (response.statusCode === 200 && response.statusMessage === "Transaction deleted with success") {
      setErrorMessage("");
      deleteValue(data, info, setInfo)
      await undoTransaction(data.value, data.total, totals, setTotals, data.isRevenue, userId, setErrorMessage);
      setConfirm(false);
    } else {
      setErrorMessage(`Erro generico: ${response.statusMessage}`);
    }

  }
  return (<>
    {
      confirm ?
        <button class="secundaryButtons" onClick={deleteTransaction}>confirmar</button> :
        <button class="secundaryButtons" onClick={changeButton}>apagar</button>

    }</>)

}



export function deleteValue(oldValue, info, setInfo) {
  const updated = info.filter(item => item !== oldValue);
  setInfo(updated);

}

function updateResumes(transactions, setResumes) {

  const groupedTransactions = transactions.reduce((acc, current) => {
    // Find if the typeName already exists in our accumulator
    const existingType = acc.find(item => item.name === current.typeName);

    if (existingType) {
      if (current.isRevenue === "true")
        existingType.value += parseFloat(current.value);
      else
        existingType.value -= parseFloat(current.value);
    } else {
      if (current.isRevenue === "true")
        acc.push({
          name: current.typeName,
          value: parseFloat(current.value)
        });
      else
        acc.push({
          name: current.typeName,
          value: parseFloat(current.value) * -1
        });
    }

    return acc;
  }, []);

  setResumes(groupedTransactions.sort((a, b) => a.name.localeCompare(b.name)));
  return;
}


export function EditButton({ setEditMode }) {
  function handleClick() {
    setEditMode(true);
  }
  return (<><button class="secundaryButtons" onClick={handleClick}>editar</button></>)
}


export function SaveButton({ setEditMode, userId, info, setInfo, table, setErrorMessage }) {
  const handleClick = async () => {
    if (table === "totals")
      updateTotal(setEditMode, userId, info, setInfo, setErrorMessage)
    else
      updateTypes(setEditMode, userId, info, setInfo, setErrorMessage)
    setInfo(info.sort((a, b) => a.name.localeCompare(b.name)))
    setEditMode(false)
  }
  return (<><button class="secundaryButtons" onClick={handleClick}>guardar alterações</button></>)
}



export async function updateTotal(setEditMode, userId, info, setInfo, setErrorMessage) {

  const purgeResponse = await API.purgeTotals(userId)
  if (purgeResponse.statusCode === 200 && purgeResponse.statusMessage === "OK") {
    if (info !== undefined)
      for (const item of info) {

        const response = await API.addTotals(
          userId,
          item.name,
          getCurrentDate(),
          item.value
        );

        if (
          response.statusCode === 200 &&
          response.statusMessage === "Total added with success"
        ) {
          setErrorMessage()
        } else {
          setErrorMessage(response.statusMessage);
        }
      }
  }
  else
    setErrorMessage(purgeResponse.statusMessage);
}

export async function updateTypes(setEditMode, userId, info, setInfo, setErrorMessage) {

  const purgeResponse = await API.purgeTypes(userId)
  if (purgeResponse.statusCode === 200 && purgeResponse.statusMessage === "OK") {
    if (info !== undefined) {
      for (const item of info) {

        const response = await API.addTypes(
          userId,
          item.name
        );

        if (
          response.statusCode === 200 &&
          response.statusMessage === "Type added with success"
        ) {
          setErrorMessage()
        } else {
          setErrorMessage(response.statusMessage);
        }
      }
    }
  }
  else
    setErrorMessage(purgeResponse.statusMessage);

}