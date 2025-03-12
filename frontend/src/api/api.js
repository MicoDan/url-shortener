import axios from "axios";

/**
 * Axios instance configuration for API requests
 * Sets base URL from environment variables
 */
export default axios.create({
    baseURL: import.meta.env.VITE_BACKEND_URL,
});