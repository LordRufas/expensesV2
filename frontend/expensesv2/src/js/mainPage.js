import * as Tables from "./Table";
import * as Buttons from "./Button";
import "../css/App.css"
import { useState, useEffect } from "react";
import { fetchTypes, fetchTotals } from "./Api";

export function MainPage({ username, setPage, userId,setErrorMessage }) {
  const [totals, setTotals] = useState([]);
  const [types, setTypes] = useState([]);
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

      <div class="headerButtons">
        <Buttons.HistoryButton setPage={setPage} />
        <Buttons.LogoutButton setPage={setPage} setErrorMessage={setErrorMessage} />
      </div>

      <div>

        <TransactionDiv userId={userId} totals={totals} setTotals={setTotals} types={types} transactions={transactions} setTransactions={setTransactions} />
        <div class="small-containerMain">
          <TotalDiv userId={userId} totals={totals} setTotals={setTotals} />
          <TypeDiv userId={userId} types={types} setTypes={setTypes} />
        </div>
      </div>
    </>
  );
}

function TransactionDiv({ userId, totals, setTotals, types, transactions, setTransactions }) {

  const [date, setDate] = useState('');
  const [total, setTotal] = useState('');
  const [value, setValue] = useState('');
  const [type, setType] = useState('');
  const [isRevenue, setIsRevenue] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  return (<>
    <div class="small-container">
      <h2>Transações</h2>
      <div class="table-container">
        <Tables.TransactionTable info={transactions} editMode={true} userId={userId} setInfo={setTransactions} setErrorMessage={setErrorMessage}></Tables.TransactionTable>
      </div>
      <div class="child">
        <input class="calendar" type="month" onChange={(e) => setDate(e.target.value)} />
        <input class="calendar" placeholder="valor" type="number" onChange={(e) => setValue(e.target.value)} ></input>
        <Buttons.Combobox info={types} value={type} setValue={setType} placeholder={"Tipo de transferencia"}></Buttons.Combobox>
        <Buttons.Combobox info={totals} value={total} setValue={setTotal} placeholder={"Total"}></Buttons.Combobox>
        <label class="custom-checkbox">
          <input type="checkbox" onChange={(e) => setIsRevenue(e.target.checked)}></input>
          <span class="checkmark"></span>
        </label>


        <Buttons.AddTransactionButton userId={userId} setTransactions={setTransactions} date={date}
          total={total} value={value} type={type} isRevenue={isRevenue} setErrorMessage={setErrorMessage}
          transactions={transactions} setTransaction={setTransactions}
          totals={totals} setTotals={setTotals}>Adicionar</Buttons.AddTransactionButton>
        <Buttons.ErrorMessage errorMessage={errorMessage}></Buttons.ErrorMessage>
      </div></div>
  </>)

}

function TotalDiv({ userId, totals, setTotals }) {
  const currentDate = getCurrentDate();
  const [name, setName] = useState('');
  const [value, setValue] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  return (<>
    <div class="small-container">
      <div>
        <h2>Totais atuais</h2>
        <div class="table-container">
          <Tables.TotalTable info={totals} setInfo={setTotals} userId={userId} setErrorMessage={setErrorMessage}></Tables.TotalTable>
        </div>
      </div>
      <div class="child">
        <input class="calendar" placeholder="nome" onChange={(e) => setName(e.target.value)}></input>
        <input class="calendar" placeholder="valor" type="number" onChange={(e) => setValue(e.target.value)}></input>
        <Buttons.AddTotalButton userId={userId} date={currentDate}
          name={name} totals={totals} setTotals={setTotals} value={value} setErrorMessage={setErrorMessage}>Adicionar</Buttons.AddTotalButton>
        <Buttons.ErrorMessage errorMessage={errorMessage}></Buttons.ErrorMessage>
      </div>
    </div>
  </>)
}

function TypeDiv({ userId, types, setTypes }) {

  const [errorMessage, setErrorMessage] = useState('');
  const [name, setName] = useState('');

  return (<>
    <div class="small-container">
      <div >
        <h2>Tipos</h2>
        <div class="table-container">
          <Tables.TypesTable info={types} setInfo={setTypes} userId={userId} setErrorMessage={setErrorMessage}></Tables.TypesTable>
        </div>
      </div>
      <div class="child"><input class="calendar" placeholder="nome" onChange={(e) => setName(e.target.value)}></input>
        <Buttons.AddTypeButton userId={userId} name={name}
          setErrorMessage={setErrorMessage} types={types} setTypes={setTypes}>Adicionar</Buttons.AddTypeButton>
        <Buttons.ErrorMessage errorMessage={errorMessage} ></Buttons.ErrorMessage>
      </div> </div></>)
}


function getCurrentDate() {
  const now = new Date();

  const year = now.getFullYear();
  const month = now.getMonth() + 1;

  return `${year}-${String(month).padStart(2, "0")}`;
}