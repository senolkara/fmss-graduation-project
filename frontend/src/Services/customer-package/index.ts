
import Cookies from "js-cookie";

export const get_all_customer_packages_by_customer_id = async (customerId: any) => {
  try {
    const res = await fetch(`http://localhost:3000/api/customer-package/get-all-by-customer-id?customerId=${customerId}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${Cookies.get('token')}`
      }
    });
    const data = await res.json();
    return data;
  } catch (error) {
    console.log('Error in getting all customer packages => ', error)
  }
}
