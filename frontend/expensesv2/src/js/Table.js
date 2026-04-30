export function TotalTable({info})
{
  return (
    <table border="1">
      <thead>
        <tr>
          Nome

          Valor
        </tr>
      </thead>

      <tbody>
            {
            info.map((data) =>(<>
            <tr key={data.userId}>
            {data.name} { }
            {data.value}</tr>
        </>)) 
        }
        </tbody>
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

      <tbody>
            {
            info.map((data) =>(<>
            <tr key={data.userId}>
            {data.name} </tr>
        </>)) 
        }
        </tbody>
    </table>
  );
}

export function TransactionTable({info})
{
  return (
    <table border="1">
      <thead>
        <tr>
          data

          tipo

          valor

          +/-
        </tr>
      </thead>

      <tbody>
            {
            info.map((data) =>(<>
            <tr key={data.userId}>
            {data.date} { }
            {data.typeName} { }
            {data.value} { }
            {data.isRevenue === "true" ? '+' : '-'}</tr>
        </>)) 
        }
        </tbody>
    </table>
  );
}