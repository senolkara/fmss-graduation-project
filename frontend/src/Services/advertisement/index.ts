
import Cookies from "js-cookie";

export const get_all_advertisements_by_customer_id = async (customerId:string) => {
  try {
    const res = await fetch(`http://localhost:3000/api/advertisement/get-all-by-customer-id?customerId=${customerId}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${Cookies.get('token')}`
      }
    });
    const data = await res.json();
    console.log(data);
    return data;
  } catch (error) {
    console.log('Error in getting all advertisements => ', error)
  }
}

export const get_by_id = async (id:string) => {
  try {
    const res = await fetch(`http://localhost:3000/api/advertisement/get-by-id?id=${id}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${Cookies.get('token')}`
      }
    });
    const data = await res.json();
    console.log(data);
    return data;
  } catch (error) {
    console.log('Error in getting advertisement => ', error);
  }
}

export const create_a_new = async (formData: any) => {
  try {
    const res = await fetch(`http://localhost:3000/api/advertisement/create`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${Cookies.get('token')}`
        },
        body: JSON.stringify(formData),
    });
    const data = res.json();
    console.log(data);
    return data;
  } catch (error) {
      console.log('error in create advertisement (service) => ', error);
  }
}

export const update_a = async (id:string, formData: any) => {
  try {
    const res = await fetch(`http://localhost:3000/api/advertisement/update?id=${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${Cookies.get('token')}`
        },
        body: JSON.stringify(formData),
    });
    const data = res.json();
    console.log(data);
    return data;
  } catch (error) {
      console.log('error in update advertisement (service) => ', error);
  }
}

export const delete_a = async (id:string) => {
    try {
      const res = await fetch(`http://localhost:3000/api/advertisement/delete?id=${id}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${Cookies.get('token')}`
        },
      })
  
      const data = await res.json();
      console.log(data);
      return data;
    } catch (error) {
      console.log('Error in deleting Advertisement (service) =>', error)
    }
  }
