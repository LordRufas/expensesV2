import * as Buttons from "./Button";
import * as API from "./Api"

export function ResumeTable({ info, setInfo }) {
  return (
    <table border="1">
      <thead>
        <tr>
          <th>Nome </th>
          <th>Valor </th>
        </tr>
      </thead>
      {
        info &&
        <tbody>
          {
            info.map((data) => (<>
              <tr>
                <td>{data.name}</td>
                <td>{data.value} </td>
              </tr>
            </>))
          }
        </tbody>
      }
    </table>
  );
}

export function TotalTable({ info, setInfo, userId, setErrorMessage }) {
  return (
    <table border="1">
      <thead>
        <tr>
          <th>Nome</th>
          <th>Valor</th>
          <th></th>
        </tr>
      </thead>{
        info &&
        <tbody>
          {
            info.map((data) => (<>
              <tr>
                <td><input type="text" value={data.name} placeholder={data.name} onChange={(e) => changeTotal(e.target.value, data, "name", info, setInfo, userId)} /></td>
                <td><input type="number" value={data.value} placeholder={data.value} onChange={(e) => changeTotal(e.target.value, data, "value", info, setInfo, userId)} /></td>
                <td><Buttons.DeleteTotalButton userId={userId} data={data} info={info} setInfo={setInfo} setErrorMessage={setErrorMessage}>apagar</Buttons.DeleteTotalButton></td>
              </tr>


            </>))
          }
        </tbody>
      }
    </table>
  );
}

export function TransactionTable({ info, userId, editMode, setInfo, setErrorMessage }) {
  return (
    <table border="1">
      <thead>
        <tr>
          <th> data </th>

          <th> tipo </th>

          {editMode && <th> total </th>}

          <th> valor </th>
          <th></th>
        </tr>
      </thead>
      {
        info &&
        <tbody>
          {
            info.map((data) => (<>
              <tr>
                <td> {data.date} </td>
                <td>{data.typeName} </td>
                <td>{data.total} </td>
                <td>{data.value} </td>
                <td>{editMode && <Buttons.DeleteTransactionButton userId={userId} data={data} info={info} setInfo={setInfo} setErrorMessage={setErrorMessage}>apagar</Buttons.DeleteTransactionButton >}</td>
              </tr>
            </>))
          }
        </tbody>
      }
    </table>
  );
}

export function TypesTable({ info, setInfo, userId, setErrorMessage }) {
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
            info.map((data) => (<>
              <tr>
                <td> <input type="text" value={data.name} placeholder={data.name} onChange={(e) => changeType(e.target.value, data.name, info, setInfo, userId)} /></td>
                <td><Buttons.DeleteTypeButton userId={userId} data={data} info={info} setInfo={setInfo} setErrorMessage={setErrorMessage}>apagar</Buttons.DeleteTypeButton ></td>
              </tr>
            </>))
          }
        </tbody>
      }
    </table>
  );
}


export function changeType(newValue, oldValue, types, setTypes, userId) {

  const filteredInfo = types.filter(item => item.name !== oldValue);
  const newType = {
    userId: userId,
    name: newValue
  };

  const newTypes = [...filteredInfo, newType];


  setTypes(newTypes.sort((a, b) => {
    return a.name.localeCompare(b.name);
  }));

}

export async function changeTotal(newValue, oldValue, property, totals, setTotals, userId) {

  const filteredInfo = totals.filter(item => item.name !== oldValue.name);
  let newTotal = [];

  if (property === "name")
    newTotal = {
      date: oldValue.date,
      name: newValue,
      userId: userId,
      value: oldValue.value

    };
  else
    newTotal = {
      date: oldValue.date,
      name: oldValue.name,
      userId: userId,
      value: newValue

    };




  const response = await API.updateTotals(newTotal.userId, oldValue.name, newTotal.name, oldValue.date, newTotal.date, Number(oldValue.value), Number(newTotal.value).toFixed(2))
  if (response.statusCode === 200 && response.statusMessage === "Total updated with success") {
    const newTotals = [...filteredInfo, newTotal];
    setTotals(newTotals.sort((a, b) => {
      return a.name.localeCompare(b.name);
    }))

  }

}

export async function updateTotal(addValue, oldValue, totals, setTotals, isRevenue, setErrorMessage) {

  const oldTotal = totals.filter(item => item.name === oldValue);

  const filteredInfo = totals.filter(item => item.name !== oldValue);
  let newTotal = [];
  let newValue = isRevenue
    ? Number(oldTotal[0].value) + Number(addValue)
    : Number(oldTotal[0].value) - Number(addValue);



  newTotal = {
    date: oldTotal[0].date,
    name: oldTotal[0].name,
    userId: oldTotal[0].userId,
    value: newValue.toFixed(2)

  };
  const response = await API.updateTotals(newTotal.userId, oldTotal[0].name, newTotal.name, oldTotal[0].date, newTotal.date, oldTotal[0].value, newValue.toFixed(2))

  if (response.statusCode === 200 && response.statusMessage === "Total updated with success") {

    const newTotals = [...filteredInfo, newTotal];

    setTotals(newTotals.sort((a, b) => {
      return a.name.localeCompare(b.name);
    }))
  } else
    setErrorMessage(response.statusMessage)
}
