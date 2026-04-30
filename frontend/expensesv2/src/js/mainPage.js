import { TotalTable, TransactionTable, TypesTable } from "./Table";
import { LogoutButton, AdminButton, HistoryButton, Title } from "./Button";
import {totals, types, transactions} from "./data"

export function MainPage({username, setPage}) {


  return (<><Title username= {username}></Title>
    <div>
  <AdminButton setPage={setPage}></AdminButton>
  <HistoryButton setPage={setPage}></HistoryButton>
  <LogoutButton setPage={setPage}></LogoutButton>
  </div>
  <div>
    <h2>Totais atuais</h2>
    <TotalTable info={totals}></TotalTable>
  
    <h2>Transações</h2>
    <TransactionTable info={transactions}></TransactionTable>

    <h2>Tipos</h2>
    <TypesTable info={types}></TypesTable>
  </div>

  </>);
}