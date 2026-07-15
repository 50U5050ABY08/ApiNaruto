const rawApiUrl = import.meta.env.VITE_API_URL

if (!rawApiUrl) {
    throw new Error('VITE_API_URL não foi configurada no arquivo .env.')
}

const API_URL = rawApiUrl.replace(/\/+$/, '')

export { API_URL }