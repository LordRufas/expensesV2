import { useSearchParams } from "react-router-dom";
import styles from "../css/App.css";

import * as Buttons from "./Button";
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
                {data.value} { }
                {editMode && <Buttons.DeleteTransactionButton userId={userId} data= {data}  info ={info} setInfo ={setInfo} setErrorMessage={setErrorMessage}>apagar</Buttons.DeleteTransactionButton >}</tr>
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
                <Buttons.DeleteTypeButton userId={userId} data= {data}  info ={info} setInfo ={setInfo} setErrorMessage={setErrorMessage}>apagar</Buttons.DeleteTypeButton ></tr>
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

export function changeTotal(newValue, oldValue, property, totals, setTotals, userId) {

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


  const newTotals = [...filteredInfo, newTotal];

  setTotals(newTotals.sort((a, b) => {
    return a.name.localeCompare(b.name);
  }))

}
