import Cookies from "js-cookie";

export const get_all = async () => {
    try {
      const res = await fetch(`http://localhost:3000/api/building/get-all`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${Cookies.get('token')}`
        }
      });
      const data = await res.json();
      console.log(data);
      return data;
    } catch (error) {
      console.log('Error in getting buildings => ', error);
    }
  }