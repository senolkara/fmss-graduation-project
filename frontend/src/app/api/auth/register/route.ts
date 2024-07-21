import Joi from 'joi';
import { NextResponse } from 'next/server';


const schema = Joi.object({
    email: Joi.string().email().required(),
    password: Joi.string().min(8).required(),
    name: Joi.string().required(),
    surname: Joi.string().required()
});


export  async function POST (req : Request)  {
    const { email, password, name, surname } = await req.json();
    const { error } = schema.validate({ email, password, name, surname });

    if (error) return NextResponse.json({ success: false, message: error.details[0].message.replace(/['"]+/g, '') });

    try {
        const res = await fetch(`http://localhost:8091/api/v1/customers`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, password, name, surname }),
        });
        const data = await res.json();
        if (data.user == undefined || 
            data.user == null){
            return NextResponse.json({ success: false, message: "Account not created"});
        }
        return NextResponse.json({ success: true, message: "Account created successfully", data: data });
    } catch (error) {
        console.log('error in register (service) => ', error);
        return NextResponse.json({ success: false, message: "Something Went Wrong Please Retry Later !" })
    }
}

