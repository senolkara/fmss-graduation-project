import Joi from 'joi';
import { NextResponse } from 'next/server';

const schema = Joi.object({
    email: Joi.string().email().required(),
    surname: Joi.string().required(),
    name: Joi.string().required()
});


export  async function PUT (req : Request)  {
    const { searchParams } = new URL(req.url);
    const userId = searchParams.get('userId');

    const userUpdateRequestDto = await req.json();
    const email = userUpdateRequestDto.email;
    const surname = userUpdateRequestDto.surname;
    const name = userUpdateRequestDto.name;
    const { error } = schema.validate({ email, surname, name });

    if (error) return NextResponse.json({ success: false, message: error.details[0].message.replace(/['"]+/g, '') });

    try {
        const token = req.headers.get("Authorization")?.split(" ")[1];
        const res = await fetch(`http://localhost:8090/api/v1/users/update/${userId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(userUpdateRequestDto),
        });
        return NextResponse.json({ success: true, message: "User profile updated successfully"});
    } catch (error) {
        console.log('error in register (service) => ', error);
        return NextResponse.json({ success: false, message: "Something Went Wrong Please Retry Later !" })
    }
}

