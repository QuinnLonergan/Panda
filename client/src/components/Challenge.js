import ItemRow from "./ItemRow"
import React, {useState, useEffect} from "react";

export default function Challenge() {
  const [inventory, setInventory] = useState([])
  const [price, setPrice] = useState({})

    //Display all items on landing
    useEffect(() => { 
        getAllItems()
      }, []);

      //set inventory to low-stock items
      function getLowStock(){
        fetch("http://localhost:4567/low-stock")
        .then((r)=>r.json())
        .then((data) => {
          data.map((item) => data[data.indexOf(item)].order = 0)
          setInventory(data)
        })
      }

      //set inventory to all items
      function getAllItems(){
        fetch("http://localhost:4567/inventory")
        .then((r)=>r.json())
        .then((data) => {
          data.map((item) => data[data.indexOf(item)].order = 0)
          setInventory(data)
        })
      }

      //Post array of candy names and their order amounts to the backend
      function handleReorder(){
        let arr = []
        inventory.map((item) => {
          if (item.order > 0){
            arr.push({"name" : item.name, "order" : item.order.toString()})
          }
          return arr
        })
        console.log(JSON.stringify(arr))
        fetch("http://localhost:4567/restock-cost", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(arr)
          })
        .then((r)=>r.json())
        .then((data) => {
          setPrice(data)
        })
        .then(arr = [])
      }


  return (
    <>
      <table>
        <thead>
          <tr>
            <td>SKU</td>
            <td>Item Name</td>
            <td>Amount in Stock</td>
            <td>Capacity</td>
            <td>Order Amount</td>
          </tr>
        </thead>
        <tbody>
          {/* Item Row with inventory passed down as a prop */}
          <ItemRow inventory={inventory}/>
        </tbody>
      </table>
      {/* total cost displayed */}
      <div>Total Cost: {price["price"]}</div>
      {/* Added toggle buttons to switch between full inventory and low stock  */}
      {(inventory.length > 16) ? (
        <button onClick={getLowStock}>Get Low-Stock Items</button>
      ) : (
        <button onClick={getAllItems}>Get All Items</button>
      )}

      <button onClick={handleReorder}>Determine Re-Order Cost</button>
    </>
  );
}
