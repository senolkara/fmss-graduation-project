
import Cookies from "js-cookie";

export const create_a_new = async (formData: any) => {
  try {
    const res = await fetch(`http://localhost:3000/api/order/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${Cookies.get('token')}`
      },
      body: JSON.stringify(formData),
    });
    const data = await res.json();
    console.log(data);
    return data;
  } catch (error) {
    console.log('Error in creating Order (service) =>', error);
  }
}



export const get_all_orders_by_customer_id = async (customerId: any) => {
  try {
    const res = await fetch(`http://localhost:3000/api/order/get-all-by-customer-id?customerId=${customerId}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${Cookies.get('token')}`
      }
    });
    const data = await res.json();
    return data;
  } catch (error) {
    console.log('Error in getting all orders Item for specific Order (service) =>', error)
  }
}


export const get_order_details= async (id: any) => {
  try {
    const res = await fetch(`http://localhost:3000/api/order/view-details?id=${id}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${Cookies.get('token')}`
      }
    });
    console.log(res)
    const data = await res.json();

    return data;
  } catch (error) {
    console.log('Error in getting all orders Item for specific User (service) =>', error)
  }
}