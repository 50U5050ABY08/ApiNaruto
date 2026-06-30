import type { ApiError } from '../types/apiError'

export async function extractApiErrorMessage(response: Response,): Promise<string> {
    try {
        const data: ApiError = await response.json()

        if (data.fields) {
            return Object.values(data.fields).join(' ')
        }

        if (data.message) {
            return data.message
        }

        return `Erro na requisição. Status: ${response.status}`
    } catch {
        return `Erro na requisição. Status: ${response.status}`
    }
}