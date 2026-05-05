import * as Tables from "./Table";
import * as Buttons  from "./Button";
import "../css/App.css"
import { use, useState , useEffect} from "react";
import { fetchTypes ,fetchTotals } from "./Api";
import userEvent from "@testing-library/user-event";

export function MainPage({ username, setPage, userId}) {
  const [totals, setTotals] = useState([]);
  const [types, setTypes] = useState([]);
  const [errorMessage, SetErrorMessage] = useState('');
  const [transactions, setTransactions] = useState([]);
  

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
      <Buttons.Title username={username} />

      <div>
        <Buttons.HistoryButton setPage={setPage} />
        <Buttons.LogoutButton setPage={setPage} />
      </div>

      <div>
        <TotalDiv userId={userId} totals={totals} setTotals={setTotals}/>
        <TransactionDiv userId={userId} totals = {totals} types={types} transactions={transactions} setTransactions={setTransactions}/>
        <TypeDiv userId={userId} types={types} setTypes={setTypes}/>
      </div>

      <div>
        <Buttons.SaveButton />
      </div>
    </>
  );
}

function TransactionDiv({userId, totals, types, transactions, setTransactions}){
  
  const [date, setDate] = useState(''); 
  const [total, setTotal] = useState('');
  const [value, setValue] = useState('');
  const [type, setType] = useState('');
  const [isRevenue, setIsRevenue] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  return(<>    <h2>Transações</h2>
    <Tables.TransactionTable info={transactions}></Tables.TransactionTable>
    
  <input type="month"  onChange={(e) => setDate(e.target.value)} />
    <input placeholder="valor" type="number"  onChange={(e) => setValue(e.target.value)} ></input>
   <Buttons.Combobox info={types} value={type} setValue={setType} placeholder={"Tipo de transferencia"}></Buttons.Combobox>
  <Buttons.Combobox info={totals} value={total} setValue={setTotal} placeholder={"Total"}></Buttons.Combobox>
  <input type="checkbox" placeholder="+/-"></input>
  <Buttons.AddTransactionButton userId={userId} setTransactions={setTransactions} date={date} 
    total={total} value={value} type={type} isRevenue={isRevenue} setErrorMessage={setErrorMessage}
    transactions={transactions}setTransaction={setTransactions}>Adicionar</Buttons.AddTransactionButton>
   <Buttons.ErrorMessage errorMessage={errorMessage}></Buttons.ErrorMessage> 
    </>)
}

function TotalDiv({userId, totals, setTotals}){
  const currentDate = getCurrentDate();
  const [name, setName] = useState('');
  const [value, setValue] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  return(<>
    <h2>Totais atuais</h2>
    <Tables.TotalTable info={totals}></Tables.TotalTable>
      <input placeholder="nome" onChange={(e) => setName(e.target.value)}></input>
   <input placeholder="valor" type="number" onChange={(e) => setValue(e.target.value)}></input>
  <Buttons.AddTotalButton userId={userId}  date={currentDate} 
    name={name} totals={totals} setTotals={setTotals} value={value} setErrorMessage={setErrorMessage}>Adicionar</Buttons.AddTotalButton>
  <Buttons.ErrorMessage errorMessage={errorMessage}></Buttons.ErrorMessage> 
  </>)
}

function TypeDiv({userId, types, setTypes}){

  const [errorMessage, setErrorMessage] = useState('');
   const [name, setName] = useState('');

  return(<>
    <h2>Tipos</h2>
    <Tables.TypesTable info={types} onChange={(e) => setName(e.target.value)}></Tables.TypesTable>
    <input placeholder="nome" onChange={(e) => setName(e.target.value)}></input>
  <Buttons.AddTypeButton userId={userId} name ={name}
  setErrorMessage={setErrorMessage} types={types} setTypes={setTypes}>Adicionar</Buttons.AddTypeButton>
  <Buttons.ErrorMessage errorMessage={errorMessage} ></Buttons.ErrorMessage> </>)
}


function getCurrentDate() {
  const now = new Date();

  const year = now.getFullYear();
  const month = now.getMonth() + 1;

  return `${year}-${String(month).padStart(2, "0")}`;
}