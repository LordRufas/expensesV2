
import { useState } from "react";
import { LogoutButton, MainPageButton, Title, GetTransactionByUserButton, ErrorMessage } from "./Button";
import { TransactionTable, ResumeTable } from "./Table";

export function HistoryPage({ username, setPage, userId }) {
  const [month, setMonth] = useState('');

  const [transactions, setTransactions] = useState([]);
  const [resumes, setResumes] = useState([]);

  const [errorMessage, setErrorMessage] = useState('');

  return (<>
    <Title username={username}></Title>
    <div class="headerButtons">
      <MainPageButton setPage={setPage}></MainPageButton>
      <LogoutButton setPage={setPage}></LogoutButton>
    </div>
    <div class="child">

      <input class="calendar" type="month" onChange={(e) => setMonth(e.target.value)} />
      <GetTransactionByUserButton userId={userId} date={month} setTransaction={setTransactions} setResumes={setResumes} setErrorMessage={setErrorMessage}></GetTransactionByUserButton>
      <ErrorMessage errorMessage={errorMessage}></ErrorMessage>
    </div>
    <div class="small-containerHistory">
     <div class ="table-container">
      <TransactionTable info={transactions} editMode={false}></TransactionTable>
      </div>
      <div class="child">
      <ResumeTable info={resumes} setInfo={setResumes} ></ResumeTable>
      </div>
    </div></>);
}