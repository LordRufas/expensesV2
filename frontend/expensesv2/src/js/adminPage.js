import { MainPageButton, HistoryButton, LogoutButton, Title } from "./Button";
import { TotalTable, TypesTable } from "./Table";
import {totals, types} from "./data"

export function AdminPage({username, setPage}) {

  return (<><Title username= {username}></Title>
 <MainPageButton setPage={setPage}></MainPageButton>
   <HistoryButton setPage={setPage}></HistoryButton>
   <LogoutButton setPage={setPage}></LogoutButton>
    <TotalTable info={totals}></TotalTable>
    <TypesTable info = {types}></TypesTable></>);
}