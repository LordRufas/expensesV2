
import { LogoutButton, AdminButton, MainPageButton,Title } from "./Button";
import { TransactionTable } from "./Table";
import { transactions} from "./data"

export function HistoryPage({username, setPage}) {

  return (<><Title username= {username}></Title>
   <MainPageButton setPage={setPage}></MainPageButton>
  <AdminButton setPage={setPage}></AdminButton>
  <LogoutButton setPage={setPage}></LogoutButton>

  <TransactionTable info={transactions}></TransactionTable></>);
}