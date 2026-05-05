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

export function TransactionTable({info1})
{

 const  info = [
      {
        "date": "01/01/1999",
        "typeName": "a",
        "userId": "0",
        "value": "1.0",
        "isRevenue": "false"
      },
      {
        "date": "01/01/1999",
        "typeName": "batata0",
        "userId": "0",
        "value": "1.0",
        "isRevenue": "false"
      },
      {
        "date": "01/01/2001",
        "typeName": "batata0",
        "userId": "0",
        "value": "1.0",
        "isRevenue": "false"
      }
    ];
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
             <button>editar</button>
            <button>apagar</button></tr>
        </>)) 
        }
        </tbody>
}
    </table>
  );
}