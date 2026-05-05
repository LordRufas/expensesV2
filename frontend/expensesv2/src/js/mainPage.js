import { TotalTable, TransactionTable, TypesTable } from "./Table";
import { LogoutButton, AdminButton, HistoryButton, Title, SaveButton } from "./Button";
import "../css/App.css"
import { use, useState , useEffect} from "react";
import { fetchTypes ,fetchTotals, fetchData } from "./Api";

export function MainPage({username, setPage, userId}) {

  const [totals, setTotals] = useState('');
  const [types, setTypes] = useState('');
  useEffect(() => {
  fetchData({
    userId,
    setTotals,
    setTypes
  });
}, [userId,
    setTotals,
    setTypes]);


  return (<><Title username= {username}></Title>
    <div >
  <AdminButton setPage={setPage}></AdminButton>
  <HistoryButton setPage={setPage}></HistoryButton>
  <LogoutButton setPage={setPage}></LogoutButton>
  </div> 
  <div>
    <TotalDiv info={totals}></TotalDiv>
    <TransactionDiv></TransactionDiv>
    <TypeDiv info={types}></TypeDiv>
    </div>
    <div>
    <SaveButton></SaveButton>
    </div>
  </>);
}

function TransactionDiv(){
  return(<>    <h2>Transações</h2>
    <TransactionTable></TransactionTable>
    <input placeholder="data"></input> <input placeholder="valor"></input>
  <input placeholder="tipo"></input>
  <input placeholder="total"></input>
  <input placeholder="+/-"></input>
  <button>Adicionar</button></>)
}

function TotalDiv({totals}){
  return(<>
    <h2>Totais atuais</h2>
    <TotalTable info={totals}></TotalTable>
      <input placeholder="nome"></input>
   <input placeholder="valor"></input>
  <button>Adicionar</button></>)
}

function TypeDiv({types}){
  return(<>
    <h2>Tipos</h2>
    <TypesTable info={types}></TypesTable>
    <input placeholder="nome"></input>
  <button>Adicionar</button></>)
}