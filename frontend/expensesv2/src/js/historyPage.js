
import { useState } from "react";
import { LogoutButton, MainPageButton,Title } from "./Button";
import { TransactionTable } from "./Table";

export function HistoryPage({username, setPage,userId}) {
  const [month, setMonth] = useState('');

  return (<><Title username= {username}></Title>
  <input type="month"  onChange={(e) => setMonth(e.target.value)} />
  <button>Pesquisar</button>
   <MainPageButton setPage={setPage}></MainPageButton>
  <LogoutButton setPage={setPage}></LogoutButton>

  <TransactionTable></TransactionTable></>);
}