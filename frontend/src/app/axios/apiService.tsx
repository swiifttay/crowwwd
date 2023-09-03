import axios from 'axios';

const api = axios.create({
    //TODO: backend to provide
    baseURL:'/api/v1'
})

export const login = async (username:string, password:string) => {
    try {
        const response = await api.post('/login', {username, password});
        return response.data;
    } catch (error){
        // error will be handled in LoginForm.tsx
        throw error;
    }
}

export const register = async (firstName:string, lastName: string, username:string, password:string) => {
    try {
        const response = await api.post('/register', {firstName, lastName, username, password});
        return response.data;
    } catch (error){
        // error will be handled in RegisterForm.tsx
        throw error;
    }
}