
import Cookies from "js-cookie";

export const get_all = async () => {
  try {
    const res = await fetch(`http://localhost:3000/api/cpackage/get-all`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${Cookies.get('token')}`
      }
    });
    const data = await res.json();
    return data;
  } catch (error) {
    console.log('Error in getting all packages => ', error)
  }
}

export const get_by_id = async (cpackageId:string) => {
  try {
    const res = await fetch(`http://localhost:3000/api/cpackage/get-by-id?cpackageId=${cpackageId}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${Cookies.get('token')}`
      }
    });
    const data = await res.json();
    console.log(data);
    return data;
  } catch (error) {
    console.log('Error in getting cpackage => ', error);
  }
}