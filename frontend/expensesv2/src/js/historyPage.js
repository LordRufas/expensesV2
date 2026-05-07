
import { use, useState } from "react";
import { LogoutButton, MainPageButton, Title, GetTransactionByUserButton, ErrorMessage } from "./Button";
import { TransactionTable } from "./Table";

export function HistoryPage({ username, setPage, userId }) {
  const [month, setMonth] = useState('');

  const [transactions, setTransactions] = useState([]);

  const [errorMessage, SetErrorMessage] = useState('');

  return (<><Title username={username}></Title>
    <input type="month" onChange={(e) => setMonth(e.target.value)} />
    <MainPageButton setPage={setPage}></MainPageButton>
    <LogoutButton setPage={setPage}></LogoutButton>

    <TransactionTable info={transactions} editMode={false}></TransactionTable>
    <GetTransactionByUserButton userId={userId} date={month} setTransaction={setTransactions} setErrorMessage={SetErrorMessage}></GetTransactionByUserButton>
    <ErrorMessage errorMessage={errorMessage}></ErrorMessage></>);
}