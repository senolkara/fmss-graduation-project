import Joi from 'joi';
import { NextResponse } from 'next/server';


const schema = Joi.object({
    email: Joi.string().email().required(),
    password: Joi.string().required(),
});




export  async function POST (req: Request){
    

    const { email, password } = await req.json();
    const { error } = schema.validate({ email, password });

    if (error) return NextResponse.json({ success: false, message: error.details[0].message.replace(/['"]+/g, '') });

    
    try {
        const res = await fetch(`http://localhost:8090/api/v1/auth/authenticate`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, password }),
        });
        if (!res.ok){
            return NextResponse.json({ success: false, message: "Login Failed"});
        }
        const data = await res.json();
        return NextResponse.json({ success: true, message: "Login Successfull",  data});
    } catch (error) {
        console.log('error in login (service) => ', error);
    }
}
