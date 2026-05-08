
import { use, useState } from "react";
import { LogoutButton, MainPageButton, Title, GetTransactionByUserButton, ErrorMessage } from "./Button";
import { TypesTable, TotalTable, TransactionTable , ResumeTable} from "./Table";

export function HistoryPage({ username, setPage, userId }) {
  const [month, setMonth] = useState('');

  const [transactions, setTransactions] = useState([]);
  const [resumes, setResumes] = useState([]);

  const [errorMessage, setErrorMessage] = useState('');

  return (<><Title username={username}></Title>
    <input type="month" onChange={(e) => setMonth(e.target.value)} />
    <MainPageButton setPage={setPage}></MainPageButton>
    <LogoutButton setPage={setPage}></LogoutButton>

    <ResumeTable info={resumes} setInfo={setResumes} ></ResumeTable>
    <TransactionTable info={transactions} editMode={false}></TransactionTable>
    <GetTransactionByUserButton userId={userId} date={month} setTransaction={setTransactions} setResumes={setResumes} setErrorMessage={setErrorMessage}></GetTransactionByUserButton>
    <ErrorMessage errorMessage={errorMessage}></ErrorMessage></>);
}