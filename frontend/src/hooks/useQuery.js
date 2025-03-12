import { useQuery, useMutation, useQueryClient } from "react-query"
import api from "../api/api"


export const useFetchMyShortUrls = (token, onError) => {
    return useQuery("my-shortenurls",
         async () => {
            return await api.get(
                "/urls/",
            {
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    Authorization: "Bearer " + token,
                },
            }
        );
    },
          {
            select: (data) => {
                const sortedData = data.data.sort(
                    (a, b) => new Date(b.createdDate) - new Date(a.createdDate)
                );
                return sortedData;
            },
            onError,
            staleTime: 5000
          }
        );
};

export const useFetchTotalClicks = (token, onError) => {
    return useQuery("url-totalclick",
         async () => {
            return await api.get(
                "/urls/totalClicks?startDate=2024-01-01&endDate=2025-12-31",
            {
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    Authorization: "Bearer " + token,
                },
            }
        );
    },
          {
            select: (data) => {
                // data.data =>
                    //  {
                    //     "2024-01-01": 120,
                    //     "2024-01-02": 95,
                    //     "2024-01-03": 110,
                    //   };
                      
                const convertToArray = Object.keys(data.data).map((key) => ({
                    clickDate: key,
                    count: data.data[key], // data.data[2024-01-01]
                }));
                // Object.keys(data.data) => ["2024-01-01", "2024-01-02", "2024-01-03"]

                // FINAL:
                //   [
                //     { clickDate: "2024-01-01", count: 120 },
                //     { clickDate: "2024-01-02", count: 95 },
                //     { clickDate: "2024-01-03", count: 110 },
                //   ]
                return convertToArray;
            },
            onError,
            staleTime: 5000
          }
        );
};

export const useCreateShortUrl = (token) => {
    const queryClient = useQueryClient();
    
    return useMutation(
        async (data) => {
            const response = await api.post("/urls/shorten", data, {
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    Authorization: "Bearer " + token,
                },
            });
            return response.data;
        },
        {
            onSuccess: () => {
                // Invalidate and refetch the urls list
                queryClient.invalidateQueries("my-shortenurls");
                // Invalidate and refetch total clicks
                queryClient.invalidateQueries("url-totalclick");
            },
        }
    );
};

export const useUpdateUrlClicks = (token) => {
    const queryClient = useQueryClient();
    
    return useMutation(
        async (shortUrl) => {
            const response = await api.post(`/urls/${shortUrl}/click`, {}, {
                headers: {
                    Authorization: "Bearer " + token,
                },
            });
            return response.data;
        },
        {
            onSuccess: () => {
                // Invalidate and refetch both queries to update the UI
                queryClient.invalidateQueries("my-shortenurls");
                queryClient.invalidateQueries("url-totalclick");
            },
        }
    );
};