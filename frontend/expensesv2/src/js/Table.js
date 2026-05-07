import { useSearchParams } from "react-router-dom";
import styles from "../css/App.css";

import * as Buttons from "./Button";
import * as API from "./Api"
import { useState } from "react";

export function TotalTable({ info, setInfo, userId, setErrorMessage }) {
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
            info.map((data) => (<>
              <tr>
                <input type="text" value={data.name} placeholder={data.name} onChange={(e) => changeTotal(e.target.value, data, "name", info, setInfo, userId)} />
                <input type="number" value={data.value} placeholder={data.value} onChange={(e) => changeTotal(e.target.value, data, "value", info, setInfo, userId)} />
                <Buttons.DeleteTotalButton userId={userId} data={data} info={info} setInfo={setInfo} setErrorMessage={setErrorMessage}>apagar</Buttons.DeleteTotalButton>
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
          data

          tipo

          total

          valor
        </tr>
      </thead>
      {
        info &&
        <tbody>
          {
            info.map((data) => (<>
              <tr>
                {data.date} { }
                {data.typeName} { }
                {data.total} { }
                {data.value} { }
                {editMode && <Buttons.DeleteTransactionButton userId={userId} data={data} info={info} setInfo={setInfo} setErrorMessage={setErrorMessage}>apagar</Buttons.DeleteTransactionButton >}</tr>
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
                <input type="text" value={data.name} placeholder={data.name} onChange={(e) => changeType(e.target.value, data.name, info, setInfo, userId)} />
                <Buttons.DeleteTypeButton userId={userId} data={data} info={info} setInfo={setInfo} setErrorMessage={setErrorMessage}>apagar</Buttons.DeleteTypeButton ></tr>
            </>))
          }
        </tbody>
      }
    </table>
  );
}


export function changeType(newValue, oldValue, types, setTypes, userId) {

  const filteredInfo = types.filter(item => {
    if (item.name !== oldValue) {
      return item;
    }

  });
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

  const filteredInfo = totals.filter(item => {
    if (item.name !== oldValue.name) {
      return item;
    }
  });
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

  const oldTotal = totals.filter(item => {
    if (item.name === oldValue) {
      return item;
    }
  });

  const filteredInfo = totals.filter(item => {
    if (item.name !== oldValue) {
      return item;
    }
  });
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
