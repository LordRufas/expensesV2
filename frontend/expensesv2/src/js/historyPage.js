
import { LogoutButton, AdminButton, MainPageButton,Title } from "./Button";
import { TransactionTable } from "./Table";

export function HistoryPage({username, setPage,userId}) {

  return (<><Title username= {username}></Title>
   <MainPageButton setPage={setPage}></MainPageButton>
  <AdminButton setPage={setPage}></AdminButton>
  <LogoutButton setPage={setPage}></LogoutButton>

  <TransactionTable></TransactionTable></>);
}