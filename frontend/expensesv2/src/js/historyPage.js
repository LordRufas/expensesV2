
import { use, useState } from "react";
import { LogoutButton, MainPageButton, Title, GetTransactionByUserButton, ErrorMessage } from "./Button";
import { TypesTable, TotalTable, TransactionTable } from "./Table";

export function HistoryPage({ username, setPage, userId }) {
  const [month, setMonth] = useState('');

  const [transactions, setTransactions] = useState([]);
   const [totals, setTotals] = useState([]);
  const [types, setTypes] = useState([]);

  const [errorMessage, setErrorMessage] = useState('');

  return (<><Title username={username}></Title>
    <input type="month" onChange={(e) => setMonth(e.target.value)} />
    <MainPageButton setPage={setPage}></MainPageButton>
    <LogoutButton setPage={setPage}></LogoutButton>

    <TypesTable info={types} setInfo={setTypes} userId={userId} setErrorMessage={setErrorMessage}></TypesTable>
    <TotalTable info={totals} setInfo={setTotals} userId={userId} setErrorMessage={setErrorMessage}></TotalTable>
    <TransactionTable info={transactions} editMode={false}></TransactionTable>
    <GetTransactionByUserButton userId={userId} date={month} setTransaction={setTransactions} setTypes={setTypes} setTotals={setTotals} setErrorMessage={setErrorMessage}></GetTransactionByUserButton>
    <TotalTable ></TotalTable>
    <ErrorMessage errorMessage={errorMessage}></ErrorMessage></>);
}