import { Table } from "./Table";
export function MainPage({username, setPage}) {

  const headers =[{"name": "teste"}];
  const data = [{"name": "teste1"}, {"name": "teste2"}, {"name": "teste3"}];

  function goToAdminPage(){
    setPage('admin')
  }

  function goToHistoryPage(){
    setPage('history')
  }

  return (<><h1>Welcome,{username} </h1>
  <Table headers={headers} info={data}></Table>
  <button onClick={goToAdminPage}>admin</button>
  <button onClick={goToHistoryPage}>history</button>
  </>);
}