import axios from "axios";

const HOUSE_BASE_URL = "http://122.202.45.37:8088";
const SCHOOL_BASE_URL = "http://192.168.1.17:8088";

const BASE_URL = SCHOOL_BASE_URL;

export const apiDefault = () => {
    return axios.create({
        baseURL: `${BASE_URL}/api/v1`,
    });
};
