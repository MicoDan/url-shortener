import React, { useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { useStoreContext } from '../contextApi/ContextApi';
import { useUpdateUrlClicks } from '../hooks/useQuery';

const ShortenUrlPage = () => {
    const { url } = useParams();
    const { token } = useStoreContext();
    const updateClicksMutation = useUpdateUrlClicks(token);

    useEffect(() => {
        if (url) {
            // Update click count before redirecting
            updateClicksMutation.mutate(url);
            window.location.href = import.meta.env.VITE_BACKEND_URL + `/${url}`;
        }
    }, [url]);

    return <p>Redirecting...</p>;
}

export default ShortenUrlPage