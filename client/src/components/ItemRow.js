
import React from "react";

export default function ItemRow({inventory}) {

    //create row for every item in inventory
    return (
        <>
        {inventory.map((item) => (
            <tr>
            <td>{item.id}</td>
            <td>{item.name}</td>
            <td>{item.stock}</td>
            <td>{item.capacity}</td>
            <td>
                <form>
                    <input 
                        onChange={(e) => (item.order = parseInt(e.target.value))}
                        type="text" 
                        name="name" />
                </form>
            </td>
          </tr>
        ))}
        </>
    )
}