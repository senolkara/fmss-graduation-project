import Cookies from 'js-cookie';

export const update_user_profile = async (userId: string, formData: any) => {
    
    try {
        const res = await fetch(`http://localhost:3000/api/user/profile?userId=${userId}`, {
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
        console.log('error in update user profile (service) => ', error);
    }
}

export const get_addresses_by_user_id = async (userId: string) => {
    
    try {
        const res = await fetch(`http://localhost:3000/api/user/addresses?userId=${userId}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${Cookies.get('token')}`
            }
        });
        const data = await res.json();
        return data;
    } catch (error) {
        console.log('error in register (service) => ', error);
    }
}