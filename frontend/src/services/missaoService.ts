import { API_URL } from './api'
import type { Missao } from '../types/missao'
import { extractApiErrorMessage } from '../utils/apiError'

export async function buscarMissoes(
  token: string,
): Promise<Missao[]> {
  const response = await fetch(`${API_URL}/missoes`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })

  if (!response.ok) {
    const message = await extractApiErrorMessage(response)
    throw new Error(message)
  }

  return response.json()
}