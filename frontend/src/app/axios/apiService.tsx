import axios from 'axios';

const api = axios.create({
    //TODO: backend to provide
    baseURL:'http://localhost:8080/api/'
})

export const authenticate = async (username:string, password:string) => {
    try {
        const response = await api.post('/authenticate', {username, password});
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