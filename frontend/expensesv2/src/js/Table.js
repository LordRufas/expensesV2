import styles from  "../css/App.css";

export function TotalTable({info})
{
  return (
    <table border="1">
      <thead>
        <tr>
          Nome

          Valor
        </tr>
      </thead>{
info &&
      <tbody>
            {
            info.map((data) =>(<>
            <tr>
            {data.name} { }
            {data.value}
             <button>editar</button>
              <button>apagar</button>
              </tr>
        </>)) 
        }
        </tbody>
}
    </table>
  );
}


export function TypesTable({info})
{
  return (
    <table border="1">
      <thead>
        <tr>
          Nome
        </tr>
      </thead>
{
info &&
      <tbody>
            {
            info.map((data) =>(<>
            <tr>
            {data.name} 
             <button>editar</button>
              <button>apagar</button></tr>
        </>)) 
        }
        </tbody>
}
    </table>
  );
}

export function TransactionTable({info, editMode})
{
  return (
    <table border="1">
      <thead>
        <tr>
          data

          tipo

          valor
        </tr>
      </thead>
{
info &&
      <tbody>
            {
            info.map((data) =>(<>
            <tr>
            {data.date} { }
            {data.typeName} { }
            {data.value} { }
            { editMode &&
             <button>editar</button>}
           { editMode &&  <button>apagar</button>}</tr>
        </>)) 
        }
        </tbody>
}
    </table>
  );
}