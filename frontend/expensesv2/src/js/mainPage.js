import { TotalTable, TransactionTable, TypesTable } from "./Table";
import { LogoutButton, HistoryButton, Title, SaveButton } from "./Button";
import "../css/App.css"
import { use, useState , useEffect} from "react";
import { fetchTypes ,fetchTotals } from "./Api";

export function MainPage({ username, setPage, userId }) {
  const [totals, setTotals] = useState([]);
  const [types, setTypes] = useState([]);

  useEffect(() => {
    async function loadData() {
      const typesResponse = await fetchTypes(userId);
      const totalsResponse = await fetchTotals(userId);

      if (typesResponse.statusCode === 200 && typesResponse.statusMessage === "OK") {
        setTypes(typesResponse.response.types);
      }

      if (totalsResponse.statusCode === 200 && totalsResponse.statusMessage === "OK") {
        setTotals(totalsResponse.response.totals);
      }
    }

    loadData();
  }, [userId]);

  return (
    <>
      <Title username={username} />

      <div>
        <HistoryButton setPage={setPage} />
        <LogoutButton setPage={setPage} />
      </div>

      <div>
        <TotalDiv totals={totals} />
        <TransactionDiv />
        <TypeDiv types={types} />
      </div>

      <div>
        <SaveButton />
      </div>
    </>
  );
}

function TransactionDiv(){
  return(<>    <h2>Transações</h2>
    <TransactionTable></TransactionTable>
    <input placeholder="data"></input> <input placeholder="valor"></input>
  <input placeholder="tipo"></input>
  <input placeholder="total"></input>
  <input placeholder="+/-"></input>
  <button>Adicionar</button></>)
}

function TotalDiv({totals}){
  return(<>
    <h2>Totais atuais</h2>
    <TotalTable info={totals}></TotalTable>
      <input placeholder="nome"></input>
   <input placeholder="valor"></input>
  <button>Adicionar</button></>)
}

function TypeDiv({types}){
  return(<>
    <h2>Tipos</h2>
    <TypesTable info={types}></TypesTable>
    <input placeholder="nome"></input>
  <button>Adicionar</button></>)
}
