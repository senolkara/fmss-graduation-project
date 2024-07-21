
import Cookies from "js-cookie";

export const get_by_user_id = async (userId:string) => {
  try {
    const res = await fetch(`http://localhost:3000/api/customer/get-by-user-id?userId=${userId}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${Cookies.get('token')}`
      }
    });
    const data = await res.json();
    return data;
  } catch (error) {
    console.log('Error in getting customer => ', error);
  }
}