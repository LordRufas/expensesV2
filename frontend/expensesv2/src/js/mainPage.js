import { TotalTable, TransactionTable, TypesTable } from "./Table";
import { LogoutButton, AdminButton, HistoryButton, Title } from "./Button";
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
    <h2>Totais atuais</h2>
    <TotalTable info={totals}></TotalTable>
  
    <h2>Transações</h2>
    <TransactionTable></TransactionTable>

    <h2>Tipos</h2>
    <TypesTable info={types}></TypesTable>
  </div>

  </>);
}