//creating food array object for different  items
let food = [
  {  name: "Samosa", price: 15, qty: 0,amount:0 },      //food[0] refers to samosa object
  {  name: "Pani Puri", price: 20, qty: 0,amount:0 },   //food[1] refers to pani puri object
  {  name: "Tikki Chaat", price: 25, qty: 0,amount:0 }, //food[2] refers to tikki chaat object
  {  name: "Indori Bhel", price: 20, qty: 0,amount:0 }, //food[3] refers to Indori bhel object
  {  name: "Vada Pav", price: 15, qty: 0,amount:0 },    //food[4] refers to Vada pav object
  {  name: "Neembu Pani", price: 20, qty: 0,amount:0 }, //food[5] refers to Neembu pani object
];
let row_to_item = []; //to save info about which row has which items
                      //(e.g--> row_to_item[2] gives us the 'item no' for '2nd row')

let item_to_row = []; //to save info about which item has which row
                      //(e.g--> item_to_row[0] gives us the 'row no' for 'item no'(here '0' refers to samosa in food array))

let count = 0;        //to keep track of the total row nos
let total_amount = 0; //saves the total amount

function display_row(row_no, item) {
  document.getElementById(row_no * 10 + 1).innerHTML = row_no;
  document.getElementById(row_no * 10 + 2).innerHTML = food[item].name;
  document.getElementById(row_no * 10 + 3).innerHTML = food[item].qty;
  document.getElementById(row_no * 10 + 4).innerHTML = food[item].amount;
}

function display_empty_row(row_no) {
  document.getElementById(row_no * 10 + 1).innerHTML = "";
  document.getElementById(row_no * 10 + 2).innerHTML = "";
  document.getElementById(row_no * 10 + 3).innerHTML = "";
  document.getElementById(row_no * 10 + 4).innerHTML = "";
}

function display_remove_button(row_no) {
  const btn = document.createElement("button");
  btn.innerHTML = "REMOVE";
  btn.id = row_no * 10;     //assigning id for remove button according to its row (e.g--> row '2' has id='20')
  document.getElementById(row_no * 10 + 3).appendChild(btn); //appending remove button in respective table data id
  document.getElementById(row_no * 10).onclick = function remove() {
    remove_item(row_to_item[row_no]);
  };
}

function display_total_amount() {
  total_amount =
    food[0].amount +
    food[1].amount +
    food[2].amount +
    food[3].amount +
    food[4].amount +
    food[5].amount;
  document.getElementById("total").innerHTML = total_amount;
}

function add_item(item) {
  if (food[item].qty == 0) //initialising the 'row_to_item' array & 'item_to_row' array data respectively &
                           //displaying the respective item in the apt row
  {
    food[item].qty++;
    food[item].amount = food[item].qty * food[item].price;
    count++;                   //updating the total rows count
    row_to_item[count] = item; //intializing the 'row_to_item' array with apt 'row no' as index with the 'item no'
    item_to_row[item] =count; //intializing the 'item_to_row' array with apt 'item no' as index with the 'row no'
    display_row(item_to_row[item], item);
    display_total_amount();
    display_remove_button(item_to_row[item]);
  } else {
    food[item].qty++;
    food[item].amount = food[item].qty * food[item].price;
    display_row(item_to_row[item], item);
    display_total_amount();
    display_remove_button(item_to_row[item]);
  }
}
function remove_item(item) {
  if (food[item].qty > 1) //if qty>1 decreases the item qty and displays its respective row
  {
    food[item].qty--;
    food[item].amount = food[item].qty * food[item].price;
    display_row(item_to_row[item], item);
    display_remove_button(item_to_row[item]);
    display_total_amount();
  }
  else
  {
    food[item].amount = 0;
    food[item].qty--;
    if (item_to_row[item] ==count)        //if the current item's row is the last row  of item to be deleted
    {
      count--;                             //updating the total rows count
      display_empty_row(item_to_row[item]);//displaying the last row as empty ,thus deleting item from the table
      display_total_amount();
    }
    else  //else causing shift in data due to deletion of the item from current row
    {
      for (let i = item_to_row[item]; i < count; i++)
      {
        row_to_item[i] = row_to_item[i + 1]; //updating the 'row_to_item' array due to shift of item upwards in row
        item_to_row[row_to_item[i]]--;       //updating the 'item_to_row' array due to shift of above 'row_to_item' array
        display_row(i, row_to_item[i]);      //displaying the updated row due to shift
        display_remove_button(i);            //displaying the updated remove button due to shift
      }
      display_total_amount();
      display_empty_row(count);               //displaying the last row as empty after shift
      count--;                                //updating the total rows count
    }
  }
}

function clear_table(){
  for(let i=1;i<=count;i++)
  {
    display_empty_row(i);
  }
  document.getElementById("total").innerHTML="";
}
