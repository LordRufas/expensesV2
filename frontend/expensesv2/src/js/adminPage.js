import { MainPageButton, HistoryButton, LogoutButton, Title } from "./Button";
import { TotalTable, TypesTable } from "./Table";

import { use, useState , useEffect} from "react";
import { fetchTypes ,fetchTotals, fetchData } from "./Api";

export function AdminPage({username, setPage,userId}) {

    const [totals, setTotals] = useState('');
  const [types, setTypes] = useState('');

 


  return (<><Title username= {username}></Title>
 <MainPageButton setPage={setPage}></MainPageButton>
   <HistoryButton setPage={setPage}></HistoryButton>
   <LogoutButton setPage={setPage}></LogoutButton>
    <TotalTable info={totals}></TotalTable>
    <TypesTable info = {types}></TypesTable></>);
}
